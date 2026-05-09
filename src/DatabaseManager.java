import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * ============================================
 * DATABASE MANAGER (FINAL CLEAN VERSION)
 * ============================================
 * - Uses SQLite safely
 * - No ResultSet leaks outside
 * - Returns clean List data
 */

public class DatabaseManager {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver load failed.");
            e.printStackTrace();
        }
    }

    private static final String URL = "jdbc:sqlite:vault.db";

    // =========================
    // CONNECT
    // =========================
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    // =========================
    // CREATE TABLE
    // =========================
    public static void createTable() {

        String sql = """
            CREATE TABLE IF NOT EXISTS vault (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                username TEXT NOT NULL,
                password TEXT NOT NULL
            );
        """;

        try (
            Connection conn = connect();
            Statement stmt = (conn != null) ? conn.createStatement() : null
        ) {
            if (conn == null) return;

            stmt.execute(sql);
            System.out.println("Table ready.");

        } catch (SQLException e) {
            System.out.println("Create table error: " + e.getMessage());
        }
    }

    // =========================
    // INSERT DATA
    // =========================
    public static void insertData(String title, String username, String password) {

        String sql = "INSERT INTO vault(title, username, password) VALUES(?,?,?)";

        try (
            Connection conn = connect();
            PreparedStatement pstmt =
                (conn != null) ? conn.prepareStatement(sql) : null
        ) {
            if (conn == null) return;

            pstmt.setString(1, title);
            pstmt.setString(2, username);
            pstmt.setString(3, password);

            pstmt.executeUpdate();
            System.out.println("Inserted.");

        } catch (SQLException e) {
            System.out.println("Insert error: " + e.getMessage());
        }
    }

    // =========================
    // GET DATA (ONLY METHOD YOU USE NOW)
    // =========================
    public static List<String[]> getDataList() {

        List<String[]> data = new ArrayList<>();

        String sql = "SELECT * FROM vault";

        try (
            Connection conn = connect();
            Statement stmt = (conn != null) ? conn.createStatement() : null;
            ResultSet rs = (stmt != null) ? stmt.executeQuery(sql) : null
        ) {

            if (conn == null || stmt == null || rs == null) return data;

            while (rs.next()) {
                data.add(new String[]{
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("username"),
                    rs.getString("password")
                });
            }

        } catch (SQLException e) {
            System.out.println("Fetch error: " + e.getMessage());
        }

        return data;
    }

    // =========================
// DELETE DATA BY TITLE
// =========================
    public static void deleteByTitle(String title) {

        String sql = "DELETE FROM vault WHERE title = ?";

        try (
            Connection conn = connect();
            PreparedStatement pstmt =
                (conn != null) ? conn.prepareStatement(sql) : null
        ) {
            // If connection failed, stop immediately
            if (conn == null) return;

            // Set the title parameter
            pstmt.setString(1, title);

            // Execute the delete statement
            int rowsAffected = pstmt.executeUpdate();

            // Optional: print result for debugging
            System.out.println(rowsAffected + " row(s) deleted.");

        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }

    public static void updateData(
        int id,
        String title,
        String username,
        String password
) {

    String sql = """
        UPDATE vault
        SET title = ?, username = ?, password = ?
        WHERE id = ?
    """;

    try (
        Connection conn = connect();
        PreparedStatement pstmt =
            (conn != null)
            ? conn.prepareStatement(sql)
            : null
    ) {

        if (conn == null) return;

        pstmt.setString(1, title);
        pstmt.setString(2, username);
        pstmt.setString(3, password);
        pstmt.setInt(4, id);

        pstmt.executeUpdate();

        System.out.println("Updated.");

    } catch (SQLException e) {

        System.out.println(
                "Update error: "
                + e.getMessage()
        );
    }
}

}