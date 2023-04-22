package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Main extends JPanel{
	static final int WIDTH = 600; // 画面サイズ（幅）
    static final int HEIGHT = 650; // 画面サイズ（高さ）
    int left = 50;    // 左側余白
    int top = 100;   // 上側余白
    int bottom = 50; //下側余白
    int frame = 50; //マスの周囲の枠のサイズ
    int mark = 5;
    int cell = 50;    // マスのサイズ
    boolean player = true;
    int ban[] = {0,0,0,1,2,0,0,0}; // 盤面
    int sec;
    boolean start = false;
    
    Board board = new Board(8, 8);
    Player play = new Player();
    Timer timer;
    JLabel label1;
    JLabel label2;
    JButton button = new JButton("start");

	//コンストラクタ
	public Main() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(new MouseProc());
		setLayout(new FlowLayout());
		
		board.prepare(); //初期設定
		play.decideOrder(); //先攻と後攻をランダムに決定
		
		label1 = new JLabel();
		label1.setFont(new Font("メイリオ", Font.PLAIN, 20));
		add(label1);
		label1.setText("オセロを始めます。画面をクリックしてください。");
		start = true;
	}
	
	//画面描画
	public void paintComponent(Graphics g) {
		//背景
		g.setColor(new Color(255, 250, 240));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(new Color(63, 63, 63));
		g.fillRect(left, top, cell * 8 + frame * 2, cell * 8 + frame * 2);
		g.setColor(new Color(46, 139, 87));
		g.fillRect(left + frame, top + frame, cell * 8, cell * 8);
		
		//横線
		g.setColor(new Color(32, 32, 32));
		for (int i = 0; i < 9; i++) {
			int num = top + frame + i * cell;
			g.drawLine(left + frame, num, WIDTH - left - frame, num);
		}
		
		//縦線
		for (int i = 0; i < 9; i++) {
			int num = left + frame + i * cell;
			g.drawLine(num, top + frame, num, HEIGHT - bottom - frame);
		}
		
		//丸ぽち
		g.fillOval(left + frame + cell * 2 - mark / 2, top + frame + cell * 2 - mark / 2, mark, mark);
		g.fillOval(left + frame + cell * 6 - mark / 2, top + frame + cell * 2 - mark / 2, mark, mark);
		g.fillOval(left + frame + cell * 2 - mark / 2, top + frame + cell * 6 - mark / 2, mark, mark);
		g.fillOval(left + frame + cell * 6 - mark / 2, top + frame + cell * 6 - mark / 2, mark, mark);
		
		//盤面のコマを表示
		List<Stone> stonelist = board.getStonelist();
		for (Stone stone: stonelist) {
			String state = stone.getState();
			int[] position = stone.getPosition();
			int x = position[0];
			int y = position[1];
			if (state == "●") {
				g.setColor(new Color(32, 32, 32));
				g.fillOval(left + frame + cell / 10 + x * cell, top + frame + cell / 10 + y * cell, cell * 8 / 10, cell * 8 / 10);
			}
			else if (state == "○") {
				g.setColor(new Color(255, 250, 240));
				g.fillOval(left + frame + cell / 10 + x * cell, top + frame + cell / 10 + y * cell, cell * 8 / 10, cell * 8 / 10);
			}
		}
		
		//置ける場所表示
		List<int[]> positions = board.getCoordinate(player);
		for (int[] pos: positions) {
			int x = pos[0];
			int y = pos[1];
			g.setColor(new Color(211, 211, 211));
			g.fillRect(left + frame + cell / 10 + x * cell, top + frame + cell / 10 + y * cell, cell * 8 / 10, cell * 8 / 10);
		}
		
		List<int[]> positions_opp = board.getCoordinate(!player);
		if(positions.size() == 0 && positions_opp.size() == 0) {
			int black = board.countBlack();
			int white = board.countWhite();
			String[] order = play.getOrder();
			label2.setText(order[0] + "（黒石）：" + black + "個、" + order[1] + "（白石）：" + white + "個");
			if (black > white) {
				label1.setText(order[0] + "の勝ち！");
			}
			else if (black < white) {
				label1.setText(order[1] + "の勝ち！");
			}
			else {
				label1.setText( "引き分け！");
			}
		}
	}    
	
	public void computer() {
		//コンピュータの番
		
		List<int[]> mine = board.getCoordinate(player);
		if (mine.size() == 0) {
			label1.setText("コンピュータの番です。コマを置く場所がないのでパスします。");
			player = !player;
			repaint();
		}
		else {
			Random rand = new Random();
			int id;
			if (mine.size() > 0) {
				id = rand.nextInt(mine.size());
			}
			else {
				id = rand.nextInt(1);
			}
			int[] position = mine.get(id);
			int pos_x = position[0];
			int pos_y = position[1];
			board.turnover(pos_x, pos_y, player, mine);
			
			try {
				Thread.sleep(3000); // 1秒(1000ミリ秒)間だけ処理を止める
			} 
			catch (InterruptedException f) {
			}
			
			label1.setText("あなたの番です。コマを置くマスをクリックしてください。");
			player = !player;
			repaint();
		}
		
	}
	
	// クリックされた時の処理用のクラス
	class MouseProc extends MouseAdapter implements ActionListener{
		public void mouseClicked(MouseEvent e) {
			// ここにクリックされたときの処理を書く
			if (start) {
				System.out.println("スタート");
				button.setEnabled(false);
				String[] order = play.getOrder();
				label2 = new JLabel();
				label2.setFont(new Font("メイリオ", Font.PLAIN, 20));
				add(label2);
				label2.setText("先攻（黒石）： " + order[0] + "、後攻（白石）： " + order[1]);
				
				if (play.indicateOrder(player) == "コンピュータ") {
					label1.setText("コンピュータの番です。お待ちください。");
				}
				else {
					label1.setText("あなたの番です。コマを置くマスをクリックしてください。");
				}
				timer = new Timer(1000 , this);
				timer.start();
				start = false;
			}
			else if (play.indicateOrder(player) == "あなた") {
				//あなたの番
				int x = e.getX();
				int y = e.getY();
				// 盤の外側がクリックされたときは何もしないで終了
				if (x < left + frame) return;
				if (x >= left + frame + cell * 8) return;
				if (y < top + frame) return;
				if (y >= top + frame + cell * 8) return;
				// クリックされたマスを特定
				int col = (x - left - frame) / cell;
				int row = (y - top - frame) / cell;
				List<int[]> pos = board.getCoordinate(player);
				
				if (pos.size() == 0) {
					label1.setText("あなたの番です。コマを置く場所がないのでパスします。");
					player = !player;
					repaint();
				}
				else {
					int judge = board.turnover(col, row, player, pos);
					
					if (judge == 1) {
						player = !player;
						label1.setText("コンピュータの番です。お待ちください。");
						// 再描画
						repaint();
					}
				}
			}
		}
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == timer){
				if (sec >= 3){
					timer.stop();
					if (play.indicateOrder(player) == "コンピュータ") {
						computer();
					}
					sec = 0;
					timer.start();
				}
				else{
					sec++;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Othello"); 
		f.getContentPane().setLayout(new FlowLayout()); 
		Main main = new Main();
		f.getContentPane().add(main); 
		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		f.setVisible(true); 
	}

}
