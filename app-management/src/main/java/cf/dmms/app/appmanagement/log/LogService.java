package cf.dmms.app.appmanagement.log;

import cf.dmms.app.appmanagement.tools.FilesArchiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

	private static final Logger log = LoggerFactory.getLogger(LogService.class);
	private String logsDirPath;

	LogService(@Value("${logsDirPath}") String logsDirPath) {
		this.logsDirPath = logsDirPath;
	}

	File getArchivedLogs() {
		log.info("Generating logs archive.");
		return FilesArchiver.zipFiles(loadAllFiles());
	}

	private List<File> loadAllFiles() {
		try {
			return Files.list(Paths.get(logsDirPath))
					.filter(Files::isRegularFile)
					.map(Path::toFile)
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new IllegalStateException("Error occurred during accessing log files.", e);
		}
	}
}
