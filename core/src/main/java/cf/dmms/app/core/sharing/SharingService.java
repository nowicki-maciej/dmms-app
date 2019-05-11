package cf.dmms.app.core.sharing;

import cf.dmms.app.core.book.BookRepository;
import cf.dmms.app.spi.sharing.OutResource;
import cf.dmms.app.spi.sharing.Outsource;
import cf.dmms.app.spi.sharing.OutsourceRepository;
import cf.dmms.app.spi.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SharingService {

	private OutsourceRepository outsourceRepository;
	private BookRepository bookRepository;

	public SharingService(OutsourceRepository outsourceRepository, BookRepository bookRepository) {
		this.outsourceRepository = outsourceRepository;
		this.bookRepository = bookRepository;
	}

	public Outsource share(User owner, String receiver, List<Long> booksId) {
		Outsource outsource = new Outsource(owner, receiver);

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



}
