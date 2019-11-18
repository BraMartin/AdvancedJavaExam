package no.kristiania.pgr200.database;

public class HttpPath {

	private String path;
	private HttpQuery query = new HttpQuery();

	public HttpPath(String fullPath) {
		int questionPos = fullPath.indexOf('?');
		if (questionPos > 0) {
			this.path = fullPath.substring(0, questionPos);
			this.query = new HttpQuery(fullPath.substring(questionPos + 1));
		} else {
			this.path = fullPath;
			this.query = new HttpQuery();
		}
	}

	public String getPath() {
		return path;
	}

	public HttpQuery query() {
		return query;
	}

	public String getQuery() {
		return query.toString();
	}

	public String getParameter(String paramName) {
		return query.retrieveParameter(paramName);
	}

	public void setParameter(String paramName, String value) {
		query.addParameter(paramName, value);
	}

	@Override
	public String toString() {
		return getPath() + (getQuery() != null ? "?" + getQuery() : "");
	}

}