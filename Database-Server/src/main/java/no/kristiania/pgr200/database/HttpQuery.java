package no.kristiania.pgr200.database;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpQuery {

	private Map<String, String> parameters = new LinkedHashMap<>();
	private String query;

	public HttpQuery() {
	}

	public HttpQuery(String query) {
		for (String parameter : query.split("&")) {
			parseParameter(parameter);
		}
	}

	public String getQuery() {
		return query;
	}

	public HttpQuery addParameter(String paramName, String value) {
		parameters.put(paramName, value);
		return this;
	}

	public String retrieveParameter(String paramName) {
		return parameters.get(paramName);
	}

	public void parseParameter(String parameter) {
		int equalsPos = parameter.indexOf('=');
		if (equalsPos >= 0) {
			parameters.put(urlDecode(parameter.substring(0, equalsPos)), urlDecode(parameter.substring(equalsPos + 1)));
		}
	}

	private String urlEncode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 should always be supported", e);
		}
	}

	private String urlDecode(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 should always be supported", e);
		}
	}

	@Override
	public String toString() {
		if (parameters.isEmpty()) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		for (String paramName : parameters.keySet()) {
			if (result.length() > 0) {
				result.append("&");
			}
			result.append(urlEncode(paramName)).append("=").append(urlEncode(parameters.get(paramName)));
		}
		return result.toString();
	}
}