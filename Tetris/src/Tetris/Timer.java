package Tetris;

public class Timer extends Thread{
	private int level = 1;
	private final int[] timing = {800, 715, 633, 550, 466, 383, 300, 216, 133, 100, 83, 83, 83, 66, 66, 66, 50, 50, 50, 33, 33, 33, 33, 33, 33, 33, 33, 33, 16};
	private boolean run = true;
	@Override
	public void run() {
		while(true) {
			while(!run) {}
			try {
				if (level >= timing.length)
					Thread.sleep(16);
				else
					Thread.sleep(timing[level]);
			} catch (Exception e) {
			}
			// TODO Stein soll ein Feld vor rücken
		}
	}

}
