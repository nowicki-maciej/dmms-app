package cf.dmms.app.usermanagement.user;

import cf.dmms.app.spi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByLogin(String login);

	User getOneByLogin(String login);

}
