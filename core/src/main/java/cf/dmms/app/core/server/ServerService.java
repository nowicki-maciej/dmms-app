package cf.dmms.app.core.server;

import cf.dmms.app.spi.server.Server;
import org.springframework.stereotype.Service;

@Service
public class ServerService {

	private ServerRepository serverRepository;

	public ServerService(ServerRepository serverRepository) {
		this.serverRepository = serverRepository;
	}



}
