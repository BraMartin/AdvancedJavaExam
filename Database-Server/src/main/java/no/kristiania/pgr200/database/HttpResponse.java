package no.kristiania.pgr200.database;

import java.io.IOException;
import java.net.Socket;

public class HttpResponse {

	private int statusCode;
	private String body;
	private String statusText;
	private HttpHeaders responseHeaders = new HttpHeaders();

	public HttpResponse(Socket socket) throws IOException {
		parseStatusLine(HttpIO.readNextLine(socket.getInputStream()));
		responseHeaders.readHeaderLine(socket.getInputStream());
		body = HttpIO.readBody(socket.getInputStream(), responseHeaders.getContentLength());
	}

	public void parseStatusLine(String statusLine) {
		int firstSpacePos = statusLine.indexOf(' ');
		int secondSpacePos = statusLine.indexOf(' ', firstSpacePos + 1);
		this.statusCode = Integer.parseInt(statusLine.substring(firstSpacePos + 1, secondSpacePos));
		this.statusText = statusLine.substring(secondSpacePos + 1);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	public String getHeader(String headerName) {
		return responseHeaders.get(headerName);
	}

	public String getBody() {
		return body;
	}

}