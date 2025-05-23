package peppertech.crm.api.Security.Config.headers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Component
public class CorsConfig implements CorsConfigurationSource {

    @Value("${http.header.cors.allowedOrigins}")
    private String allowedOrigins;

    private static final List<String> ALLOWED_METHODS = List.of("GET", "POST", "PUT", "DELETE", "PATCH");
    private static final List<String> ALLOWED_HEADERS = List.of("Content-Type", "Authorization", "X-Requested-With");
    private static final List<String> EXPOSED_HEADERS = List.of("Authorization", "X-Total-Count");

    @Override
    public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();

        List<String> originsList = allowedOrigins.contains(",")
                ? Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList()
                : List.of(allowedOrigins.trim());

        config.setAllowedOrigins(originsList);
        config.setAllowedMethods(ALLOWED_METHODS);
        config.setAllowedHeaders(ALLOWED_HEADERS);
        config.setExposedHeaders(EXPOSED_HEADERS);
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        return config;
    }

}