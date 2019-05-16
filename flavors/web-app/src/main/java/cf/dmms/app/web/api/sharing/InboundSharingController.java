package cf.dmms.app.web.api.sharing;

import cf.dmms.app.core.sharing.SharingService;
import cf.dmms.app.spi.server.Server;
import cf.dmms.app.core.server.ServerRepository;
import cf.dmms.app.spi.sharing.Outsource;
import cf.dmms.app.spi.user.User;
import cf.dmms.app.usermanagement.user.UserRepository;
import cf.dmms.app.web.resolver.CurrentUserId;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static cf.dmms.app.spi.server.ServerType.INSOURCE;

@RestController
@RequestMapping("/sharing/in")
public class InboundSharingController {

	private UserRepository userRepository;
	private SharingService sharingService;
	private SharedResourceMapper sharedResourceMapper;
	private ServerRepository serverRepository;

	public InboundSharingController(UserRepository userRepository, SharingService sharingService,
			SharedResourceMapper sharedResourceMapper, ServerRepository serverRepository) {
		this.userRepository = userRepository;
		this.sharingService = sharingService;
		this.sharedResourceMapper = sharedResourceMapper;
		this.serverRepository = serverRepository;
	}

	@GetMapping
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

	private List<SharedResourceDto> getSharedResourcesFromOtherServers(String username) {
		List<Server> servers = serverRepository.findAll().stream()
				.filter(server -> server.getType() == INSOURCE)
				.collect(Collectors.toList());

		RestTemplate restTemplate = new RestTemplate();
		return servers.stream()
				.map(server -> restTemplate.getForEntity(
						server.getIpAddress() + "/api/sharing/out/" + server.getAssignedId() + "/" + username,
						OutboundSharingController.ResourcesResponseWrapper.class))
				.filter(response -> response.getStatusCode().is2xxSuccessful())
				.map(HttpEntity::getBody)
				.map(resource -> resource.getSharedResourceDtos())
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

}
