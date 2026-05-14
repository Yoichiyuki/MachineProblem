import java.sql.*;
import java.util.ArrayList;
import java.util.List;

 // With all honesty, this class is impossible to make without using AI. I have no idea how to use SQL in Java,
 // and I am still currently learning. So I just asked AI to do it for me.
 // Of course, I had to make some adjustments to the code, but the majority of it is AI-generated.

public class DatabaseManager {

    // =========================
    // DRIVER INITIALIZATION
    // =========================
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String URL = "jdbc:sqlite:vault.db";

    // =========================
    // CONNECTION
    // =========================
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // =========================
    // USERS TABLE
    // =========================
    public static void createUsersTable() {

        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL
            );
        """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            if (conn == null) return;
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // SIGN UP
    // =========================
    public static boolean signUp(String username, String passwordHash) {

        String sql = "INSERT INTO users(username, password_hash) VALUES(?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            ps.setString(1, username);
            ps.setString(2, passwordHash);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    // =========================
    // LOGIN CHECK
    // =========================
    public static boolean login(String username, String passwordHash) {

        String sql = "SELECT id FROM users WHERE username=? AND password_hash=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            ps.setString(1, username);
            ps.setString(2, passwordHash);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // GET USER ID
    // =========================
    public static int getUserId(String username) {

        String sql = "SELECT id FROM users WHERE username=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return -1;

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // =========================
    // USER COUNT (ADMIN FIX)
    // =========================
    public static int getUserCount() {

        String sql = "SELECT COUNT(*) AS total FROM users";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (conn == null) return 0;

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // =========================
    // VAULT TABLE
    // =========================
    public static void createTable() {

        String sql = """
            CREATE TABLE IF NOT EXISTS vault (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                title TEXT,
                username TEXT,
                password TEXT
            );
        """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            if (conn == null) return;
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // INSERT VAULT DATA
    // =========================
    public static void insertData(int userId, String title, String username, String password) {

        String sql = "INSERT INTO vault(user_id, title, username, password) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return;

            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, username);
            ps.setString(4, password);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // GET VAULT DATA (USER-SAFE)
    // =========================
    public static List<String[]> getDataList(int userId) {

        List<String[]> data = new ArrayList<>();

        String sql = "SELECT * FROM vault WHERE user_id=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return data;

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                data.add(new String[]{
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("username"),
                    rs.getString("password")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // =========================
    // UPDATE VAULT DATA
    // =========================
    public static void updateData(int id, String title, String username, String password) {

        String sql = "UPDATE vault SET title=?, username=?, password=? WHERE id=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setInt(4, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // DELETE VAULT DATA
    // =========================
    public static void deleteById(int id) {

        String sql = "DELETE FROM vault WHERE id=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}