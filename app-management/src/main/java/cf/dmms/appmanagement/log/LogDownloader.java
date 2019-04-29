package cf.dmms.appmanagement.log;

import cf.dmms.appmanagement.tools.FilesArchiver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class LogDownloader {

	private String logsDirPath;

	public LogDownloader(String logsDirPath) {
		this.logsDirPath = logsDirPath;
	}

	public File getArchivedLogs() {
		try {
			return FilesArchiver.zipFiles(loadAllFiles());
		} catch (IOException e) {
			throw new IllegalStateException("Error occured during compresing logs.", e);
		}
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
