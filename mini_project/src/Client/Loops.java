package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Loops extends Thread{
	Scanner sc;
	String id;
	int highScore;
	OracleQuery oq;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	
	public Loops(Socket socket) {
		this.socket = socket;
		sc = new Scanner(System.in);
		oq = new OracleQuery();
		try {
			reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	int menu1() {
		System.out.println("-------------------------");
		System.out.println("\n\n\n1. Login \n\n2. Sign up\n\n3. Exit\n\n\n");
		System.out.println("-------------------------");
		System.out.print("Select >> ");
		String select = sc.next();
		if (!select.equals("1") && !select.equals("2") && !select.equals("3")) 
			return 0;
		System.out.println("\n");
		writer.println(select);
		writer.flush();
		
		return Integer.parseInt(select);
	}
	
	int menu2() {
		System.out.println("-------------------------");
		System.out.println("\n\n\n1. Logout \n\n2. Start\n\n3. My Records\n\n4. Leaderboard\n\n5. Exit\n\n\n");
		System.out.println("-------------------------");
		System.out.print("Select >> ");
		String select = sc.next();
		if (!select.equals("1") && !select.equals("2") && !select.equals("3") && !select.equals("4") && !select.equals("5")) 
			return 0;
		System.out.println("\n");
		writer.println(select);
		writer.flush();
		
		return Integer.parseInt(select);
		
	}
	
	public void run() {
		boolean loop = true;
		boolean loop1 = true;
		boolean loop2 = false;
		int n;
		try {
		while (loop) {
			while (loop1) {
				n = menu1();
				if (n == 0) continue;
				reader.readLine(); 
				switch (n) {
				case 1: 
					writer.println(oq.Login()); 
					writer.flush();
					String[] result = reader.readLine().split(",");
					if (result[0].equals("true")) {
						System.out.println("로그인 성공");
						System.out.println("Login Success");
						id = result[1];
						highScore = Integer.parseInt(result[2]);
						loop1 = false;
						loop2 = true;
					}
					break;
				case 2:
					writer.println(oq.Signup());
					writer.flush();
					reader.readLine();
					break;
				case 3:
					loop = loop1 = loop2 = false;
					System.out.println("Exit");
					break;
				}
			}
			while (loop2) {
				n = menu2(); 
				if (n == 0) continue;
				switch (n) {
				case 1:
					loop1 = true;
					loop2 = false;
					reader.readLine(); 
					id = null;
					highScore = 0;
					break;
				case 2:
					ClientThread cli = new ClientThread(reader, writer);
					cli.start();
					try {
						cli.join();
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					int score = Integer.parseInt(reader.readLine());
					if (highScore < score) {
						highScore = score;
						writer.println(oq.Update_Hidghscore(id, score));
						writer.flush();
						reader.readLine();
					}
					writer.println(oq.Update_Records(id, score));
					writer.flush();
					reader.readLine();
					break;
				case 3:
					writer.println(oq.user_records(id));
					writer.flush();
					reader.readLine();
					break;
				case 4:
					writer.println(oq.show_leaderboard());
					writer.flush();
					reader.readLine();
					break;
				case 5:
					loop = loop1 = loop2 = false;
					System.out.println("Exit");
					break;
				default:
					break;
				}
			}
		}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
