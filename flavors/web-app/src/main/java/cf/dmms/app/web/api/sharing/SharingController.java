package cf.dmms.app.web.api.sharing;

import cf.dmms.app.core.sharing.SharingService;
import cf.dmms.app.spi.sharing.Outsource;
import cf.dmms.app.spi.user.User;
import cf.dmms.app.usermanagement.user.UserRepository;
import cf.dmms.app.web.resolver.CurrentUserId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/sharing")
public class SharingController {

	private UserRepository userRepository;
	private SharingService sharingService;
	private SharedResourceMapper sharedResourceMapper;

	public SharingController(UserRepository userRepository, SharingService sharingService,
			SharedResourceMapper sharedResourceMapper) {
		this.userRepository = userRepository;
		this.sharingService = sharingService;
		this.sharedResourceMapper = sharedResourceMapper;
	}

	@PostMapping
	public ResponseEntity share(@CurrentUserId Long userId, @Valid @RequestBody SharingRequest sharingRequest) {

		User currentUser = userRepository.getOne(userId);
		sharingService.share(currentUser, sharingRequest.getReceiver(), sharingRequest.getBooksId());

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

		return ResponseEntity.ok(shared);
	}



}
