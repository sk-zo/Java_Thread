package Server;


public class word_object {
	int length;
	int x;
	int y;
	String word;
	
	
	public word_object(String word, int x) {
		length = word.length();
		this.x = x;
		y = 0;
		this.word = word;
	}
	
	void fall() {
		y += 1;
	}
	
	String get_word() {
		return word;
	}
	
	int[] get_yx() {
		int []yx = {y, x}; 
		return yx;
	}
	
}
