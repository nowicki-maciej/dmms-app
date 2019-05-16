package cf.dmms.app.core.sharing;

import cf.dmms.app.spi.sharing.OutboundResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundResourceRepository extends JpaRepository<OutboundResource, Long> {
}
