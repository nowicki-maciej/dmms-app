package cf.dmms.app.core.book.storage;

import cf.dmms.app.core.book.Format;
import org.springframework.content.commons.repository.ContentStore;

public interface BookContentStore extends ContentStore<Format, String> {
}