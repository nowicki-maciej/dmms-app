package cf.dmms.app.web.api.sharing;

import cf.dmms.app.spi.sharing.OutResource;
import cf.dmms.app.spi.sharing.Outsource;
import cf.dmms.app.web.api.book.BookDto;
import cf.dmms.app.web.api.book.BookMapper;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SharedResourceMapper {

	private BookMapper bookMapper;

	public SharedResourceMapper(BookMapper bookMapper) {
		this.bookMapper = bookMapper;
	}

	public List<SharedResourceDto> toDto(Outsource outsource) {
		String owner = outsource.getOwner().getLogin();
		List<BookDto> sharedBooks = outsource.getSharedResources().stream()
				.map(OutResource::getBook)
				.map(bookMapper::toDto)
				.collect(Collectors.toList());

		return sharedBooks.stream()
				.map(book -> new SharedResourceDto(owner, book))
				.collect(Collectors.toList());
	}

	public List<SharedOutResourceDto> toOutDto(Outsource outsource) {
		String receiver = outsource.getReceiver();
		List<BookDto> sharedBooks = outsource.getSharedResources().stream()
				.map(OutResource::getBook)
				.map(bookMapper::toDto)
				.collect(Collectors.toList());

		return sharedBooks.stream()
				.map(book -> new SharedOutResourceDto(receiver, book))
				.collect(Collectors.toList());
	}

}
