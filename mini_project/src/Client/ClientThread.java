package Client;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class ClientThread extends Thread{
	BufferedReader reader;
	PrintWriter writer;
	Scanner sc;
	
	public ClientThread(BufferedReader reader, PrintWriter writer) {
		this.reader = reader;
		this.writer = writer;
		sc = new Scanner(System.in);
	}
	
	public void run() {
		String query;
		String answer;
		while (true) {
			try {
				query = sc.next();
				writer.println(query);
				writer.flush();
				answer = reader.readLine();
				if (answer.equals("q!")) {
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}writer.println("");
		writer.flush();
	}
}
