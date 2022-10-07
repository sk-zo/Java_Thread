package Server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(10010);
			
			while (true) {
				Socket socket = serverSocket.accept();
				OracleDB db = new OracleDB();
				ArrayList<String> words = db.get_words();
				SharedArea share = new SharedArea(words);
				
				ServerThread st = new ServerThread(socket, share, db);
				st.start();
			}
			
		}  catch(Exception e2) {
			System.out.println(e2.getMessage());
		}
	}
}
