package pip.pip4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pip.pip4.entities.User;
import pip.pip4.exceptions.NonUniqeUsername;
import pip.pip4.repositories.UserRepository;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(String username, String password) throws NonUniqeUsername {
        User user = new User(username, password);
        Date registrationTime = new Date();
        user.setLastLogout(registrationTime);
        try {
            user = userRepository.save(user);
            return user;
        } catch (Throwable e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new NonUniqeUsername("Username is already in use", e.getCause());
            } else {
                throw e;
            }
        }
    }

    @Override
    public long getUserId(String username) {
        return userRepository.findByUsername(username).getId();
    }
}
