package GUI;
import java.util.ArrayList;
import java.util.List;

public class Board {
	private List<Stone> stonelist;
	private int ynum = 0;
	private int xnum = 0;
	private int[][] direction = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
	
	//コンストラクタ
	public Board(int xnum, int ynum){
	    this.xnum = xnum;
	    this.ynum = ynum;
	  }
	
	//盤面の初期化
	public void prepare(){
	    this.stonelist = new ArrayList<>();
	    for (int y = 0; y < this.ynum; y++) {
	      for (int x = 0; x < this.xnum; x++) {
	        Stone stone = new Stone(x, y);
	        this.stonelist.add(stone);
	      }
	    }
	    this.putStone(3, 3, "○");
	    this.putStone(4, 4, "○");
	    this.putStone(3, 4, "●");
	    this.putStone(4, 3, "●");
	  }
	
	//そのマスのStoneクラスのオブジェクトを返す
	public Stone getStone(int y, int x) {
	    for(Stone stone : this.stonelist) {
	      int[] pos = stone.getPosition();
	      if (pos[0] == y && pos[1] == x) {
	        return stone;
	      }
	    }
	    return null;
	  }
	
	//石を置く操作
	public void putStone(int x, int y, String state){
	    Stone stone = this.getStone(x,y);
	    stone.setState(state);
	  }
	
	//stonelistを出力
	public List<Stone> getStonelist(){
		return stonelist;
	}
	
	//盤面を出力
	public void feature(){
	    String [][] board = new String[ynum][xnum];
	    for(Stone stone : this.stonelist){
	      int[] pos = stone.getPosition();
	      String state = stone.getState();
	      board[pos[1]][pos[0]] = state;
	    }
	    System.out.println("\n\tA\tB\tC\tD\tE\tF\tG\tH\n");
	    for (int y = 0; y < board.length; y++) {
	      System.out.print(y + "\t");
	      for (int x = 0 ;x <board[0].length; x++) {
	        String b = board[y][x];
	        System.out.print(b + "\t");
	      }
	      System.out.println("\n");
	    }
	  }
	
	//指定した場所に石をおき、裏返す
	public int turnover(int x, int y, boolean player, List<int[]> result) {
		boolean flag = false;
		Player play = new Player();
		String mine = play.indicateColor(player);
		String not_mine = play.indicateColor(!player);
		
		for (int i = 0; i < result.size(); i++) {
			int[] rev_stone = result.get(i);
			int rev_x = rev_stone[0];
			int rev_y = rev_stone[1];
			
			if (rev_x == x && rev_y == y) {
				putStone(rev_x, rev_y, mine);
				
				for(int[] dir: direction) {
					int search_x = rev_x;
					int search_y = rev_y;
					boolean reverse = false;
					List<int[]> rev = new ArrayList<>();
					
					for (int j = 1; j < 8; j++) {
						search_x += dir[0];
						search_y += dir[1];
						
						if (search_x < 0 || search_x > 7 || search_y < 0 || search_y > 7) {
							break;
						}
						int search_id = search_y * 8 + search_x;
						
						Stone neighboring_stone = stonelist.get(search_id);
						String neighboring_state = neighboring_stone.getState();
						
						if (neighboring_state == not_mine) {
							reverse = true;
							int[] neighboring_pos = {search_x, search_y};
							rev.add(neighboring_pos);
						}
						
						else if (neighboring_state == mine && reverse) {
							flag = true;
							for (int[] k: rev) {
								int pos_x = k[0];
								int pos_y = k[1];
								putStone(pos_x, pos_y, mine);
							}
							break;
						}
						
						else {
							break;
						}
					}
				}
				break;
			}
		}
		if (flag) {
			return 1;
		}
		else {
			System.out.println("指定した座標には置けません。もう一度入力してください。");
			return 0;
		}
	}
	
	//石を置ける座標を取得する
	public List<int[]> getCoordinate(boolean player) {
		Player play = new Player();
		String mine = play.indicateColor(player);
		String not_mine = play.indicateColor(!player);
		List <int[]> result = new ArrayList<>();
		
		for (int i = 0; i < stonelist.size(); i++) {
			Stone stone = stonelist.get(i);
			String state = stone.getState();
			if (state == mine) {
				int[] coordinate = stone.getPosition();
				int x = coordinate[0];
				int y = coordinate[1];
				
				for (int[] dir: direction) {
					boolean flag = false;
					int search_x = x;
					int search_y = y;
					
					for (int j = 0; j < 8; j++) {
						search_x += dir[0];
						search_y += dir[1];
						if (search_x < 0 || search_x > 7 || search_y < 0 || search_y > 7) {
							break;
						}
						
						int search_index = 8 * search_y + search_x;
						Stone neighboring_stone = stonelist.get(search_index);
						String neighboring_state = neighboring_stone.getState();
						
						if (neighboring_state == not_mine) {
							flag = true;
						}
						
						else if (neighboring_state == "・" && flag) {
							int[] neighboring_pos = {search_x, search_y};
							result.add(neighboring_pos);
							break;
						}
						
						else {
							break;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	//黒石の量を数える
	public int countBlack() {
		int count = 0;
		for (Stone stone: stonelist) {
			if (stone.getState() == "●") {
				count += 1;
			}
		}
		return count;
	}
	
	//白石の量を数える
		public int countWhite() {
			int count = 0;
			for (Stone stone: stonelist) {
				if (stone.getState() == "○") {
					count += 1;
				}
			}
			return count;
		}

}
