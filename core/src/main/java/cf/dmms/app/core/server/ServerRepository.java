package cf.dmms.app.core.server;

import cf.dmms.app.spi.server.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

	Optional<Server> findByAssignedId(Long assignedId);

}
