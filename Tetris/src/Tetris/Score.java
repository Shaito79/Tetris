package Tetris;

public class Score {
	private int score = 0;
	private int rows = 0;
	private String name = "";
	private ObjTree objTree;
	
	public Score(ObjTree objTree) {
		this.objTree = objTree;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void addScore(int add) {
		int ergebnis = 0;
		for(int i = 1; i <= add; i++) {
			ergebnis += ergebnis + i;
		}
		ergebnis *= objTree.getTimer().getLevel();
		this.score += ergebnis;
		objTree.getGui().lblScore.setText(Integer.toString(this.score));
	}
	public void addRows(int add) {
		
		this.rows += add;
		objTree.getGui().lblRows.setText(Integer.toString(this.rows));
		if(objTree.getTimer().getLevel() < ((this.rows + 10)/10)) {
			objTree.getTimer().addLevel();
			objTree.getGui().lblLevel.setText(Integer.toString(objTree.getTimer().getLevel()));
		}
	}
	

}
