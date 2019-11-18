package no.kristiania.pgr200.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class HttpPostRequestTest {

	@Test
	public void shouldGenerateRequest() {
		HttpPostRequest request = new HttpPostRequest("urlecho.org", 80, "/EtEller/Annet");
		request.getFormQuery().addParameter("melding", "foobar");

		assertThat(request.getUrl()).isEqualTo("http://urlecho.org:80/EtEller/Annet");
		assertThat(request.getBody()).isEqualTo("melding=foobar");

		assertThat(request.getRequestLine()).isEqualTo("POST" + request.getUrl() + " HTTP/1.1");
	}

	@Test
	public void shouldHaveDifferentEquals() {
		HttpPostRequest request1 = new HttpPostRequest("urlecho.org", 10080, "noe/greier");
		request1.getFormQuery().addParameter("melding", "barfoo");

		HttpPostRequest requestWithOtherUrl = new HttpPostRequest("urlecho.org", 80, "noe/greier");
		requestWithOtherUrl.getFormQuery().addParameter("melding", "barfoo");

		HttpPostRequest requestWithOtherParams = new HttpPostRequest("urlecho.org", 80, "noe/greier");
		requestWithOtherParams.getFormQuery().addParameter("melding", "barfoo");

		assertThat(request1).isNotEqualTo(requestWithOtherUrl).isNotEqualTo(requestWithOtherParams);
	}

}
