package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class OracleDB {
	Connection conn = null;
	String sql;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Scanner sc;
	// user_info
	String id;
	String pw;
	int highScore;
		
	public OracleDB() {
		sc = new Scanner(System.in);
		String url = "";
		String user = "", pw="";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	String[] input_IdPw() {
		System.out.print("ID : ");
		String id = sc.next();
		System.out.print("PW : ");
		String pw = sc.next();
		String[] id_pw = {id, pw};
		return id_pw;
	}
	
	void Signup(String[] id_pw) {
		sql = "select count(*) from user_info where user_id = '" + id_pw[0] + "'";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt(1) == 0) {
					sql = "insert into user_info(user_id, password) values('" + id_pw[0] + "', '" + id_pw[1] + "')";
					pstmt = conn.prepareStatement(sql);
					pstmt.executeUpdate();
					System.out.println("Sign Up Success");
				} else {
					System.out.println(id_pw[0] + " is already exists");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	String Login(String sql) {
		String result = "";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getString(1);
				pw = rs.getString(2);
				highScore = rs.getInt(3);
				result += ("true," + id + "," + Integer.toString(highScore));
				System.out.println("\n\n\n\n\n\n\n\nLogin Success\n\n\n\n\n\n\n\n");
			}
			else {
				result += "false,";
				System.out.println("\n\n\n\n\n\n\n\nLogin Fail\n\n\n\n\n\n\n\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}return result;
		
	}
	
	void Update_Hidghscore(String sql) {
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	void Update_Records(String sql) {
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	void show_leaderboard(String sql) {
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("\n\n\n     LEADERBOARD");
			System.out.println("=====================");
			System.out.println("RANK\tID\tSCORE");
			System.out.println("---------------------");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2)+ "\t" + rs.getInt(3));
			}System.out.println("=====================\n\n\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
//	JSONObject get_scoreboard(String sql) {
//		JSONObject scoreInfo = new JSONObject();
//		try {
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			List<String> scoreId = new ArrayList<String>();
//			List<Integer> scores = new ArrayList<Integer>();
//
//			while (rs.next()) {
//				scoreId.add(rs.getString(1));
//				scores.add(rs.getInt(2));
//			}
//
//			
//			scoreInfo.put("id", scoreId);
//			scoreInfo.put("score", scores);
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		} return scoreInfo;
//	}
	
	void Logout() {
		id = null;
		pw = null;
		highScore = 0;
		System.out.println("\n\n\n\n\n\n\n\nLogout Success\n\n\n\n\n\n\n\n");
	}
	
	boolean Withdrawal() {
		String[] id_pw = input_IdPw();
		sql = "select * from user_info where user_id = '" + id_pw[0] + "' and password = '" + id_pw[1] + "'";
		boolean withdraw = false;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				withdraw = true;
			}
			if (withdraw) {
				sql = "delete from user_info where user_id='" + id + "' and password='" + pw + "'";
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				System.out.println("Withdrawl user " + id);
			}
			else System.out.println("Withdrawl user Fail");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}return withdraw;
	}
	
	void user_records(String sql) {
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("\n\n\t\tMy Records");
			System.out.println("=========================================");
			System.out.println("\tTIMESTAMP\t\tSCORE");
			System.out.println("-----------------------------------------");
			while (rs.next()) {
				System.out.println((rs.getTimestamp(1) + "\t\t  " +  + rs.getInt(3)));
			} System.out.println("=========================================\n\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	void close() {
		try {
			if (!rs.isClosed())
				rs.close();
			if (!pstmt.isClosed()) pstmt.close();
			if(!conn.isClosed()) conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	ArrayList<String> get_words() {
		String sql = "select * from words sample(10)";
		ArrayList<String> words = new ArrayList<String>();
		try {
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			words.add(rs.getString(1));
		}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return words;
	}
	
	
}
