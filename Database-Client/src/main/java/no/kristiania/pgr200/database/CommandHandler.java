package no.kristiania.pgr200.database;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Scanner;

public class CommandHandler {

	public static void main(String[] args) throws IOException {
		new CommandHandler().run(args);
	}

	public void run(String[] args) throws IOException {

		if (args.length == 0) {
			System.out.println("Here is a list of the arguments you can run as");
			commandList();
			System.exit(1);
		}

		String command = args[0];

		if (command.equals("add")) {
			addTalk();
		} else if (command.equals("list")) {
			listTalk();
		} else if (command.equals("show")) {
				showTalk();
		} else {
			System.err.println("Unknown argument");
		}
	}

	private void showTalk() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter ID on which talk you want to show");
		System.out.println(">");
		int talkId = sc.nextInt();
		try {
			new HttpRequest("localhost", 10080, "/api/show/talk/" + talkId).execute();

		} catch (IOException e) {
			e.printStackTrace();
		}
		sc.close();
	}

	private void listTalk() {
		try {
			new HttpRequest("localhost", 10080, "/api/list/talks").execute();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addTalk() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the title of your talk");
		System.out.println(">");
		String title = sc.nextLine();
		System.out.println("Enter the description of your talk");
		System.out.println(">");
		String desc = sc.nextLine();

		try {
			HttpPostRequest request = new HttpPostRequest("localhost", 10080, "/api/add/talk");
			request.getFormQuery().addParameter("title", title);
			request.getFormQuery().addParameter("description", desc);
			request.execute();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Talk successfully added!");
		sc.close();
	}

	public void commandList() {
		System.out.println(">add \n>list \n>show >update\n");
	}

}
