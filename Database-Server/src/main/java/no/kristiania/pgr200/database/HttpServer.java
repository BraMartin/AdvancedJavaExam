package no.kristiania.pgr200.database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

	private ServerSocket serverSocket;

	public HttpServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		start();
	}

	public static void main(String[] args) throws IOException {
		new HttpServer(10080);
	}

	public void start() throws IOException {
		new Thread(() -> {
			try {
				serverThread(serverSocket);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void serverThread(ServerSocket serverSocket) throws SQLException {
		while (true) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
				handleRequest(clientSocket);
			} catch (RuntimeException e) {
				if (clientSocket != null) {
					try {
						clientSocket.close();
					} catch (IOException ioEx) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void handleRequest(Socket clientSocket) throws IOException, SQLException {
		String statusCode;
		String body;

		HttpQuery query;
		HttpHeaders responseHeaders = new HttpHeaders();
		RequestHandler requestHandler = new RequestHandler();

		try {
			String requestLine = HttpIO.readNextLine(clientSocket.getInputStream());
			String requestTarget = requestLine.split(" ")[1];
			HttpPath path = new HttpPath(requestTarget);

			HttpHeaders headers = new HttpHeaders();
			headers.readHeaderLine(clientSocket.getInputStream());

			if (requestLine.split(" ")[0].equals("POST")) {
				query = new HttpQuery(HttpIO.readBody(clientSocket.getInputStream(), headers.getContentLength()));
				body = requestHandler.handlePostRequests(path, query);
			} else {
				query = path.query();
				body = requestHandler.handleGetRequests(path);

                System.out.println(body);


			}

			statusCode = query.retrieveParameter("status");
			if (statusCode == null) {
				statusCode = "200";
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			writeResponseLine(clientSocket, "500");
			responseHeaders.writeTo(clientSocket.getOutputStream());
			return;
		}

		writeResponseLine(clientSocket, statusCode);
		responseHeaders.put("Content-Type", "text/plain; charset=utf-8").setContentLength(body.length());
		query.retrieveParameter("Location");

		responseHeaders.writeTo(clientSocket.getOutputStream());

		clientSocket.getOutputStream().write(body.getBytes());
	}

	public int getPort() {
		return serverSocket.getLocalPort();
	}

	public void writeResponseLine(Socket clientSocket, String statusCode) throws IOException {
		String statusMessage = getStatusMessage(statusCode);
		HttpIO.writeNextLine(clientSocket.getOutputStream(), "HTTP/1.1 " + statusCode + " " + statusMessage);
	}

	private String getStatusMessage(String statusCode) {
		return statusMessages.get(statusCode);
	}

	private static Map<String, String> statusMessages = new HashMap<>();
	static {
		statusMessages.put("200", "OK");
		statusMessages.put("202", "Accepted");
		statusMessages.put("204", "No content");
		statusMessages.put("400", "Bad Request");
		statusMessages.put("401", "Unauthorized");
		statusMessages.put("403", "Forbidden");
		statusMessages.put("404", "Not Found");
		statusMessages.put("500", "Internal Server Error");
		statusMessages.put("502", "Bad gateway");
		statusMessages.put("505", "Unknown HTTP version");
	}

}
