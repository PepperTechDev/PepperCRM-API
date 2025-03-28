package peppertech.crm.api.Security.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peppertech.crm.api.Exceptions.InvalidTokenException;
import peppertech.crm.api.Users.Model.DTO.UserDTO;
import peppertech.crm.api.Users.Service.SUserI;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class JwtService implements JwtServiceI {

    private final SUserI serviceUser;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long expiration;

    /**
     * Constructor que inyecta las dependencias del servicio.
     *
     * @param serviceUser servicio que maneja las operaciones de base de datos.
     */
    @Autowired
    public JwtService(SUserI serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Override
    public String Login(UserDTO reqUser) throws Exception {
        return Optional.of(serviceUser.getUserByEmail(reqUser.getEmail()))
                .filter(userDTO -> new BCryptPasswordEncoder().matches(reqUser.getPassword(), userDTO.getPassword()))
                .map(validUser -> generateToken(validUser.getId(), validUser.getEmail()))
                .orElseThrow(() -> new Exception("Credenciales Invalidas"));

    }


    private String generateToken(String id, String email) {
        // TODO: Refine token (email != subject)
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("email", email);
        // claims.put("roles", user.getAuthorities());
        return createToken(claims, email);
        // createToken (CustomClaims, subjectClaim)
    }

    @Override
    public UserDTO validateAuthHeader(String authHeader) throws Exception {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new Exception("El encabezado de autorización es inválido. Se esperaba 'Bearer <token>'.");
        }

        String token = authHeader.split(" ")[1];
        String email = extractEmail(token);
        UserDetails user = serviceUser.loadUserByUsername(email);
        if (email == null) {
            throw new InvalidTokenException("Correo no encontrado.");
        }

        if (!validateToken(token, user)) {
            throw new InvalidTokenException("Se ha expirado.");
        }

        UserDTO finalUser = serviceUser.getUserByEmail(email);
        finalUser.setPassword("");
        return finalUser;

    }

    private Boolean validateToken(String token, UserDetails userDetails) {
        Date expirationDate = extractExpiration(token);
        if (expirationDate.before(new Date())) {
            return false;
        }
        String username = extractUsername(token);
        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());


    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey())
                //.signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

}