package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	// Socket
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	SharedArea share;
	printThread wt;
	backUpdate bt;
	OracleDB db;
	String sql;

	public ServerThread() {
		super();
	}

	public ServerThread(Socket sock, SharedArea share, OracleDB db) {
		super();
		this.socket = sock;
		this.share = share;
		this.db = db;
		try {
			reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

	void loop() {
		boolean loop = true;
		boolean loop1 = true;
		boolean loop2 = false;
		int n;
		try {
			while (loop) {
				while (loop1) {
					n = Integer.parseInt(reader.readLine());
					writer.println(""); 
					writer.flush();
					switch (n) { 
					case 1:
						String loginAnswer = db.Login(reader.readLine());
						writer.println(loginAnswer);
						writer.flush();
						if (loginAnswer.split(",")[0].equals("true")) {
							loop1 = false;
							loop2 = true;
						}
						break;
					case 2:
						String answer = reader.readLine();
						String[] id_pw = answer.split(",");
						db.Signup(id_pw);
						writer.println();
						writer.flush();
						break;
					case 3:
						loop = loop1 = loop2 = false;
						System.out.println("Exit");
						break;
					}
				}
				while (loop2) { 
					n = Integer.parseInt(reader.readLine()); 
					switch (n) {
					case 1:
						writer.println("");
						writer.flush();
						db.Logout();
						loop1 = true;
						loop2 = false;
						break;
					case 2:
						printThread pt = new printThread(share);
						updateThread ut = new updateThread(share);
						inputThraed it = new inputThraed(share, reader, writer);
						pt.start();
						ut.start();
						it.start();
						try {
							it.join();
							ut.join();
							pt.join();
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
						writer.println(Integer.toString(share.score));
						writer.flush();
						String update = reader.readLine();
						if (update.startsWith("update")) {
							System.out.println("highscore");
							db.Update_Hidghscore(update);
							writer.println();
							writer.flush();
							update = reader.readLine();
						}
						db.Update_Records(update);
						writer.println();
						writer.flush();
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
						System.out.println("Score : " + share.score+"\n\n\n\n\n\n\n\n\n");
						share.init();
						break;
					case 3:
						db.user_records(reader.readLine());
						writer.println("");
						writer.flush();
						break;
					case 4:
						db.show_leaderboard(reader.readLine());
						writer.println("");
						writer.flush();
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void run() {
		try {
			loop();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
