package Tetris;

public class DatabaseTest {

    public static void main(String[] args) {
        System.out.println("--- Starte Highscore-Fenster ---");

        DatabaseManager db = new DatabaseManager();
        db.connect();
        db.createTableIfNotExists();
        db.disconnect();

     
        HighscoreWindow window = new HighscoreWindow();
        window.setVisible(true);
    }
}