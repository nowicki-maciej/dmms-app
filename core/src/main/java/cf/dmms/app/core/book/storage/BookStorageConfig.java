package cf.dmms.app.core.book.storage;

import cf.dmms.app.core.book.Format;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.config.FilesystemStoreConfigurer;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
@EnableFilesystemStores
public class BookStorageConfig {

	@Value("${book.storage.dir}")
	String bookStorageDir;

	@Bean
	FileSystemResourceLoader storageRootDirResourceLoader() {
		return new FileSystemResourceLoader(bookStorageDir);
	}

	@Bean
	FilesystemStoreConfigurer filesystemStoreConfigurer() {
		return converterRegistry -> converterRegistry.addConverter(new BookFormatToPathConverter());
	}

	private static class BookFormatToPathConverter implements Converter<Format, String> {

		@Override
		public String convert(Format format) {
			return format.getFormat() + "/" +
					format.getStorageToken() + "." + format.getFormat().getSaveExtension();
		}
	}
}
