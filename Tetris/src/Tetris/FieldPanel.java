package Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class FieldPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ObjTree objTree;
	private Field field;
	public static final int BLOCK_GROESSE = 30;

	/**
	 * Create the panel.
	 */
	
	public FieldPanel(ObjTree objTree) {
		this.field = objTree.getField();

	    	setPreferredSize(new Dimension(
	    			field.BREITE * BLOCK_GROESSE,
	                field.HOEHE * BLOCK_GROESSE));
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        for (int y = 0; y < field.HOEHE; y++) {
	            for (int x = 0; x < field.BREITE; x++) {

	                int block = field.getBlock(x,y);

	                if (block != -1) {
	                    g.setColor(getFarbe(block));
	                    g.fillRect(
	                            x * BLOCK_GROESSE,
	                            y * BLOCK_GROESSE,
	                            BLOCK_GROESSE,
	                            BLOCK_GROESSE);
	                }

	                // Gitter zeichnen
	                g.setColor(Color.GRAY);
	                g.drawRect(
	                        x * BLOCK_GROESSE,
	                        y * BLOCK_GROESSE,
	                        BLOCK_GROESSE,
	                        BLOCK_GROESSE);
	            }
	        }
	    }

	    private Color getFarbe(int block) {
	        switch (block) {
	            case 1: return Color.CYAN;
	            case 2: return Color.YELLOW;
	            case 3: return Color.MAGENTA;
	            case 4: return Color.GREEN;
	            case 5: return Color.RED;
	            case 6: return Color.BLUE;
	            case 7: return Color.ORANGE;
	            default: return Color.BLACK;
	        }
	    }
	}
