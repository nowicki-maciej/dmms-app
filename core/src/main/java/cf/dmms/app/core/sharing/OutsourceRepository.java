package cf.dmms.app.core.sharing;

import cf.dmms.app.spi.sharing.Outsource;
import cf.dmms.app.spi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutsourceRepository extends JpaRepository<Outsource, Long> {

	List<Outsource> findAllByReceiver(String receiver);
	List<Outsource> findAllByOwner(User owner);

}
