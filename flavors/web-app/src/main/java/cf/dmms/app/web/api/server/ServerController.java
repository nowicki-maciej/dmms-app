package cf.dmms.app.web.api.server;

import cf.dmms.app.spi.server.Server;
import cf.dmms.app.core.server.ServerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

//TODO: fix, add server service
@RestController
@RequestMapping("/servers")
public class ServerController {

	private ServerRepository serverRepository;

	public ServerController(ServerRepository serverRepository) {
		this.serverRepository = serverRepository;
	}

	@PostMapping
	public ResponseEntity addServer(@RequestBody DummyServerDto serverDto) {
		serverRepository.save(serverDto.toEntity());

		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getAllServers() {
		return ResponseEntity.ok(serverRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
	}


	//TODO: extract to mapper
	private DummyServerDto mapToDto(Server server) {
		return new DummyServerDto(server.getIpAddress(), server.getAssignedId(), server.getType());
	}

}
