package cf.dmms.app.web.api.sharing;

import cf.dmms.app.spi.sharing.OutboundResource;
import cf.dmms.app.spi.sharing.Outsource;
import cf.dmms.app.web.api.book.BookDto;
import cf.dmms.app.web.api.book.BookMapper;
import org.springframework.stereotype.Controller;

import java.net.DatagramSocket;
import java.net.InetAddress;
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
				.map(OutboundResource::getBook)
				.map(bookMapper::toDto)
				.collect(Collectors.toList());

		String localIp = getServerIpAddress(outsource);

		return sharedBooks.stream()
				.map(book -> new SharedResourceDto(owner, book, localIp))
				.collect(Collectors.toList());
	}

	public List<SharedOutResourceDto> toOutDto(Outsource outsource) {
		String receiver = outsource.getReceiver();
		List<BookDto> sharedBooks = outsource.getSharedResources().stream()
				.map(OutboundResource::getBook)
				.map(bookMapper::toDto)
				.collect(Collectors.toList());

		return sharedBooks.stream()
				.map(book -> new SharedOutResourceDto(receiver, book, outsource.getDestinationServerIp()))
				.collect(Collectors.toList());
	}

	private String getServerIpAddress(Outsource outsource) {
		if(!outsource.getDestination().isPresent()) {
			return "local";
		}

		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			return socket.getLocalAddress().getHostAddress();
		}
		catch (Exception e) {
			throw new IllegalStateException("Some ip problems", e);
		}
	}

}
