package Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Diese Klasse rendert die nächsten 3 erscheinenden Tetrominos
 * @author Ralf Bieller
 */

public class Preview extends JPanel {

	private static final long serialVersionUID = 1L;
	private ObjTree objTree;

	// kleinere Blockgröße als im Spielfeld, da hier nur eine Vorschau gezeigt wird
	public static final int BLOCK_GROESSE = 30;

	// Höhe, die einem einzelnen Vorschau-Slot zur Verfügung steht
	private static final int SLOT_HOEHE = 90;

	public Preview(ObjTree objTree) {
		this.objTree = objTree;
		setPreferredSize(new Dimension(200, 3 * SLOT_HOEHE));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
	}
	/**
	 * Diese Methode rendert das Preview Fenster neu
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Field field = objTree.getField();
		ArrayDeque<Integer> preview = field.getPreview();

		g.setColor(Color.BLACK);
		g.drawString("NEXT", 10, 15);

		int slot = 0;
		Iterator<Integer> it = preview.iterator();

		// zeigt maximal die nächsten 3 Teile an
		while (it.hasNext() && slot < 3) {

			int type = it.next();
			int[][] piece = objTree.getTetromino().getPiece(type, 0);

			// oberer Rand des aktuellen Slots (erster Slot etwas unterhalb der Überschrift)
			int offsetY = 25 + slot * SLOT_HOEHE;

			// Breite/Höhe der Piece-Matrix, um sie im Slot zu zentrieren
			int pieceBreite = piece[0].length * BLOCK_GROESSE;
			int pieceHoehe = piece.length * BLOCK_GROESSE;
			int offsetX = (getWidth() - pieceBreite) / 2;

			g.setColor(ColorScheme.getColor(type, objTree.getTimer().getLevel()));

			for (int ty = 0; ty < piece.length; ty++) {
				for (int tx = 0; tx < piece[ty].length; tx++) {

					if (piece[ty][tx] == 1) {

						int px = offsetX + tx * BLOCK_GROESSE;
						int py = offsetY + ty * BLOCK_GROESSE
								+ (SLOT_HOEHE - pieceHoehe) / 2;

						g.fillRect(px, py, BLOCK_GROESSE, BLOCK_GROESSE);

						g.setColor(Color.GRAY);
						g.drawRect(px, py, BLOCK_GROESSE, BLOCK_GROESSE);

						g.setColor(ColorScheme.getColor(type, objTree.getTimer().getLevel()));
					}
				}
			}

			slot++;
			}
		}
	}
	