package app;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Main {
	public static void main(String args[]) {
		boolean player = true; //先攻：true, 後攻：false
		Board board = new Board(8,8);
		Player play = new Player();
		
		board.prepare(); //盤面の初期設定
		play.decideOrder();
		System.out.println("GM: オセロを始めます。");
		System.out.println();
		System.out.print("GM: 先攻と後攻を決定します");
		for (int i = 0; i < 3; i++) {
			try {
				System.out.print(".");
				Thread.sleep(1000); // 3秒間だけ処理を止める
			} 
			catch (InterruptedException e) {
			}
		}
		System.out.println();
		System.out.println();
		
		System.out.println("GM: 先攻・後攻を決定しました。");
		System.out.println("GM: 先攻(●)・" + play.indicateOrder(player) + "、後攻(○)・" + play.indicateOrder(!player) + "です");
		System.out.println();
		try {
			Thread.sleep(1000); // 3秒間だけ処理を止める
		} 
		catch (InterruptedException e) {
		}
		System.out.println("GM: それではゲームスタートです。");
		System.out.println();
		board.feature();
		System.out.println("-----------------------------");
		System.out.println();
		try {
			Thread.sleep(3000); // 3秒間だけ処理を止める
		} 
		catch (InterruptedException e) {
		}
		
		while (true) {
			List<int[]> mine = board.getCoordinate(player);
			List<int[]> not_mine = board.getCoordinate(!player);
			
			if (mine.size() == 0 & not_mine.size() == 0) {
				break;
			}
			
			String ord = play.indicateOrder(player); //順番を示す
			System.out.println("GM: " + ord + "の番です。(石： " + play.indicateColor(player) + ")");
			System.out.println();
			
			int x;
			int y;
			
			//置くところがなければパスを宣言
			if (mine.size() == 0) {
				board.feature();
				System.out.println("GM: 石を置く場所がありません。パスします。");
				System.out.println();
				System.out.println("-----------------------------");
				System.out.println();
				//プレイヤー交代
				player = !player;
			}
			else {
				//石を置く場所を入力
				while (true) {
					//石を置く場所を入力
					if (ord == "コンピュータ") {
						Random rand = new Random();
						int id;
						if (mine.size() > 0) {
							id = rand.nextInt(mine.size());
						}
						else {
							id = rand.nextInt(1);
						}
						
						int[] pos = mine.get(id);
						x = pos[0];
						y = pos[1];
						try {
							Thread.sleep(3000); // 3秒間だけ処理を止める
						} 
						catch (InterruptedException e) {
						}
						//System.out.println("コンピュータ: (" + x + ", " + y + ")に石を置きました。");
					}
					else {
						System.out.print("GM: 石を置く場所を入力してください (例 2B) : ");
						System.out.println();
						Scanner scanner = new Scanner(System.in);
						String input = scanner.nextLine();
						char x_input = input.charAt(1);
						char y_input = input.charAt(0);
						int[] input_coordinate = board.receiveInput(x_input, y_input);
						x = input_coordinate[0];
						y = input_coordinate[1];
					}
					
					//入力した場所に石を置く
					int judge = board.turnover(x, y, player, mine);
					if (judge == 1) {
						break;
					}
				}
				//石を置けたら盤を表示
				board.feature();
				System.out.println("-----------------------------");
				System.out.println();
				//プレイヤー交代
				player = !player;
				}
		
		}
		//ゲーム終了、勝敗を宣言
		System.out.println("ゲーム終了");
		board.feature();
		player = true;
		int black = board.countBlack();
		int white = board.countWhite();
		System.out.println("●：" + black + "個");
		System.out.println("○：" + white + "個");
		if (black > white) {
			System.out.println(play.indicateOrder(player) + "(●)の勝ち！");
		}
		else if (white > black) {
			System.out.println(play.indicateOrder(!player) + "(○)の勝ち！");
		}
		else {
			System.out.println("引き分け！");
		}
		//scanner.close();
	}
}
