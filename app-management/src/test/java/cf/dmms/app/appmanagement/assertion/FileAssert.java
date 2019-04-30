package cf.dmms.app.appmanagement.assertion;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.fail;

public class FileAssert extends AbstractAssert<FileAssert, File> {

	private FileAssert(File file) {
		super(file, FileAssert.class);
	}

	public static FileAssert assertThat(File file) {
		return new FileAssert(file);
	}

	public FileAssert isEqualTo(File file) {
		isNotNull();
		try {
			Assertions.assertThat(readAllBytes(actual.toPath()))
					.isEqualTo(readAllBytes(file.toPath()));
		} catch (IOException e) {
			fail("There was an error during reading files");
		}
		return this;
	}
	
}
