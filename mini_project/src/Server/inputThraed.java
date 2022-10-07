package Server;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class inputThraed extends Thread{
	SharedArea share;
	BufferedReader reader;
	PrintWriter writer;
	
	public inputThraed(SharedArea share, BufferedReader reader, PrintWriter writer) {
		super();
		this.share = share;
		this.reader = reader;
		this.writer = writer;
	}
	
	void check(String s) {
		int score = 0;
		for (int i=0; i<share.woArr.size(); i++) {
			if (share.woArr.get(i).get_word().equals(s)) {
				share.woArr.remove(i);
				share.score += share.woArr.get(i).length;
			}
		}
	}
	
	public void run() {
		try {
			String s;
			while (share.isOn) {
				s = reader.readLine();
				writer.println();
				writer.flush();
				synchronized(share) {
					check(s);
				}Thread.sleep(100);
			} reader.readLine();
			writer.println("q!");
			writer.flush();
			reader.readLine();
		} catch (Exception e) {}
	}
}