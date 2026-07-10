package Tetris;
/**
 * Die Klasse, die alle Objekte referenziert.
 * @author Ralf Bieller
 */
public class ObjTree {
	private static final ObjTree objTree = new ObjTree();
	private Timer timer;
	private Tetromino tetromino;
	private Field field;
	private Gui gui;
	private FieldPanel fieldPanel;
	private Score score;
	private Preview preview;
	private ColorScheme colorScheme;
	private DatabaseManager databaseManager;

	
	private HighscoreWindow highscoreWindow;
	
	private ObjTree() {
	}
	
	public static ObjTree getObjTree() {
		return objTree;
	}
	public DatabaseManager getDatabaseManager() {
		if (databaseManager == null) databaseManager = new DatabaseManager();
		return databaseManager;
	}

	public Gui getGui() {
		return gui;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	public HighscoreWindow getHighscoreWindow() {
		if(highscoreWindow == null) highscoreWindow = new HighscoreWindow();
		return highscoreWindow;
	}

	public void setHighscoreWindow(HighscoreWindow highscoreWindow) {
		this.highscoreWindow = highscoreWindow;
	}

	public FieldPanel getFieldPanel() {
		return fieldPanel;
	}

	public void setFieldPanel(FieldPanel fieldPanel) {
		this.fieldPanel = fieldPanel;
	}

	public Timer getTimer() {
		if(timer == null) timer = new Timer(objTree);
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
		if (field == null) this.field = new Field(objTree);
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

	public ColorScheme getColorScheme() {
		return colorScheme;
	}

	public void newGame() {
		this.timer = new Timer(objTree);
		this.tetromino = new Tetromino();
		this.field = new Field(objTree);
		this.score = new Score(objTree);
		this.colorScheme = new ColorScheme();
		field.fillPreview();
		field.putNewPiece();
		timer.start();
		timer.unpause();
		
	}

	public Preview getPreview() {
		if (preview == null) this.preview = new Preview(objTree);
		return preview;
	}


}
