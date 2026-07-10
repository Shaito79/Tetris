package Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Diese Klasse rendert das Spielfeld
 * @author Ralf Bieller
 */
public class FieldPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ObjTree objTree;
	public int[] invisible;

	public static final int BLOCK_GROESSE = 30;

	/**
	 * Create the panel.
	 */
	
	public FieldPanel(ObjTree objTree) {
		this.objTree=objTree;
	    	setPreferredSize(new Dimension(
	    			objTree.getField().BREITE * BLOCK_GROESSE,
	    			objTree.getField().HOEHE * BLOCK_GROESSE));
	    }
	/**
	 * Diese methode zeichnet das Spielfeld neu
	 */
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    Field field = objTree.getField();

	    // Bereits abgelegte Blöcke zeichnen
	    for (int y = 2; y < field.HOEHE; y++) {

	    		for (int x = 0; x < field.BREITE; x++) {
	        	
	    			int block = field.getBlock(x, y);

	    			if ((block != -1)) {
	    				g.setColor(ColorScheme.getColor(block, objTree.getTimer().getLevel()));
	    				g.fillRect(
	                        x * BLOCK_GROESSE,
	                        (y - 2) * BLOCK_GROESSE,
	                        BLOCK_GROESSE,
	                        BLOCK_GROESSE);
	    			}

	    			g.setColor(Color.GRAY);
	    			g.drawRect(
	                    x * BLOCK_GROESSE,
	                    (y -2) * BLOCK_GROESSE,
	                    BLOCK_GROESSE,
	                    BLOCK_GROESSE);
	        }
	    	
	    }

	    // Aktuelles Tetromino zeichnen
	    int[][] piece = objTree.getTetromino().getPiece(
	            field.getType(),
	            field.getRotation());

	    g.setColor(ColorScheme.getColor(field.getType(), objTree.getTimer().getLevel()));

	    for (int ty = 0; ty < piece.length; ty++) {
	        for (int tx = 0; tx < piece[ty].length; tx++) {

	            if (piece[ty][tx] == 1) {

	                int gx = field.getX() + tx;
	                int gy = field.getY() + ty - 2;
	                if(gy >= 0) {
	                	g.fillRect(
	                        gx * BLOCK_GROESSE,
	                        gy * BLOCK_GROESSE,
	                        BLOCK_GROESSE,
	                        BLOCK_GROESSE);

	                	g.setColor(Color.GRAY);
	                	g.drawRect(
	                        gx * BLOCK_GROESSE,
	                        gy * BLOCK_GROESSE,
	                        BLOCK_GROESSE,
	                        BLOCK_GROESSE);

	                	g.setColor(ColorScheme.getColor(field.getType(), objTree.getTimer().getLevel()));
	                }
	            }
	        }
	    }
	}

	}
