package pip.pip4.services;

import pip.pip4.entities.User;

public interface UserService {
    public User addUser(String username, String password);

    public long getUserId(String username);
}
