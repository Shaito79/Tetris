package Tetris;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;




public class Gui {
// Die Variable frame speichert dieses Fenster, damit man später Einstellungen daran vornehmen kann.
	private JFrame fenster;


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
		initialize();
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
		
		

		
		
		
		
		
		
		JPanel panel = new JPanel();
		fenster.add(panel);      // Panel in das Fenster einfügen
		
		panel.setLayout(null);
		
		// fügt das spielfeld ein
		ObjTree objTree = ObjTree.getObjTree();
		objTree.setField();

		FieldPanel fieldPanel = new FieldPanel(objTree);
		fieldPanel.setBounds(50, 100, 300, 600);
		panel.add(fieldPanel);
		
		
		
		
		JButton button = new JButton("AN");			// button erstellen
			button.setBounds(100, 20, 100, 40);		// posetion
			button.setBackground(Color.GREEN);
			panel.add(button); 						// Button in das Panel einfügen		

			button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true)); // kasten
			
		JButton button2 = new JButton ("PAUSE");
			button2.setBounds(200, 20, 100, 40);
			button2.setBackground(Color.red);
			panel.add(button2);

			button2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
						
			
	JPanel scoreBox = new JPanel();
			scoreBox.setLayout(null);              // damit  setBounds benutzen kannst
			scoreBox.setBounds(440, 20, 200, 60);  
			scoreBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
			panel.add(scoreBox);
			
		JLabel scoreN = new JLabel("SCORE      LVL");
			scoreN.setBounds(10, 10, 200, 20);
			scoreBox.add(scoreN);

		JLabel score = new JLabel("1250        4");
			score.setBounds(10, 35, 200, 20);
			scoreBox.add(score);

			
			
		
	JPanel highscoreBox = new JPanel();
			highscoreBox.setLayout(null);                     
			highscoreBox.setBounds(440, 100, 200,600);        
			highscoreBox.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3, true)); 
			highscoreBox.setBackground(Color.WHITE);          // Hintergrund sichtbar machen
			highscoreBox.setOpaque(true);
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

}
