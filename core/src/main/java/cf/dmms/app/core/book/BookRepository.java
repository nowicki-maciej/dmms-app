package cf.dmms.app.core.book;

import cf.dmms.app.spi.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findAllByOwnerId(Long ownerId);

	Book getOneByIdAndOwnerId(Long id, Long ownerId);

	void deleteByIdAndOwnerId(Long id, Long ownerId);
}
