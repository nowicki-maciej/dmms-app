package cf.dmms.app.web.api.sharing;

import cf.dmms.app.core.sharing.SharingService;
import cf.dmms.app.spi.server.Server;
import cf.dmms.app.spi.server.ServerRepository;
import cf.dmms.app.spi.sharing.Outsource;
import cf.dmms.app.spi.user.User;
import cf.dmms.app.usermanagement.user.UserRepository;
import cf.dmms.app.web.resolver.CurrentUserId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cf.dmms.app.spi.server.ServerType.INSOURCE;
import static cf.dmms.app.spi.server.ServerType.OUTSOURCE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/sharing")
public class SharingController {

	private UserRepository userRepository;
	private SharingService sharingService;
	private SharedResourceMapper sharedResourceMapper;
	private ServerRepository serverRepository;

	public SharingController(UserRepository userRepository, SharingService sharingService, SharedResourceMapper sharedResourceMapper, ServerRepository serverRepository) {
		this.userRepository = userRepository;
		this.sharingService = sharingService;
		this.sharedResourceMapper = sharedResourceMapper;
		this.serverRepository = serverRepository;
	}

	@PostMapping
	public ResponseEntity share(@CurrentUserId Long userId, @Valid @RequestBody SharingRequest sharingRequest) {
		User currentUser = userRepository.getOne(userId);
		Long receiverServerId = sharingRequest.getReceiverServer().orElse(-1L);
		sharingService.share(currentUser, sharingRequest.getReceiver(), receiverServerId, sharingRequest.getBooksId());

		return new ResponseEntity(OK);
	}

	@GetMapping
	public ResponseEntity<?> sharedResources(@CurrentUserId Long userId) {
		User currentUser = userRepository.getOne(userId);
		List<Outsource> sharedResources = sharingService.getSharedResourcesByOwner(currentUser);

		List<SharedOutResourceDto> shared = sharedResources.stream()
				.flatMap(outsource -> sharedResourceMapper.toOutDto(outsource).stream())
				.collect(Collectors.toList());

		return ResponseEntity.ok(shared);
	}

	@GetMapping("/in")
	public ResponseEntity<?> sharedForMe(@CurrentUserId Long userId) {
		User currentUser = userRepository.getOne(userId);
		List<Outsource> sharedResources = sharingService.getSharedResources(currentUser.getLogin());

		List<SharedResourceDto> shared = sharedResources.stream()
				.flatMap(outsource -> sharedResourceMapper.toDto(outsource).stream())
				.collect(Collectors.toList());

		List<SharedResourceDto> sharedFromOtherServers = getSharedResourcesFromOtherServers(currentUser.getLogin());
		shared.addAll(sharedFromOtherServers);

		return ResponseEntity.ok(shared);
	}

	@GetMapping("/out/{serverId}/{userLogin}")
	public ResponseEntity<?> sharedTo(@PathVariable("serverId") Long serverId, @PathVariable("userLogin") String userLogin) {

		Server outServer = serverRepository.findByAssignedId(serverId)
				.filter(s -> s.getType() == OUTSOURCE)
				.filter(this::isServerAccepted)
				.orElseThrow(() -> new IllegalStateException("No server found with given Id"));

		List<Outsource> sharedResources = sharingService.getSharedResources(userLogin).stream()
				.filter(outsource -> outsource.getDestination().isPresent())
				.filter(outsource -> outsource.getDestination().get().equals(outServer))
				.collect(Collectors.toList());

		List<SharedResourceDto> sharedForEveryone = sharingService.getSharedResourcesForEveryone().stream()
				.filter(outsource -> sharedForServerOrForEveryone(outsource, outServer))
				.flatMap(outsource -> sharedResourceMapper.toDto(outsource).stream())
				.collect(Collectors.toList());

		List<SharedResourceDto> shared = sharedResources.stream()
				.flatMap(outsource -> sharedResourceMapper.toDto(outsource).stream())
				.collect(Collectors.toList());

		List<SharedResourceDto> out = new ArrayList<>();
		out.addAll(sharedForEveryone);
		out.addAll(shared);

		return ResponseEntity.ok(new ResourcesResponseWrapper(out));
	}

	private boolean isServerAccepted(Server s) {
		return "ACCEPTED".equals(s.getToken());
	}

	private List<SharedResourceDto> getSharedResourcesFromOtherServers(String username) {
		List<Server> servers = serverRepository.findAll().stream()
				.filter(server -> server.getType() == INSOURCE)
				.collect(Collectors.toList());

		RestTemplate restTemplate = new RestTemplate();
		return servers.stream()
				.map(server -> restTemplate.getForEntity(
						server.getIpAddress() + "/api/sharing/out/" + server.getAssignedId() + "/" + username,
						ResourcesResponseWrapper.class))
				.filter(response -> response.getStatusCode().is2xxSuccessful())
				.map(HttpEntity::getBody)
				.filter(Objects::nonNull)
				.map(ResourcesResponseWrapper::getSharedResourceDtos)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	private boolean sharedForServerOrForEveryone(Outsource outsource, Server destiantion) {
		if(outsource.getDestination().isPresent()) {
			return outsource.getDestination().get().getIpAddress().equals("ALL") ||
					outsource.getDestination().get().equals(destiantion);
		}
		return false;
	}
}
