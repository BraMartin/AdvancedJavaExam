package no.kristiania.pgr200.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

public class HttpClientTest {

	@Test
	public void shouldExecuteRequest() throws Exception {
		HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo");
		HttpResponse response = request.execute();

		assertThat(response.getStatusCode()).isEqualTo(200);
	}

	@Test
	public void shouldReadOtherStatusCodes() throws IOException {
		HttpRequest request = new HttpRequest("urlecho.appspot.com", 80,
				"/echo?" + new HttpQuery().addParameter("status", "404"));
		HttpResponse response = request.execute();

		assertThat(response.getStatusCode()).isEqualTo(404);
	}

	@Test
	public void shouldReadBody() throws IOException {
		HttpRequest request = new HttpRequest("urlecho.appspot.com", 80,
				"/echo?" + new HttpQuery().addParameter("body", "Hello World"));
		HttpResponse response = request.execute();

		assertThat(response.getBody()).isEqualTo("Hello World");
	}

	@Test
	public void shouldReadResponseHeaders() throws IOException {
		HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo?Location=http%3A%2F%2Fwww.google.com");
		HttpResponse response = request.execute();
		assertThat(response.getHeader("location")).isEqualTo("http://www.google.com");
	}

}