package no.kristiania.pgr200.database;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpPostRequest {

	private String hostname;
	private int port;
	private String path;
	private String method = "POST";

	public HttpPostRequest(String hostname, int port, String path) {
		this.hostname = hostname;
		this.port = port;
		this.path = path;
	}

	public static void main(String[] args) throws IOException {
		HttpPostRequest request = new HttpPostRequest("localhost", 10080, "/api/add/talk");
		request.getFormQuery().addParameter("title", "Does this");
		request.getFormQuery().addParameter("description", "even work");
		request.execute();
	}

	public String getBody() {
		return formQuery.toString();
	}

	private HttpQuery formQuery = new HttpQuery();

	public HttpQuery getFormQuery() {
		return formQuery;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	private void writeRequest(OutputStream output) throws IOException {
		output.write(("POST " + path + " HTTP/1.1\r\n").getBytes());
		output.write(("Content-Type: application/x-www-form-urlencoded" + "\r\n").getBytes());
		output.write(("Content-Length: " + getFormQuery().toString().length()).getBytes());
		output.write("\r\n\r\n".getBytes());

		output.write((getFormQuery().toString()).getBytes());
	}

	public HttpResponse execute() throws IOException {
		try (Socket socket = new Socket(hostname, port)) {
			writeRequest(socket.getOutputStream());

			return new HttpResponse(socket);
		}

	}

	public void writeRequestLine(Socket socket) throws IOException {
		HttpIO.writeNextLine(socket.getOutputStream(), method + " " + path + " HTTP/1.1");
	}

	public String getUrl() {
		return "http://" + hostname + ":" + port + path;
	}

	public String getRequestLine() {
		return "POST" + getUrl() + " HTTP/1.1";
	}

	public HashMap<String, String> getContentLength() {
		HashMap<String, String> content = new HashMap<>();
		content.put("Content-Length", String.valueOf(getBody().length()));
		return content;
	}

	public Map<String, String> getHeaders() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Length", String.valueOf(getBody().length()));
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		return headers;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof HttpPostRequest)) {
			return false;
		}
		HttpPostRequest other = (HttpPostRequest) o;
		return Objects.equals(getRequestLine(), other.getRequestLine())
				&& Objects.equals(getFormQuery(), other.getFormQuery())
				&& Objects.equals(getHeaders(), other.getHeaders());

	}

	@Override
	public int hashCode() {
		return Objects.hash(getRequestLine(), getFormQuery(), getHeaders());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{getRequestLine()=" + getRequestLine() + "{getFormatQuery()="
				+ getFormQuery() + "{getHeaders()=" + getHeaders();
	}

}
