package pip.pip4.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

public interface JwtService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    String login(String username, String password);

    void logout(String username);

    Date loadLastLogout(String username);

}
