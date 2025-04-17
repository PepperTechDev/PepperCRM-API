package peppertech.crm.api.Security.Service;

import peppertech.crm.api.Mail.Model.DTO.EmailDTO;
import peppertech.crm.api.Users.Model.DTO.UserDTO;
import peppertech.crm.api.Users.Model.Entity.User;

public interface JwtServiceI {
    String Login(UserDTO reqUser) throws Exception;

    UserDTO validateAuthHeader(String token) throws Exception;

    String resetPassword(UserDTO reqUser) throws Exception;

    Boolean forgotPassword(String email) throws Exception;
}
