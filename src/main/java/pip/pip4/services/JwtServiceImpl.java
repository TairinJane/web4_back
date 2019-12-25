package pip.pip4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pip.pip4.authentication.JwtUtil;
import pip.pip4.entities.User;
import pip.pip4.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JwtServiceImpl implements UserDetailsService, JwtService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public JwtServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user with username:" + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public String login(String username, String password) {
        User entity = userRepository.findByUsername(username);
        if (entity != null) {
            long id = entity.getId();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = loadUserByUsername(username);
            String token = jwtUtil.generateToken(userDetails.getUsername(), id);
            return token;
        } else {
            throw new BadCredentialsException("No user with this login");
        }
    }

    @Override
    public void logout(String username) {
        User entity = userRepository.findByUsername(username);
        Date lastLogout = new Date();
        entity.setLastLogout(lastLogout);
        userRepository.save(entity);
    }

    @Override
    public Date loadLastLogout(String username) {
        User entity = userRepository.findByUsername(username);
        return entity.getLastLogout();
    }

}
