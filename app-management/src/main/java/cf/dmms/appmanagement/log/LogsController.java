package cf.dmms.appmanagement.log;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/logs")
public class LogsController {

	private static final String FILE_PREFIX = "DMMS-log_";
	private static final String DATE_PATTERN = "yyyyMMdd-HHmmss";

	private LogService logService;

	public LogsController(LogService logService) {
		this.logService = logService;
	}

	@GetMapping(value = "/download", produces = "application/zip")
	public ResponseEntity<byte[]> downloadLogs() {
		byte[] content = getArchivedLogsAsBytes();
		return new ResponseEntity<>(content, prepareHeaders(), OK);
	}

	private byte[] getArchivedLogsAsBytes() {
		try {
			return readAllBytes(logService.getArchivedLogs().toPath());
		} catch (IOException e) {
			throw new IllegalStateException("Error during packing logs.", e);
		}
	}

	private HttpHeaders prepareHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "inline;filename=" + generateFilename());
		return headers;
	}

	private String generateFilename() {
		return FILE_PREFIX + now().format(ofPattern(DATE_PATTERN)) + ".zip";
	}

}
