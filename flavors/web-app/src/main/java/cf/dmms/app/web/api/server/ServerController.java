package cf.dmms.app.web.api.server;

import cf.dmms.app.spi.server.DummyServerDto;
import cf.dmms.app.spi.server.Server;
import cf.dmms.app.spi.server.ServerRepository;
import cf.dmms.app.spi.server.ServerType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.stream.Collectors;

//TODO: fix, add server service
@RestController
@RequestMapping("/servers")
public class ServerController {

	private ServerRepository serverRepository;
	private Random random;
	private RestTemplate restTemplate;

	public ServerController(ServerRepository serverRepository) {
		this.serverRepository = serverRepository;
		Random random = new Random();
		restTemplate = new RestTemplate();
	}

	@PostMapping
	public ResponseEntity addServer(@RequestBody DummyServerDto serverDto) {
		int id = 10000 + random.nextInt(89999);
		serverDto.setAssignedId((long) id);

		DummyServerDto request = new DummyServerDto(getServerIpAddress(), (long) id,ServerType.OUTSOURCE);
		ResponseEntity<?> response = restTemplate
				.postForEntity(serverDto.getIpAddress(), request, ResponseEntity.class);
		if(!response.getStatusCode().is2xxSuccessful()) {
			throw new IllegalStateException("Server unavailable!");
		}
		Server server = serverDto.toEntity();
		server.setToken("ACCEPTED");
		serverRepository.save(server);

		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getAllServers() {
		return ResponseEntity.ok(serverRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
	}

	@PostMapping("/request")
	public ResponseEntity<?> requestServer(@RequestBody DummyServerDto serverDto) {
		serverRepository.save(serverDto.toEntity());
		return ResponseEntity.ok(serverDto.getAssignedId());
	}

	@GetMapping("/{id}/accept")
	public ResponseEntity acceptServer(@PathVariable long id){
		Server server = serverRepository.findByAssignedId(id).orElseThrow(() -> new IllegalStateException("Server not found!"));
		server.setToken("ACCEPTED");
		serverRepository.save(server);
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/{id}/reject")
	public ResponseEntity rejectServer(@PathVariable long id){
		serverRepository.deleteByAssignedId(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	private String getServerIpAddress() {
		try (final DatagramSocket socket = new DatagramSocket()) {
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			return socket.getLocalAddress().getHostAddress();
		} catch (Exception e) {
			throw new IllegalStateException("Some ip problems", e);
		}
	}

	//TODO: extract to mapper
	private DummyServerDto mapToDto(Server server) {
		DummyServerDto dummyServerDto = new DummyServerDto(server.getIpAddress(), server.getAssignedId(), server.getType());
		dummyServerDto.setAccepted("ACCEPTED".equals(server.getToken()));
		return dummyServerDto;
	}

}
