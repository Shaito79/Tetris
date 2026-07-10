package Tetris;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.JOptionPane;

/**
 * Diese Klasse speichert alle bisher gefallenen und nicht gelöschten Steine.
 * Ausserdem steuert sie das aktuelle Tertomino
 * @author Ralf Bieller
 */

public class Field {

	private Random random = new Random();
	private ObjTree objTree;
	public final int BREITE = 10;
	public final int HOEHE = 22;
	/**
	 * @ ein field ist ein Datentyp, der abgelegte Steine im Tertis Feld speichert. Dazu wird das gesammte Spielfeld in einem 2 Dimensionalen Array repesentiert.
	 * -1 bedeuted leeres Feld
	 * 0 - 6 stellt die Farbe eines Steins dar.
	 */
	public int[][] field;
	/**
	 * Diese variablen Speichern den Ort(x und y) die Rotation (rot) sowie den Tetromno Typ(typ)
	 */
	private int x, y, type, rot;
	/**
	 * Hierin werden die nächsten 3 Steien gespeichert.
	 */
	private ArrayDeque<Integer> preview = new ArrayDeque<Integer>();

	
	public Field(ObjTree objTree) {
		this.objTree = objTree;
		field = new int[HOEHE][BREITE];
		for(int y=0;y<HOEHE;y++)
		    for(int x=0;x<BREITE;x++)
		        field[y][x]=-1;
	}
	public int getBlock(int x, int y) {
		int ergebnis = this.field[y][x];
		return ergebnis;
	}
	public int getX() {
	    return x;
	}

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public int getRotation() {
        return rot;
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
        int len = ergebnis.length;
        objTree.getScore().addScore(len);
        objTree.getScore().addRows(len);


		return ergebnis;
	}
    /**
     * Fügt 3 Tetrominos dem Preview hinzu. Diese Methode wird beim Start des Spieles ausgeführt
     */
	public void fillPreview() {
		addToPreview();
		addToPreview();
		addToPreview();
	}
	/**
	 * Fügt 1 Tetromino dem Preview hinzu
	 */
	private void addToPreview() {
		preview.add(random.nextInt(7));
		objTree.getPreview().repaint();
		
	}
	public ArrayDeque<Integer> getPreview() {
		return preview;
	}
	/**
	 * Diese Methode setzt ein neues Teromino. Es holt sich den Typ von Preview und erstellt Das Tetromino in den den beiden 
	 */
	public void putNewPiece() {
		type = preview.poll();
		addToPreview();
		x = 4;
		y = 0;
		rot = 0;
		if(collides(rot)) gameOver();
		objTree.getFieldPanel().repaint();
	}
	/**
	 * Diese methode beendet das Spiel, fragt den Spieler nach seinem Namen und speichert den Score 
	 */
	private synchronized void gameOver() {
		objTree.getTimer().pause();
		String name = JOptionPane.showInputDialog(objTree.getGui()  ,"Du hast verloren!\nAber gib deinen Namen ein, dann schauen wir ob du einen Highscore hast.");
		
		if (name == null || name.trim().isEmpty()) {
			name = "Anonym";
		}
		int points = objTree.getScore().getScore();
		int rows = objTree.getScore().getRows();

		DatabaseManager db = objTree.getDatabaseManager();
		db.connect();
		db.saveHighscore(name, rows, points);
		db.disconnect();

		objTree.getHighscoreWindow().reloadHighscores();
		objTree.getTimer().terminate();
	}
	/**
	 * Bewegt das Tetromino nach links
	 */
	public void moveLeft() {
		if(!collides(rot, x-1, y)) x--;
		objTree.getFieldPanel().repaint();
		
	}
	/**
	 * Bewegt das Tetromino nach rechts
	 */
	public void moveRight() {

		if(!collides(rot, x+1, y)) x++;
		objTree.getFieldPanel().repaint();
	}
	/**
	 * Diese Methode bewegt das aktuelle Tetromino ein Feld nach unten
	 * @return True falls unter dem Tetromino ein Stein ist
	 */
	public synchronized boolean moveOneDown() {
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
			objTree.getFieldPanel().repaint();
			return true;
		}
		else y++;
		objTree.getFieldPanel().repaint();
		return false;
		
	}
	public void dropDown() {
		
		while (!moveOneDown());
			
		
	}
	/**
	 * Diese Methode fügt das aktuelle Tetromino den abgelegten hinzu und prüft dannach auf 
	 * volle Reihen. Falls er welche findet werden diese gelöscht.
	 */
	public void setPiece() {
		// Tetromino dem Hintergrund hinzufügen
		int gx, gy;
		int[][] temp = objTree.getTetromino().getPiece(type, rot);
		for(int tx = 0; tx < temp[0].length; tx++) {
			for(int ty = 0; ty < temp.length; ty ++) {
				gx = x + tx;
				gy = y + ty;
				if (temp[ty][tx] == 1) field[gy][gx] = type;
			}
		}
		// auf volle Reihen testen
		int[] volleZeilen  = isFull();
		if(volleZeilen.length > 0) {
			objTree.getFieldPanel().repaint();
			objTree.getTimer().pause();
			for(int voll : volleZeilen) {
				int[][] newField = new int[HOEHE][BREITE];
				for (int i = HOEHE-1; i> voll; i--) {
					newField[i] = field[i];
				}
				for (int i = voll; i > 0; i--) {
					newField[i] = field[i-1];
					newField[0] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
				}
				this.field = newField;
			}
			objTree.getFieldPanel().repaint();
			objTree.getTimer().unpause();
		}
		putNewPiece();
		
	}
	/**
	 * Dies Methode dreht das aktuelle Teromino und prüft ob dadurch Kollisionen enstehen
	 */
	public void rotatePiece() {
		int neueRot = (rot + 1) %4;
		if(!collides(neueRot)) rot = neueRot;
		else if(wallKick(neueRot)) rot = neueRot;
		objTree.getFieldPanel().repaint();
		
	}
	/**
	 * Diese Methode versucht ein rotiertes Tetromino etwas nach liks oder rechts zu verschieben,
	 * drehen mölich wird.
	 * @param rot Die zu prüfende Rotation. Erlaubte Werte 0 - 3
	 * @return True für: Ich habe eine position gefunden in der ich das Teromino drehen kann.
	 * 		   False für: keine Position gefunden, drehen nicht möglich
	 */
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
	/**
	 * Diese Methode überprüft ob ein Tetromino mit andern Steinen oder dem Levelrand kollidiert
	 * @param rot Die zu testende Rotation. Erlaubte Werte: 0-3
	 * @param nx Der zu testende x wert für die Position
	 * @param ny Der zu testende y wert für die Position
	 * @return True für: kollidiert
	 * 		   False für: kollidiert nicht
	 */
	public boolean collides(int rot, int nx, int ny) {
		int[][] piece = objTree.getTetromino().getPiece(type, rot);
	    for (int ty = 0; ty < piece.length; ty++) {
	        for (int tx = 0; tx < piece[ty].length; tx++) {

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
	/**
	 * Diese Methode überprüft ob ein Tetromino mit andern Steinen oder dem Levelrand kollidiert
	 * @param rot Die zu testende Rotation. Erlaubte Werte: 0-3
	 * @return True für: kollidiert
	 * 		   False für: kollidiert nicht
	 */
    public boolean collides(int rot) {
        return collides(rot, this.x, this.y);
    }
}