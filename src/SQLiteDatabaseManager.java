import java.util.List;

/*
 * ==========================================================
 * SQLITE DATABASE MANAGER
 * ==========================================================
 *
 * This class IMPLEMENTS the VaultRepository interface.
 *
 * Meaning:
 * - VaultRepository defines WHAT methods must exist.
 * - SQLiteDatabaseManager defines HOW those methods work.
 *
 * This is a great example of ABSTRACTION:
 * The rest of the program only knows about the interface,
 * and does not need to know that SQLite is used underneath.
 *
 * Example:
 *     VaultRepository repo = new SQLiteDatabaseManager();
 *
 *     repo.addEntry("Facebook", "john123", "mypassword");
 *
 * The variable 'repo' uses the interface type, while the
 * actual object is SQLiteDatabaseManager.
 */

public class SQLiteDatabaseManager implements VaultRepository {

    /*
     * ----------------------------------------------------------
     * ADD A NEW VAULT ENTRY
     * ----------------------------------------------------------
     *
     * This method fulfills the addEntry() method required by
     * the VaultRepository interface.
     *
     * Parameters:
     * - title:     Name of the service (e.g., "Facebook")
     * - username:  Account username or email
     * - password:  Stored password
     *
     * Instead of writing SQL here, we delegate the actual work
     * to DatabaseManager.insertData().
     */
    @Override
    public void addEntry(String title, String username, String password) {
        DatabaseManager.insertData(title, username, password);
    }

    /*
     * ----------------------------------------------------------
     * UPDATE AN EXISTING VAULT ENTRY
     * ----------------------------------------------------------
     *
     * Parameters:
     * - id:        Unique database ID of the entry
     * - title:     Updated service name
     * - username:  Updated username
     * - password:  Updated password
     *
     * Calls DatabaseManager.updateData() to execute the SQL
     * UPDATE statement.
     */
    @Override
    public void updateEntry(int id, String title, String username, String password) {
        DatabaseManager.updateData(id, title, username, password);
    }

    /*
     * ----------------------------------------------------------
     * DELETE AN ENTRY BY ID
     * ----------------------------------------------------------
     *
     * Our existing DatabaseManager only has deleteByTitle(),
     * not deleteById().
     *
     * So this method:
     * 1. Retrieves all entries.
     * 2. Searches for the entry with the matching ID.
     * 3. Extracts its title.
     * 4. Deletes the entry using that title.
     *
     * Note:
     * This works, but a better long-term solution is to create
     * DatabaseManager.deleteById(int id).
     */
    @Override
    public void deleteEntry(int id) {

        // Get all rows from the vault table.
        List<String[]> entries = DatabaseManager.getDataList();

        // Loop through every row.
        for (String[] row : entries) {

            // row[0] contains the ID as a String.
            // Convert it to int before comparing.
            if (Integer.parseInt(row[0]) == id) {

                // row[1] contains the title.
                // Use the existing deleteByTitle() method.
                DatabaseManager.deleteByTitle(row[1]);

                // Stop searching after deleting the matching entry.
                break;
            }
        }
    }

    /*
     * ----------------------------------------------------------
     * GET ALL ENTRIES
     * ----------------------------------------------------------
     *
     * Returns a list where each String[] represents one row:
     *
     * row[0] = id
     * row[1] = title
     * row[2] = username
     * row[3] = password
     *
     * This method simply delegates to DatabaseManager.
     */
    @Override
    public List<String[]> getAllEntries() {
        return DatabaseManager.getDataList();
    }

    /*
     * ----------------------------------------------------------
     * GET A SINGLE ENTRY BY ID
     * ----------------------------------------------------------
     *
     * Parameters:
     * - id: Database ID to search for.
     *
     * Process:
     * 1. Retrieve all entries.
     * 2. Loop through each row.
     * 3. Compare the row ID with the target ID.
     * 4. Return the matching row.
     *
     * Returns:
     * - String[] containing the row data if found.
     * - null if no matching entry exists.
     */
    @Override
    public String[] getEntryById(int id) {

        // Retrieve all rows from the database.
        List<String[]> entries = DatabaseManager.getDataList();

        // Search each row one by one.
        for (String[] row : entries) {

            // row[0] contains the ID as text.
            if (Integer.parseInt(row[0]) == id) {

                // Return the entire row:
                // [id, title, username, password]
                return row;
            }
        }

        // No matching ID found.
        return null;
    }
}