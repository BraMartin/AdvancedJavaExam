package no.kristiania.pgr200.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpIO {

	public static String readNextLine(InputStream input) throws IOException {
		StringBuilder nextLine = new StringBuilder();
		int c;
		while ((c = input.read()) != -1) {
			if (c == '\r') {
				input.read();
				break;
			}
			nextLine.append((char) c);
		}
		System.out.println(nextLine);
		return nextLine.toString();
	}

	public static String readBody(InputStream input, int contentLength) throws IOException {
		if (contentLength < 0) {
			return null;
		}
		StringBuilder content = new StringBuilder();

		int c;
		int remains = contentLength;

		while ((remains-- > 0) && (c = input.read()) != -1) {
			content.append((char) c);
		}
		return content.toString();

	}

	public static void writeNextLine(OutputStream output, String line) throws IOException {
		output.write((line + "\r\n").getBytes());
	}

}