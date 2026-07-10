package Tetris;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HighscoreWindow extends JPanel {

    private JTable tableHighscores;

    public HighscoreWindow() {
        initialize();
        loadHighscores();
    }

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

    public void newHighScore(Score score) {
        // 1. Dialog zur Namenseingabe öffnen
    	 String name;
    	do {
    		name = JOptionPane.showInputDialog(this, 
                "Game Over!\nDeine Punkte: " + score.getPoints() + "\nGib deinen Namen ein:");
    	} while (name != null && !name.trim().isEmpty());
        // 2. Prüfen, ob der User etwas eingegeben hat
  
            // Aktuelles Datum für SQLite formatieren
            String aktuellesDatum = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            String sql = "INSERT INTO highscores (Name, punktzahl, zeilen_anzahl, zeitpunkt) VALUES (?, ?, ?, ?)";
            
            // 3. In die Datenbank schreiben
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:tetris.db");
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, name.trim());
                pstmt.setInt(2, score.getPoints());
                pstmt.setInt(3, score.getRows());
                pstmt.setString(4, aktuellesDatum);
                
                pstmt.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println("Fehler beim Speichern des Highscores: " + e.getMessage());
            }
            
            // 4. Ansicht aktualisieren und Fenster anzeigen
            loadHighscores();
            setVisible(true);
        
    }

}