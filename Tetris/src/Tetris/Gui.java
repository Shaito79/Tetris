package Tetris;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;

import javax.swing.BorderFactory;

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
		 //             x.link position  y.position   breite    höhe des bildes
		fenster.setBounds(200,          200,               700,      800);
		// benden
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		


		
		
		
		
		
		
		JPanel panel = new JPanel();
		fenster.add(panel);      // Panel in das Fenster einfügen
		
		panel.setLayout(null);
		
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
						
			

JLabel LVL = new JLabel(" Score LVL");
			LVL.setBounds(450, 20, 200, 40);
			LVL.setBackground(Color.BLUE);

			panel.add(LVL);

			
		JLabel highscore = new JLabel("Highscore: 0 NAME DATUM ");
			highscore.setForeground(Color.BLACK);  
			highscore.setBounds(450, 60, 200, 60);  		
			panel.add(highscore);
			
			
			
			
			
		// highscore.setText("Highscore: " + punktzahl);			
			
			
			
		JTextField textField = new JTextField();
		
			
		
		
		/**
		 * 
		 * 
		 * 	score		lvl
		 * hi score 	name datum
		 */
		
		
		
	}

}
