package Tetris;



/**
 * Diese Klasse Speichert, das aktuelle Level und gibt ausserdem den Anstoß um das aktuelle Tetromino ein feld nach unten zu bewegen.
 * @author Ralf Bieller
 */
public class Timer extends Thread{
	private ObjTree objTree;
	private int level = 1;
	private final int[] timing = {
			800, 715, 633,
			550, 466, 383, 
			300, 216, 133, 
			100, 83, 83, 
			83, 66, 66, 
			66, 50, 50, 
			50, 33, 33, 
			33, 33, 33, 
			33, 33, 33, 
			33, 16};
	private volatile boolean run = true;
	private volatile boolean terminated = false;
	
	public Timer(ObjTree objTree){
	    this.objTree=objTree;
	}
	/**
	 * Diese Methode ist das Herz der Klasse sie kümmert sich um Pause sowie die Gravitation der Steine.
	 */
	@Override
	public void run() {
		if (objTree == null) this.objTree = ObjTree.getObjTree();
		while(!terminated) {
			try {
			while(!run  && !terminated) {
				Thread.sleep(10);
				}
				if(terminated) break;
				if (level >= timing.length)
					Thread.sleep(16);
				else
					Thread.sleep(timing[level - 1]);
			} catch (Exception e) {
			}
			if(!terminated && run) objTree.getField().moveOneDown();
		}
	}
	/**
	 * Dies beendet den Thread
	 */
	public void terminate() {
		terminated = true;
		run = true; // falls gerade pausiert, damit die innere Schleife nicht hängen bleibt
	}
	
	public int getLevel() {
		return level;
	}
	public void resetLevel() {
		level = 1;
	}
	public void addLevel() {
		level++;
	}
	
	public void pause() {
		run = false;
	}
	public void unpause() {
		run = true;
	}
	public void switchPause() {
		run = !run;
	}

}
