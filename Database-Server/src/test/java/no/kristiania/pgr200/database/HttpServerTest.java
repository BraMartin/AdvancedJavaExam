//package no.kristiania.pgr200.database;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.io.IOException;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public class HttpServerTest {
//
//	private static HttpServer server;
//
//	@BeforeClass
//	public static void startServer() throws IOException {
//		server = new HttpServer(0);
//		server.start();
//	}
//
//	@Test
//	public void shouldWriteStatusCode() throws IOException {
//
//		HttpRequest request = new HttpRequest("localhost", server.getPort(), "/echo?status=200");
//		HttpResponse response = request.execute();
//
//		assertThat(response.getStatusCode()).isEqualTo(200);
//	}
//
//	@Test
//	public void shouldReadOtherStatusCodes() throws IOException {
//		HttpRequest request = new HttpRequest("localhost", server.getPort(), "/echo?status=404");
//		HttpResponse response = request.execute();
//
//		assertThat(response.getStatusCode()).isEqualTo(404);
//	}
//
//
//}
