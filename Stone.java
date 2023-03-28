package app;

public class Stone {
	//参考 https://qiita.com/yonedaco/items/1dde224740885997d679
	private String state; // オセロの色が黒…B、白…W、空…E
    private int x;
    private int y;
    
  //コンストラクタ
    public Stone(int x, int y){
        this.state = "・";
        this.x = x;
        this.y = y;
    }
    
    //マスの状態を表す
    public String getState(){
        return this.state;
    }
    
    //マスの状態を更新する
    public void setState(String state){
        this.state = state;
    }

    //座標を返す
    public int[] getPosition(){
        int[] pos = {this.x, this.y};
        return pos;
    }

}
