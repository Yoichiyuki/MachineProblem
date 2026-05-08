import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    // Database file
    private static final String URL = "jdbc:sqlite:vault.db";

    // Connect to database
    public static Connection connect() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connected to database.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }



public static void insertData(String title, String username, String password) {

    String sql = "INSERT INTO vault(title, username, password) VALUES(?,?,?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, title);
        pstmt.setString(2, username);
        pstmt.setString(3, password);

        pstmt.executeUpdate();

        System.out.println("Data inserted.");

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

    public static void createTable() {

    String sql =
        "CREATE TABLE IF NOT EXISTS vault (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "title TEXT NOT NULL, " +
        "username TEXT NOT NULL, " +
        "password TEXT NOT NULL" +
        ");";

    try (
        Connection conn = connect();
        Statement stmt = conn.createStatement()
    ) {

        stmt.execute(sql);

        System.out.println("Table created.");

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }


}

}