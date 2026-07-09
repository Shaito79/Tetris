package Tetris;

import java.awt.Color;

public class ColorScheme {

	// 10 Schemen à 7 Farben (Index 0-6 entspricht den Blocktypen)
	private static final Color[][] SCHEMES = {

		// Level 1 - Klassisch (Original-Farben)
		{ Color.ORANGE, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.RED, Color.BLUE },

		// Level 2 - Kräftig
		{ new Color(255, 90, 0), new Color(0, 180, 255), new Color(255, 220, 0),
		  new Color(180, 0, 220), new Color(0, 200, 80), new Color(230, 0, 60),
		  new Color(40, 60, 220) },

		// Level 3 - Neon
		{ new Color(255, 110, 0), new Color(0, 255, 255), new Color(255, 255, 0),
		  new Color(255, 0, 255), new Color(0, 255, 100), new Color(255, 0, 90),
		  new Color(80, 80, 255) },

		// Level 4 - Erdtöne
		{ new Color(200, 120, 40), new Color(90, 150, 140), new Color(210, 180, 80),
		  new Color(150, 90, 110), new Color(110, 140, 70), new Color(170, 70, 50),
		  new Color(70, 90, 130) },

		// Level 5 - Ozean
		{ new Color(0, 130, 180), new Color(0, 200, 220), new Color(120, 220, 210),
		  new Color(60, 100, 200), new Color(0, 170, 140), new Color(30, 60, 150),
		  new Color(0, 90, 160) },

		// Level 6 - Sonnenuntergang
		{ new Color(255, 100, 0), new Color(255, 60, 90), new Color(255, 190, 0),
		  new Color(220, 40, 120), new Color(255, 140, 40), new Color(200, 20, 60),
		  new Color(150, 30, 90) },

		// Level 7 - Wald
		{ new Color(120, 80, 40), new Color(40, 120, 90), new Color(180, 200, 60),
		  new Color(90, 60, 30), new Color(30, 100, 40), new Color(140, 40, 30),
		  new Color(20, 70, 50) },

		// Level 8 - Pastellgrau
		{ new Color(200, 150, 130), new Color(150, 190, 200), new Color(210, 200, 150),
		  new Color(190, 150, 190), new Color(160, 190, 160), new Color(200, 140, 140),
		  new Color(140, 150, 190) },

		// Level 9 - Feuer & Eis
		{ new Color(255, 80, 0), new Color(0, 220, 255), new Color(255, 200, 0),
		  new Color(150, 0, 255), new Color(0, 255, 180), new Color(255, 0, 40),
		  new Color(0, 100, 255) },

		// Level 10 - Monochrom mit Akzent
		{ new Color(90, 90, 90), new Color(140, 140, 140), new Color(190, 190, 190),
		  new Color(220, 220, 220), new Color(60, 60, 60), new Color(255, 215, 0),
		  new Color(30, 30, 30) }
		  

	};

	/**
	 * Liefert die Farbe für einen Blocktyp im aktuellen Level.
	 * Ab Level 11 wird wieder von vorne begonnen (Level 11 = Schema 1, usw.)
	 *
	 * @param block Blocktyp (0-6), alles andere ergibt Schwarz
	 * @param level aktuelles Spiellevel (1-basiert)
	 */
	public static Color getColor(int block, int level) {

		if (block < 0 || block > 6) {
			return Color.BLACK;
		}

		int schemeIndex = (level - 1) % SCHEMES.length;
		if (schemeIndex < 0) {
			schemeIndex = 0;
		}

		return SCHEMES[schemeIndex][block];
	}
}