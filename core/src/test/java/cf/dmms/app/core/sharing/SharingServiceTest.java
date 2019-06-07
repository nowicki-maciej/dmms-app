package cf.dmms.app.core.sharing;

import cf.dmms.app.core.book.BookRepository;
import cf.dmms.app.spi.server.Server;
import cf.dmms.app.spi.server.ServerRepository;
import cf.dmms.app.spi.server.ServerType;
import cf.dmms.app.spi.sharing.OutsourceRepository;
import cf.dmms.app.spi.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static cf.dmms.app.spi.user.Role.ROLE_ADMIN;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class SharingServiceTest {

	private static final Long LOCAL_ID = 0L;
	private static final Long EXTERNAL_ID = 1L;

	private SharingService sharingService;
	private final OutsourceRepository outsourceRepository = mock(OutsourceRepository.class);
	private final ServerRepository serverRepository = mock(ServerRepository.class);

	@Before
	public void setup() {
		sharingService = new SharingService(outsourceRepository, mock(BookRepository.class), serverRepository);
		when(serverRepository.findByAssignedId(EXTERNAL_ID)).thenReturn(Optional.of(new Server("ip", EXTERNAL_ID, ServerType.OUTSOURCE)));
	}

	@Test
	public void shouldShareWithLocalUser(){
		User user = new User("admin", "admin", "admin", "admin@admin", ROLE_ADMIN);
		sharingService.share(user, "user", LOCAL_ID, asList(1L));

		verify(outsourceRepository).save(any());
		verify(serverRepository, never()).findByAssignedId(LOCAL_ID);
	}

	@Test
	public void shouldShareWithExternalServerlUser(){
		User user = new User("admin", "admin", "admin", "admin@admin", ROLE_ADMIN);
		sharingService.share(user, "user", EXTERNAL_ID, asList(1L));

		verify(outsourceRepository).save(any());
		verify(serverRepository, times(1)).findByAssignedId(EXTERNAL_ID);
	}


}