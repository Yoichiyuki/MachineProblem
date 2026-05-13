import java.util.List;

public interface VaultRepository {

    void addEntry(String title, String username, String password);

    void updateEntry(int id, String title, String username, String password);

    void deleteEntry(int id);

    List<String[]> getAllEntries();

    String[] getEntryById(int id);
}