package cf.dmms.app.spi.sharing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutResourceRepository extends JpaRepository<OutResource, Long> {
}
