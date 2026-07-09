package Tetris;

	
	import javax.swing.*;
	import java.awt.*;

public class Preview extends JPanel {
	



	    public Preview() {

	        
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

}
