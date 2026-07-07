package Tetris;

import java.util.ArrayList;
import java.util.Random;


public class Field {
	private Random random = new Random();
	private ObjTree objTree;
	public final int BREITE = 10;
	public final int HOEHE = 22;
	public int[][] field;
	private int x, y, type, rot;
	
	/**
	 * ein field ist ein Datentyp, der abgelegte Steine im Tertis Feld speichert. Dazu wird das gesammte Spielfeld in einem 2 Dimensionalen Array repesentiert.
	 * -1 bedeuted leeres Feld
	 * 0 - 6 stellt die Farbe eines Steins dar.
	 */
	
	public Field(ObjTree objTree) {
		this.objTree = objTree;
		field = new int[HOEHE][BREITE];
		for (int[] reihe : field) {
			for (int feld : reihe) {
				feld = -1;
			}
		}
	}
	public int getBlock(int x, int y) {
		int ergebnis = this.field[y][x];
		return ergebnis;
	}
	/**
	 * 
	 * @return int[] Dieser array speichert alle Reihen, die momentan voll sind, also bereit entfernt zu werden.
	 */
	public int[] isFull() {
		ArrayList<Integer> zwischenergebnis = new ArrayList<>();
		boolean isFull;
		for (int i = 0; i < HOEHE; i++) {
			int[] reihe = field[i];
			isFull = true;
			for (int feld: reihe) 
				isFull &= feld >= 0;
			if(isFull) zwischenergebnis.add(i);
		}
		int[] ergebnis = zwischenergebnis.stream().mapToInt(Integer::intValue).toArray();

		return ergebnis;
	}
	public void putNewPiece() {
		type = random.nextInt(8);
		x = 4;
		y = 0;
		rot = 0;
	}
	public void moveLeft() {
		if(x > 0) x--;
	}
	public void moveRight() {
		int[][] temp = objTree.getTetromino().getPiece(type, rot);
		int add = temp[0].length;
		if((x + add) < 10) x++;
	}
	public boolean moveOneDown() {
		boolean blockDarunter = false;
		int gx, gy;
		int[][] temp = objTree.getTetromino().getPiece(type, rot);
		for (int tx = 0; tx < temp[0].length && !blockDarunter; tx++) {
			for (int ty = temp.length - 1; ty >= 0; ty--) {
				if (temp[ty][tx] == 1 ) {
					gx = x + tx;
					gy = y + ty;
					if (gy + 1 >= field.length) {
						blockDarunter = true;
					}else if (field[gy + 1][gx] != -1) {
						blockDarunter = true;
					}
					break;
				}
			}
		}
		if (blockDarunter) {
			setPiece();
			return true;
		}
		else y++;
		return false;
		
	}
	public void dropDown() {
		boolean gedropped = false;
		while (y < 22) {
			if (moveOneDown()) break;
		}
	}
	public void setPiece() {
		int gx, gy;
		int[][] temp = objTree.getTetromino().getPiece(type, rot);
		for(int tx = 0; tx <= temp[0].length; tx++) {
			for(int ty = 0; ty <= temp.length; ty ++) {
				gx = x + tx;
				gy = y + ty;
				if (temp[ty][tx] == 1) field[gy][gx] = type;
			}
		}
		int[] volleZeilen  = isFull();
		if(volleZeilen.length > 0) {
			objTree.getTimer().pause();
			for(int voll : volleZeilen) {
				int[][] newField = new int[HOEHE][BREITE];
				for (int i = HOEHE; i> voll; i--) {
					newField[i] = field[i];
				}
				for (int i = voll; i >= 0; i--) {
					newField[i] = field[i-1];
					newField[0] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
				}
				this.field = newField;
			}
			objTree.getTimer().unpause();
		}
		
	}
	public void rotatePiece() {
		int neueRot = (rot + 1) %4;
		if(!collides(neueRot)) rot = neueRot;
		else if(wallKick(neueRot)) rot = neueRot;
		
	}
	public boolean wallKick(int rot) {
		int nx, ny;
		int[][] kicks = {
				{1,0},
				{-1,0},
				{2,0},
				{-2,0},
				{0,-1}
		};
		for(int[] kick: kicks) {
			nx = this.x + kick[0];
			ny = this.y + kick[1];
			if (!collides(rot, nx, ny)) {
				x = nx;
				y = ny;
				return true;
			}
			
		}
		return false;
	}
	public boolean collides(int rot, int nx, int ny) {
		int[][] piece = objTree.getTetromino().getPiece(type, rot);
	    for (int ty = 0; ty < piece.length; ty++) {
	        for (int tx = 0; tx < piece[ty].length; tx++) {

	            if (piece[ty][tx] == 0)
	                continue;

	            int gx = nx + tx;
	            int gy = ny + ty;

	            // Außerhalb des Spielfelds
	            if (gx < 0 || gx >= field[0].length)
	                return true;

	            if (gy < 0 || gy >= field.length)
	                return true;

	            // Bereits belegtes Feld
	            if (field[gy][gx] != -1)
	                return true;
	        }
	    }

	    return false;
		
	}
	public boolean collides(int rot) {
		return collides(rot, this.x, this.y);
	}

}
