import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ChangesLogWindow extends JFrame {
    public ChangesLogWindow() {
        setTitle("Журнал изменений");
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Столбцы таблицы
        String[] columnNames = {"ID", "Действие", "Изменённое поле", "Старое значение", "Новое значение", "Дата и время", "Описание"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Заполнение таблицы
        try (Connection conn = DriverManager.getConnection(ChangeLogger.URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM changes_log ORDER BY timestamp DESC")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String action = rs.getString("action");
                String fieldChanged = rs.getString("field_changed");
                String oldValue = rs.getString("old_value");
                String newValue = rs.getString("new_value");
                String timestamp = rs.getString("timestamp");
                String description = rs.getString("description");

                model.addRow(new Object[]{id, action, fieldChanged, oldValue, newValue, timestamp, description});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных: " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

        // Таблица для отображения данных
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
