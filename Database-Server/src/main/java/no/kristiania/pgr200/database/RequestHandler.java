package no.kristiania.pgr200.database;

import java.io.IOException;
import java.sql.SQLException;

public class RequestHandler {

	private Services services;
	private int id;

	public RequestHandler() throws SQLException, IOException {
		this.services = new Services();

	}

	public String handlePostRequests(HttpPath path, HttpQuery query) throws SQLException {

		ConferenceTalk talk = new ConferenceTalk();

		if (path.getPath().equalsIgnoreCase("/api/add/talk")) {
			talk = services.postTalk(query);
			services.postInsert(talk);
		}
		return ">Please have api/add/talk as path with POST requests";
	}

	public String handleGetRequests(HttpPath path) throws SQLException {

		if (path.getPath().equalsIgnoreCase("/api/list/talks")) {

			return services.getList().toString();

		} else if (path.getPath().contains("/api/show/talk/")) {

			return services.showTalk(id).toString();

		}
		return ">Please have api/list/talks or api/show/talk in the path with GET requests";
	}

}
