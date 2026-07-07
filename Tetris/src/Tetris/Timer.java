package Tetris;

public class Timer extends Thread{
	private ObjTree objTree;
	private int level = 1;
	private final int[] timing = {800, 715, 633, 550, 466, 383, 300, 216, 133, 100, 83, 83, 83, 66, 66, 66, 50, 50, 50, 33, 33, 33, 33, 33, 33, 33, 33, 33, 16};
	private boolean run = true;
	@Override
	public void run() {
		if (objTree == null) this.objTree = ObjTree.getObjTree();
		while(true) {
			try {
			while(!run) {
				Thread.sleep(10);
				}
			
				if (level >= timing.length)
					Thread.sleep(16);
				else
					Thread.sleep(timing[level - 1]);
			} catch (Exception e) {
			}
			objTree.getField().moveOneDown();
		}
	}
	public void pause() {
		run = false;
	}
	public void unpause() {
		run = true;
	}

}
