package CUI;

import java.util.Random;

public class Player {
	private String[] order = new String[2];
	
	//コンストラクタ
	public Player() {
		
	}
	
	//順番を決める
	public void decideOrder() {
		Random rand = new Random();
		int num = rand.nextInt(2);
		if (num == 0) {
			order[0] = "コンピュータ";
			order[1] = "あなた";
		}
		else {
			order[0] = "あなた";
			order[1] = "コンピュータ";
		}
	}
	
	//色を指定する
	public String indicateColor(boolean player) {
		if (player) {
			return "●";
		}
		else {
			return "○";
		}
	}
	
	//順番を示す
	public String indicateOrder(boolean player) {
		if (player) {
			return order[0];
		}
		else {
			return order[1];
		}
	}
	
}
