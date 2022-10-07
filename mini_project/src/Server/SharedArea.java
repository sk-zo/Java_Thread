package Server;


import java.util.ArrayList;
import java.util.Arrays;

public class SharedArea {
	ArrayList<word_object>woArr;
	char[][] back;
	int row = 15;
	int column = 50;
	int score;
	boolean isOn;
	ArrayList<String> words;
	
	public SharedArea(ArrayList<String> words) {
		super();
		this.words = words;
		woArr = new ArrayList<word_object>();
		back = new char[row][column];
		for (int i=0; i<back.length; i++)
			Arrays.fill(back[i], ' ');
		score = 0;
		isOn = true;
	}
	
	void init() {
		woArr = new ArrayList<word_object>();
		back = new char[row][column];
		for (int i=0; i<back.length; i++)
			Arrays.fill(back[i], ' ');
		score = 0;
		isOn = true;
	}
}
