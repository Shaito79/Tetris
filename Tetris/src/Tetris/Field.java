package Tetris;

import java.util.ArrayList;

public class Field {
	private final int breite = 10;
	private final int hoehe = 20;
	public int[][] field;
	
	/**
	 * ein field ist ein Datentyp, der abgelegte Steine im Tertis Feld speichert. Dazu wird das gesammte Spielfeld in einem 2 Dimensionalen Array repesentiert.
	 * -1 bedeuted leeres Feld
	 * 0 - 6 stellt die Farbe eines Steins dar.
	 */
	
	public Field() {
		field = new int[hoehe][breite];
		for (int[] reihe : field) {
			for (int feld : reihe) {
				feld = -1;
			}
		}
	}
	/**
	 * 
	 * @return int[] Dieser array speichert alle Reihen, die momentan voll sind, also bereit entfernt zu werden.
	 */
	public int[] isFull() {
		ArrayList<Integer> zwischenergebnis = new ArrayList<>();
		boolean isFull;
		for (int i = 0; i < hoehe; i++) {
			int[] reihe = field[i];
			isFull = true;
			for (int feld: reihe) 
				isFull &= feld >= 0;
			if(isFull) zwischenergebnis.add(i);
		}
		int[] ergebnis = zwischenergebnis.stream()
                .mapToInt(Integer::intValue)
                .toArray();

		return ergebnis;
	}
	

}
