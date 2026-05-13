import java.util.List;

public class SQLiteDatabaseManager implements VaultRepository {

    // =========================
    // CREATE ENTRY
    // =========================
    @Override
    public void addEntry(String title, String username, String password) {
        DatabaseManager.insertData(MainSession.userId, title, username, password);
    }

    // =========================
    // UPDATE ENTRY
    // =========================
    @Override
    public void updateEntry(int id, String title, String username, String password) {
        DatabaseManager.updateData(id, title, username, password);
    }

    // =========================
    // DELETE ENTRY
    // =========================
    @Override
    public void deleteEntry(int id) {
        DatabaseManager.deleteById(id);
    }

    // =========================
    // GET ALL
    // =========================
    @Override
    public List<String[]> getAllEntries() {
        return DatabaseManager.getDataList(MainSession.userId);
    }

    // =========================
    // GET BY ID
    // =========================
    @Override
    public String[] getEntryById(int id) {

        for (String[] row : getAllEntries()) {
            if (Integer.parseInt(row[0]) == id) {
                return row;
            }
        }

        return null;
    }
}