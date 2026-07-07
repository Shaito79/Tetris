package Tetris;

public class ObjTree {
	private static final ObjTree objTree = new ObjTree();
	private Timer timer;
	private Tetromino tetromino;
	private Field field;
	

	private ObjTree() {
	}
	
	public static ObjTree getObjTree() {
		return objTree;
	}
	public Timer getTimer() {
		return timer;
	}

	public void setTimer() {
		this.timer = new Timer();
		
	}

	public Tetromino getTetromino() {
		return tetromino;
	}

	public void setTetromino(Tetromino tetromino) {
		this.tetromino = new Tetromino();
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = new Field(this);
	}


}
