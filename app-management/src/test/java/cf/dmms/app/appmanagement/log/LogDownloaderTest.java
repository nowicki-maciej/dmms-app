package cf.dmms.app.appmanagement.log;

import cf.dmms.app.appmanagement.tools.FileUnarchiver;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static cf.dmms.app.appmanagement.assertion.FileAssert.assertThat;

public class LogDownloaderTest {

	private static final String LOG1 = "logs/log1";
	private static final String LOG2 = "logs/log2";

	private LogService logService;
	private List<File> unarchived;

	@Before
	public void setup() throws IOException {
		logService = new LogService(getFileFromResources("logs").getPath());
	}

	@Test
	public void shouldCreateCorrectLogsArchive() throws IOException {
		File archive = logService.getArchivedLogs();
		unarchived = FileUnarchiver.unarchive(archive);

		assertThat(getUnarchivedFileByName("log1"))
				.isEqualTo(getFileFromResources(LOG1));
		assertThat(getUnarchivedFileByName("log2"))
				.isEqualTo(getFileFromResources(LOG2));
	}

	private File getFileFromResources(String fileName) throws IOException {
		return new ClassPathResource(fileName).getFile();
	}

	private File getUnarchivedFileByName(String expectedFilename) {
		return unarchived.stream()
				.filter(file -> file.getName().equals(expectedFilename))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("There is no file with given name"));
	}

}