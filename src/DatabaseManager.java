import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * ============================================
 * DATABASE MANAGER (WITH USER AUTHENTICATION)
 * ============================================
 *
 * Features:
 * - SQLite connection handling
 * - Creates:
 *      1. users table   (for login/signup)
 *      2. vault table   (for saved passwords)
 * - User authentication methods:
 *      - signUp()
 *      - login()
 *      - getUserId()
 * - Existing vault CRUD methods remain intact
 *
 * Recommended startup:
 *      DatabaseManager.createUsersTable();
 *      DatabaseManager.createTable();
 */

public class DatabaseManager {

    // =========================
    // LOAD SQLITE JDBC DRIVER
    // =========================
    static {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver load failed.");
            e.printStackTrace();
        }
    }

    // =========================
    // DATABASE FILE
    // =========================
    private static final String URL = "jdbc:sqlite:vault.db";

    // =========================
    // CONNECT TO DATABASE
    // =========================
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    // ==========================================================
    // USERS TABLE
    // ==========================================================

    // =========================
    // CREATE USERS TABLE
    // =========================
    public static void createUsersTable() {

        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password_hash TEXT NOT NULL
            );
        """;

        try (
            Connection conn = connect();
            Statement stmt = (conn != null) ? conn.createStatement() : null
        ) {
            if (conn == null) return;

            stmt.execute(sql);
            System.out.println("Users table ready.");

        } catch (SQLException e) {
            System.out.println("Create users table error: " + e.getMessage());
        }
    }

    // =========================
    // SIGN UP NEW USER
    // Returns true if successful
    // Returns false if username already exists
    // =========================
    public static boolean signUp(String username, String passwordHash) {

        String sql = """
            INSERT INTO users(username, password_hash)
            VALUES(?, ?)
        """;

        try (
            Connection conn = connect();
            PreparedStatement pstmt =
                (conn != null) ? conn.prepareStatement(sql) : null
        ) {
            if (conn == null) return false;

            pstmt.setString(1, username);
            pstmt.setString(2, passwordHash);

            pstmt.executeUpdate();

            System.out.println("User created.");
            return true;

        } catch (SQLException e) {
            // UNIQUE constraint failed -> username already exists
            System.out.println("Sign up error: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // LOGIN CHECK
    // Returns true if username/password match
    // =========================
    public static boolean login(String username, String passwordHash) {

        String sql = """
            SELECT id
            FROM users
            WHERE username = ?
              AND password_hash = ?
        """;

        try (
            Connection conn = connect();
            PreparedStatement pstmt =
                (conn != null) ? conn.prepareStatement(sql) : null
        ) {
            if (conn == null) return false;

            pstmt.setString(1, username);
            pstmt.setString(2, passwordHash);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // true if a matching row exists
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // GET USER ID BY USERNAME
    // Returns user ID or -1 if not found
    // =========================
    public static int getUserId(String username) {

        String sql = """
            SELECT id
            FROM users
            WHERE username = ?
        """;

        try (
            Connection conn = connect();
            PreparedStatement pstmt =
                (conn != null) ? conn.prepareStatement(sql) : null
        ) {
            if (conn == null) return -1;

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            System.out.println("Get user ID error: " + e.getMessage());
        }

        return -1;
    }

    // ==========================================================
    // VAULT TABLE
    // ==========================================================

    // =========================
    // CREATE VAULT TABLE
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
            System.out.println("Vault table ready.");

        } catch (SQLException e) {
            System.out.println("Create table error: " + e.getMessage());
        }
    }

    // =========================
    // INSERT VAULT DATA
    // =========================
    public static void insertData(
        String title,
        String username,
        String password
    ) {

        String sql = """
            INSERT INTO vault(title, username, password)
            VALUES(?, ?, ?)
        """;

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
    // GET ALL VAULT DATA
    // =========================
    public static List<String[]> getDataList() {

        List<String[]> data = new ArrayList<>();

        String sql = "SELECT * FROM vault";

        try (
            Connection conn = connect();
            Statement stmt =
                (conn != null) ? conn.createStatement() : null;
            ResultSet rs =
                (stmt != null) ? stmt.executeQuery(sql) : null
        ) {
            if (conn == null || stmt == null || rs == null) {
                return data;
            }

            while (rs.next()) {
                data.add(new String[] {
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
    // DELETE VAULT ITEM BY TITLE
    // =========================
    public static void deleteByTitle(String title) {

        String sql = "DELETE FROM vault WHERE title = ?";

        try (
            Connection conn = connect();
            PreparedStatement pstmt =
                (conn != null) ? conn.prepareStatement(sql) : null
        ) {
            if (conn == null) return;

            pstmt.setString(1, title);

            int rowsAffected = pstmt.executeUpdate();

            System.out.println(rowsAffected + " row(s) deleted.");

        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }

    // =========================
    // UPDATE VAULT ITEM
    // =========================
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
            System.out.println("Update error: " + e.getMessage());
        }
    }
}