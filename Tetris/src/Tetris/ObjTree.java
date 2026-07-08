package Tetris;

public class ObjTree {
	private static final ObjTree objTree = new ObjTree();
	private Timer timer;
	private Tetromino tetromino;
	private Field field;
	private Gui gui;
	private FieldPanel fieldPanel;
	private Score score;
	

	private ObjTree() {
	}
	
	public static ObjTree getObjTree() {
		return objTree;
	}
	public Gui getGui() {
		return gui;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	public FieldPanel getFieldPanel() {
		return fieldPanel;
	}

	public void setFieldPanel(FieldPanel fieldPanel) {
		this.fieldPanel = fieldPanel;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer() {
		this.timer = new Timer(objTree);
		
	}

	public Tetromino getTetromino() {
		if(tetromino == null ) tetromino = new Tetromino();
		return tetromino;
	}

	public void setTetromino() {
		this.tetromino = new Tetromino();
	}

	public Field getField() {
		return field;
	}

	public void setField() {
	    this.field = new Field(this);
	}
	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public void newGame() {
		this.timer = new Timer(objTree);
		this.tetromino = new Tetromino();
		this.field = new Field(objTree);
		this.score = new Score(objTree);
		field.putNewPiece();
		timer.start();
		timer.unpause();
		
	}


}
