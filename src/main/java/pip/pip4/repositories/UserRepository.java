package pip.pip4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pip.pip4.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
