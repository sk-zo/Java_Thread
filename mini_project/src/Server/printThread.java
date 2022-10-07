package Server;


import java.util.Arrays;
import java.util.Random;

public class printThread extends Thread{
	SharedArea share;
	
	
	public printThread(SharedArea share) {
		super();
		this.share = share;
	}

	void print() {
		System.out.println("\n==================================================");
		for(int i=0; i<share.row; i++) {
			for (int j=0; j<share.column; j++) {
				System.out.print(share.back[i][j]);
			}System.out.println();
		}
		System.out.println("▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲");
	}
	
	public void run() {
		try {
			while(share.isOn) {
				synchronized(share) {
					print();
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
		}
	}
}
