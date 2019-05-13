package cf.dmms.app.core.sharing;

import cf.dmms.app.core.book.BookRepository;
import cf.dmms.app.spi.server.Server;
import cf.dmms.app.spi.server.ServerRepository;
import cf.dmms.app.spi.server.ServerType;
import cf.dmms.app.spi.sharing.OutResource;
import cf.dmms.app.spi.sharing.Outsource;
import cf.dmms.app.spi.sharing.OutsourceRepository;
import cf.dmms.app.spi.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static cf.dmms.app.spi.server.ServerType.OUTSOURCE;

@Service
public class SharingService {

	private static final Long LOCALHOST_ID = -1L;

	private OutsourceRepository outsourceRepository;
	private BookRepository bookRepository;
	private ServerRepository serverRepository;

	public SharingService(OutsourceRepository outsourceRepository, BookRepository bookRepository, ServerRepository serverRepository) {
		this.outsourceRepository = outsourceRepository;
		this.bookRepository = bookRepository;
		this.serverRepository = serverRepository;
	}

	public Outsource share(User owner, String receiver, Long receiverServerId, List<Long> booksId) {
		Outsource outsource = createOutsource(owner, receiver, receiverServerId);

		List<OutResource> shared = booksId.stream()
				.map(bookId -> bookRepository.getOneByIdAndOwnerId(bookId, owner.getId()))
				.map(book -> new OutResource(book, outsource))
				.collect(Collectors.toList());

		outsource.setSharedResources(shared);
		return outsourceRepository.save(outsource);
	}

	public List<Outsource> getSharedResources(String login) {
		return outsourceRepository.findAllByReceiver(login);
	}

	public List<Outsource> getSharedResourcesByOwner(User user) {
		return outsourceRepository.findAllByOwner(user);
	}

	private Outsource createOutsource(User owner, String receiver, Long receiverServerId){
		if (receiverServerId.equals(LOCALHOST_ID)) {
			return new Outsource(owner, receiver);
		}
		Server server = serverRepository.findById(receiverServerId)
				.filter(s -> s.getType() == OUTSOURCE)
				.orElseThrow(() -> new IllegalArgumentException("Not found server with given Id"));

		return new Outsource(owner, receiver, server);
	}



}
