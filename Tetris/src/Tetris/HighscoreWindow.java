package Tetris;

import java.awt.Font;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Diese Klasse rendert die High Score Liste , so dass sie im Hauptfenster eingebunden werden kann
 * @author Adis Aslani
 */

public class HighscoreWindow extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3602862331770763923L;
	private JTable tableHighscores;

    public HighscoreWindow() {
        initialize();
        loadHighscores();
    }

    /**
     * Initialisert das Panel
     */
    private void initialize() {
        setLayout(null);
        setBackground(new Color(255, 165, 0));  // farbe high..
        setOpaque(true);

        JLabel title = new JLabel("HIGHSCORE");
        title.setBounds(30, 8, 194, 30);
        title.setFont(new Font("Monospaced", Font.BOLD, 26));  // schrift
        
        add(title);

        tableHighscores = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableHighscores);
        // passend zur blauen Box (200 x 600), abzüglich Platz für Titel und Rahmen
        scrollPane.setBounds(0, 40, 194, 560);
        
        scrollPane.getViewport().setBackground(new Color(255, 165, 0));   //farb
        scrollPane.setBorder(null);

      /*  tableHighscores.setBackground(new Color(255, 165, 0));			//farb
        tableHighscores.setForeground(Color.WHITE);
      tableHighscores.setGridColor(Color.LIGHT_GRAY);
       */ 
        
        
        add(scrollPane);
    }
    /**
     * Lädt die aktuell Besten Spielergebnisse und rendert diese.
     */
    private void loadHighscores() {
        String[] columnNames = {"Platz", "Name", "Punkte", "Zeilen"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:tetris.db");
            String sql = "SELECT Name, punktzahl, zeilen_anzahl, zeitpunkt FROM highscores ORDER BY punktzahl DESC LIMIT 10";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            int platz = 1;
            while (rs.next()) {
                String name = rs.getString("Name");
                int punkte = rs.getInt("punktzahl");
                int zeilen = rs.getInt("zeilen_anzahl");
//                String datum = rs.getString("zeitpunkt");

//                if (datum != null && datum.contains(" ")) {
//                    datum = datum.substring(0, datum.indexOf(" "));
//                }

                model.addRow(new Object[]{platz, name, punkte, zeilen});
                platz++;
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Fehler beim Laden: " + e.getMessage());
            model.addRow(new Object[]{"-", "FEHLER", "---", "---", "---"});
        }

        tableHighscores.setModel(model);
    }

    // von aussen aufrufbar, z.B. nachdem Field.gameOver() einen neuen Highscore gespeichert hat
    public void reloadHighscores() {
        loadHighscores();
    }

}