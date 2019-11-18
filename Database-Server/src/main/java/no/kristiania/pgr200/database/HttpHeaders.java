package no.kristiania.pgr200.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class HttpHeaders {

	private Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	public HttpHeaders put(String key, String value) {
		headers.put(key, value);
		return this;
	}

	public void writeTo(OutputStream outputStream) throws IOException {
		writeHeaders(outputStream);
		writeEmptyLine(outputStream);
	}

	public void writeEmptyLine(OutputStream outputStream) throws IOException {
		outputStream.write("\r\n".getBytes());
	}

	public void writeHeaders(OutputStream outputStream) throws IOException {
		for (Entry<String, String> entry : headers.entrySet()) {
			outputStream.write((entry.getKey() + ": " + entry.getValue() + "\r\n").getBytes());
		}
	}

	public void parseHeaderLine(String headerLine) throws UnsupportedEncodingException {
		int colonPos = headerLine.indexOf(':');
		String key = headerLine.substring(0, colonPos).trim();
		String value = headerLine.substring(colonPos + 1).trim();
		headers.put(key, value);
	}

	public void readHeaderLine(InputStream input) throws IOException {
		String headerLine;
		while (!(headerLine = HttpIO.readNextLine(input)).isEmpty()) {
			parseHeaderLine(headerLine);
		}

	}

	public String get(String key) {
		return headers.get(key);
	}

	public HttpHeaders setContentLength(int contentLength) {
		return put("Content-Length", String.valueOf(contentLength));
	}

	public int getContentLength() {
		String contentLength = get("Content-Length");
		return contentLength != null ? Integer.parseInt(contentLength) : -1;
	}

}
