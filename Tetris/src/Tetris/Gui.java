package Tetris;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.event.ActionListener;




public class Gui {
// Die Variable frame speichert dieses Fenster, damit man später Einstellungen daran vornehmen kann.
	private JFrame fenster;
	private JPanel panel;
	private ObjTree objTree;
	private FieldPanel fieldPanel ;
	public JLabel lblRows;
	public JLabel lblLevel;
	public JLabel lblScore;

	public static void main(String[] args) {
		
	/**
	 * verhindert, dass Fehler auftreten, wenn mehrere Threads gleichzeitig auf das Fenster zugreifen
	 */		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					// neues Objekt der Klasse Gui erstellt.
					Gui window = new Gui();
					window.fenster.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Diese Methode erstellt das Fenster.
	 */
	public Gui() {
		this.objTree = ObjTree.getObjTree();
		objTree.setGui(this);
		initialize();
		objTree.setFieldPanel(fieldPanel);
		initKeyBindings();
		
	}

	/**
	 * erstellt das Fenster.
	 */
	private void initialize() {
		fenster = new JFrame();
		 //             x.link position  y.position        breite    höhe des bildes
		fenster.setBounds(200,          200,               700,      800);
		// benden
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

		
		
		
		
		
		panel = new JPanel();
//		panel = new JPanel();
		fenster.getContentPane().add(panel);      // Panel in das Fenster einfügen
		
		panel.setLayout(null);
		
		// fügt das spielfeld ein
		objTree.setField();

		fieldPanel = new FieldPanel(objTree);
		fieldPanel.setBounds(50, 100, 300, 600);
		panel.add(fieldPanel);
		
		
		
		
		JButton btnNeuesSpiel = new JButton("Neues Spiel");			// button erstellen
		btnNeuesSpiel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objTree.newGame();
			}
		});
			btnNeuesSpiel.setBounds(100, 20, 100, 40);		// posetion
			btnNeuesSpiel.setBackground(Color.GREEN);
			panel.add(btnNeuesSpiel); 						// Button in das Panel einfügen		

			btnNeuesSpiel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true)); // kasten
			
		JButton button2 = new JButton ("PAUSE");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objTree.getTimer().switchPause();
			}
		});
			button2.setBounds(200, 20, 100, 40);
			button2.setBackground(Color.red);
			panel.add(button2);

			button2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
						
			
	JPanel scoreBox = new JPanel();
			scoreBox.setLayout(null);              // damit  setBounds benutzen kannst
			scoreBox.setBounds(440, 20, 200, 60);  
			scoreBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
			panel.add(scoreBox);
			
		JLabel scoreN = new JLabel("SCORE      LVL      ROWS");
			scoreN.setBounds(10, 10, 200, 20);
			scoreBox.add(scoreN);

			lblScore = new JLabel("0");
			lblScore.setBounds(10, 35, 39, 20);
			scoreBox.add(lblScore);
			
			lblLevel = new JLabel("1");
			lblLevel.setBounds(53, 35, 28, 20);
			scoreBox.add(lblLevel);
			
			lblRows = new JLabel("0");
			lblRows.setBounds(102, 40, 44, 12);
			scoreBox.add(lblRows);

			
			
		
			HighscoreWindow highscoreBox = new HighscoreWindow();
			highscoreBox.setBounds(440, 100, 200, 600);
			highscoreBox.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3, true));
			panel.add(highscoreBox);

			
		JLabel HIGHSCOR = new JLabel("HIGHSCORE");
			HIGHSCOR.setBounds(10, 5, 200, 20);
			highscoreBox.add(HIGHSCOR);

		
		JLabel NameD = new JLabel("sebastian   9999   07.07.2026");
			NameD.setBounds(10, 30, 200, 20);
			highscoreBox.add(NameD);
				


			
			
		/**						abstand						fenstergrösse
			fenster.setBounds(  200, 200,      			   700,     800);
		 * 						x	 y	      			   breite   höhe
		 *
			x = 200 → Abstand vom linken Bildschirmrand
			y = 200 → Abstand vom oberen Bildschirmrand
					breite = 700 → Fensterbreite
					höhe = 800 → Fensterhöhe
					
					
					
		// highscore.setText("Highscore: " + punktzahl);			
			
			
			

		/**
		 * 
		 * 
		 * 	score		lvl
		 * hi score 	name datum
		 */
		
		
		
	}
	private void initKeyBindings() {

	    InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	    ActionMap actionMap = panel.getActionMap();

	    // Links
	    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
	    actionMap.put("left", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            objTree.getField().moveLeft();
	        }
	    });

	    // Rechts
	    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
	    actionMap.put("right", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	objTree.getField().moveRight();
	        }
	    });

	    // Oben
	    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
	    actionMap.put("up", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            objTree.getField().rotatePiece();
	        }
	    });

	    // Unten
	    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
	    actionMap.put("down", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            objTree.getField().dropDown();
	        }
	    });
	}
}
