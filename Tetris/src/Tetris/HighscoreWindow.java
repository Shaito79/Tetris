package Tetris;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HighscoreWindow extends JFrame {

    private JTable tableHighscores;

    public HighscoreWindow() {
        initialize();
        loadHighscores();
    }
    
    private void initialize() {
        setTitle("Tetris - Top 10 Highscores");
        setBounds(100, 100, 800, 600); // 800 Breit, 600 Hoch
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Schließt nur dieses Fenster, nicht das ganze Spiel
        getContentPane().setLayout(null); // ABSOLUTES LAYOUT

        // 2. Die Tabelle erstellen
        tableHighscores = new JTable();
        
        // 3. Ein ScrollPane (damit man scrollen kann, falls die Tabelle zu lang wird)
        JScrollPane scrollPane = new JScrollPane(tableHighscores);
        // Koordinaten: x=50, y=50, Breite=700, Höhe=450
        scrollPane.setBounds(50, 50, 700, 450); 
        
        // 4. Zum Fenster hinzufügen
        getContentPane().add(scrollPane);
    }

    private void loadHighscores() {
        // Spaltenüberschriften
        String[] columnNames = {"Platz", "Name", "Punkte", "Zeilen", "Datum"};
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
                String datum = rs.getString("zeitpunkt");
                
                if (datum != null && datum.contains(" ")) {
                    datum = datum.substring(0, datum.indexOf(" "));
                }
                
                model.addRow(new Object[]{platz, name, punkte, zeilen, datum});
                platz++;
            }
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            System.out.println("Fehler beim Laden: " + e.getMessage());
            // Falls Fehler, fügen wir einen Platzhalter ein, damit die Tabelle nicht leer ist
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