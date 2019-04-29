package cf.dmms.appmanagement.log;

import cf.dmms.appmanagement.tools.FileUnarchiver;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LogDownloaderTest {


	LogDownloader logDownloader;

	@Before
	public void setup() throws IOException {
		logDownloader = new LogDownloader(getFileDirFromResources("logs").getPath());
	}

	@Test
	public void test() throws IOException {

		File archive = logDownloader.getArchivedLogs();

		List<File> unarchived = FileUnarchiver.unarchive(archive);

		//TODO: finish it
//		assertThat(unarchived).containsOnly()

		System.out.println("done.");
	}


	private File getFileDirFromResources(String dirName) throws IOException {
		return new ClassPathResource(dirName).getFile();
	}

	private List<File> getFilesFromResources() throws IOException {

//		return new ClassPathResource(filename).getFile();
		return null;
	}

}