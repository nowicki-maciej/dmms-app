package cf.dmms.app.core.book;

import java.util.Arrays;
import java.util.List;

public enum MediaType {

	PDF("pdf"),
	MOBI("mobi", "prc"),
	EPUB("epub"),
	TXT("txt"),
	DOCX("docx"),
	KINDLE("azw");

	private List<String> extensions;

	MediaType(String... extensions) {
		this.extensions = Arrays.asList(extensions);
	}

	public List<String> getExtensions() {
		return extensions;
	}

	public String getSaveExtension() {
		return extensions.get(0);
	}
}

