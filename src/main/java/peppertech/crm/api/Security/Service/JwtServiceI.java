package peppertech.crm.api.Security.Service;

import peppertech.crm.api.Users.Model.DTO.UserDTO;

public interface JwtServiceI {
    String Login(UserDTO reqUser) throws Exception;

    UserDTO validateAuthHeader(String token) throws Exception;

    String resetPassword(UserDTO reqUser) throws Exception;

    Boolean forgotPassword(String email) throws Exception;
}
