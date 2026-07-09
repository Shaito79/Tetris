package Tetris;

//<<<<<<< HEAD
	
	import javax.swing.*;
	import java.awt.*;
//=======
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Iterator;

import javax.swing.JPanel;
//>>>>>>> branch 'master' of https://github.com/Shaito79/Tetris.git

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

	     
        setLayout(new GridLayout(3, 1));   // verteilen

        
        JLabel bereich1 = new JLabel("Bereich 1", SwingConstants.CENTER);
        	bereich1.setFont(new Font("Arial", Font.BOLD, 16));
        	add(bereich1);

        
        JLabel bereich2 = new JLabel("Bereich 2", SwingConstants.CENTER);
        	add(bereich2);

        
        JLabel bereich3 = new JLabel("Bereich 3", SwingConstants.CENTER);
        	add(bereich3);
	}

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

//	// exakt dieselbe Farbzuordnung wie in FieldPanel, damit die Vorschau
//	// zum gerade fallenden bzw. abgelegten Steinen passt
//	private Color getFarbe(int block) {
//		switch (block) {
//			case 1: return Color.CYAN;
//			case 2: return Color.YELLOW;
//			case 3: return Color.MAGENTA;
//			case 4: return Color.GREEN;
//			case 5: return Color.RED;
//			case 6: return Color.BLUE;
//			case 0: return Color.ORANGE;
//			default: return Color.BLACK;
//		}
//	}
}
