package Tetris;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet; // Neu importiert!

public class DatabaseManager {
	
	
	private Connection connection;
	
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC"); 
			
			String url = "jdbc:sqlite:tetris.db";
			
			connection = DriverManager.getConnection(url);
			System.out.println(">> Verbindung zur Datenbank erfolgreich!");
			
		} catch (ClassNotFoundException e) {
			System.out.println("!! SQLite Treiber nicht gefunden! Hast du die JAR Datei hinzugefügt?");
		} catch (SQLException e) {
			System.out.println("!! Fehler beim Verbinden: " + e.getMessage());
		}
	}
	
	public void disconnect() {
		try {
			if (connection != null) {
				connection.close();
				System.out.println(">> Datenbankverbindung geschlossen.");
			}
		} catch (SQLException e) {
			System.out.println("!! Fehler beim Schließen: " + e.getMessage());
		}
	}
	
	public void saveHighscore(String spielerName, int zeilenAnzahl, int punktzahl) {
		String sql = "INSERT INTO highscores (Name, zeilen_anzahl, punktzahl) VALUES (?, ?, ?)";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, spielerName);
			pstmt.setInt(2, zeilenAnzahl);
			pstmt.setInt(3, punktzahl);
			
			pstmt.executeUpdate();
			
			System.out.println(">> Highscore gespeichert: " + spielerName + " - "
					+ punktzahl + " Punkte (" + zeilenAnzahl + " Zeilen)");
			
		} catch (SQLException e) {
			System.out.println("!! Fehler beim Speichern: " + e.getMessage());
		}
	}


	public void printTopHighscores() {
		
		String sql = "SELECT Name, zeilen_anzahl, punktzahl, zeitpunkt FROM highscores ORDER BY punktzahl DESC LIMIT 10";
		
		System.out.println("\n--- TOP 10 HIGHSCORES ---");
		
		try (var stmt = connection.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			
			int platz = 1;
			while (rs.next()) {
				String name = rs.getString("Name");
				int zeilen = rs.getInt("zeilen_anzahl");
				int punkte = rs.getInt("punktzahl");
				String datum = rs.getString("zeitpunkt");
				
			
				if (datum != null && datum.contains(" ")) {
					datum = datum.substring(0, datum.indexOf(" "));
				}
				
				System.out.printf("%d. %-10s | Punkte: %-5d | Zeilen: %d | Datum: %s%n", 
						platz, name, punkte, zeilen, (datum != null ? datum : ""));
				platz++;
			}
			
			if (platz == 1) {
				System.out.println("Noch keine Highscores vorhanden.");
			}
			
		} catch (SQLException e) {
			System.out.println("!! Fehler beim Auslesen der Highscores: " + e.getMessage());
		}
	}

	public void createTableIfNotExists() {
		// TODO Auto-generated method stub
		
	}
}
