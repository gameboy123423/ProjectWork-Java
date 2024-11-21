import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChangeLogger {
    public static final String URL = "jdbc:sqlite:bot_database.db";

    // Создание таблицы log при инициализации
    static {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String createLogTableSQL = "CREATE TABLE IF NOT EXISTS changes_log (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "action TEXT, " + // Действие (например, создание, изменение)
                    "field_changed TEXT, " + // Поле, которое изменилось
                    "old_value TEXT, " +
                    "new_value TEXT, " +
                    "timestamp TEXT, " + // Время изменения
                    "description TEXT)"; // Дополнительное описание
            stmt.execute(createLogTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logChange(String actionType, String goodName, String oldValue, String newValue) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO changes_log (action, good_name, old_value, new_value, date) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, actionType);
            pstmt.setString(2, goodName);
            pstmt.setString(3, oldValue);
            pstmt.setString(4, newValue);
            pstmt.setString(5, date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
//Кнопка которая показывает созданные изменения не работает, как решишь ошибку удали все комменты