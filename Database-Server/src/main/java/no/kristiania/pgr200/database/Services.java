package no.kristiania.pgr200.database;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;

public class Services {

	private DataSource dataSource;
	private ConferenceTalkDao talkDao;

	public Services() throws SQLException, IOException {
		this.dataSource = createDataSource();
		this.talkDao = new ConferenceTalkDao(dataSource);
	}

	public static DataSource createDataSource() throws FileNotFoundException, IOException {

		Properties prop = new Properties();
		try (FileReader reader = new FileReader("innlevering.properties")) {
			prop.load(reader);
		}

		String jdbcUrl = prop.getProperty("dataSource.url");
		String user = prop.getProperty("dataSource.user");
		String pwd = prop.getProperty("dataSource.password");

		PGPoolingDataSource dataSource = new PGPoolingDataSource();
		dataSource.setUrl(jdbcUrl);
		dataSource.setUser(user);
		dataSource.setPassword(pwd);

		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();

		return dataSource;
	}

	public ConferenceTalk postTalk(HttpQuery query) {
		ConferenceTalk talk = new ConferenceTalk();
		talk.setTitle(query.retrieveParameter("title"));
		talk.setDescription(query.retrieveParameter("description"));
		return talk;
	}

	public void postInsert(ConferenceTalk talk) throws SQLException {
		talkDao.insertTalk(talk);
	}

	public List<ConferenceTalk> getList() throws SQLException {
		return talkDao.listAll();
	}

	public List<ConferenceTalk> showTalk(int id) throws SQLException {
		return talkDao.listSpecificTalk(id);
	}

}
