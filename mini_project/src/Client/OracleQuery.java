package Client;
import java.util.Scanner;

public class OracleQuery {
	Scanner sc;
	String sql;
	
	public OracleQuery() {
		sc = new Scanner(System.in);
	}
	
	String[] input_IdPw() {
		System.out.print("ID : ");
		String id = sc.next();
		System.out.print("PW : ");
		String pw = sc.next();
		System.out.println("\n\n\n\n\n\n\n\n");
		String[] id_pw = {id, pw};
		return id_pw;
	}
	
	String Signup() {
		String[] id_pw = input_IdPw();
		String sql = id_pw[0] + "," + id_pw[1];
		return sql;
	}
	
	String Login() {
		String[] id_pw = input_IdPw();
		sql = "select * from user_info where user_id = '" + id_pw[0] + "' and password = '" + id_pw[1] + "'";
		return sql;
	}
	
	String Update_Hidghscore(String id, int score) {
		return sql = "update user_info set high_score=" + score + " where user_id='" + id + "'";
	}
	
	String Update_Records(String id, int score) {
		sql = "insert into user_records(user_id, score) values('" + id +"', " + score + ")";
		return sql;
	}
	
	String show_leaderboard() {
		sql = "select rownum, user_id, high_score from (select user_id, high_score from user_info order by high_score desc)";
		return sql;	
	}
	
	String user_records(String id) {
		sql = "select * from user_records where user_id = '" + id + "' order by record_date";
		return sql;
	}
}
