package cf.dmms.app.web.utils;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpHeadersBuilder {

	private HttpServletRequest request;
	private HttpHeaders headers;

	private HttpHeadersBuilder(HttpServletRequest request) {
		this.request = request;
		this.headers = new HttpHeaders();
	}

	public static HttpHeadersBuilder responseFor(HttpServletRequest request) {
		return new HttpHeadersBuilder(request);
	}

	public HttpHeadersBuilder contentType(String type) {
		headers.set(HttpHeaders.CONTENT_TYPE, type);
		return this;
	}

	public HttpHeadersBuilder attachment(String filename) {
		String encodedFilename = encode(filename);
		String userAgent = getUserAgent();

		String headerValuePrefix = "";
		if (userAgent.contains("IE") || userAgent.contains("Chrome")) {
			headerValuePrefix = "attachment; filename=";
		}

		if (userAgent.contains("Firefox")) {
			headerValuePrefix = "attachment; filename*=UTF-8''";
		}


		headers.set(HttpHeaders.CONTENT_DISPOSITION, headerValuePrefix + encodedFilename);
		return this;
	}

	public HttpHeaders build() {
		return headers;
	}

	private String encode(String name) {
		try {
			return URLEncoder.encode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	private String getUserAgent() {
		return request.getHeader("User-Agent");
	}
}
