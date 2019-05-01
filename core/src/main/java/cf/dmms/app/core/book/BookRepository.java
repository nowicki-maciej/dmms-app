package cf.dmms.app.core.book;

import cf.dmms.app.spi.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findAllByUserId(Long userId);

	Book getOneByIdAndUserId(Long id, Long userId);

	void deleteByIdAndUserId(Long id, Long userId);
}
