import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SalesSummaryWindow extends JFrame {
    public SalesSummaryWindow(String period, String query) {
        setTitle("Продажи за " + period);
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Таблица с данными
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Название товара");
        tableModel.addColumn("Количество продано");
        tableModel.addColumn("Сумма продажи");
        tableModel.addColumn("Дата");

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        double totalSales = 0.0; // Переменная для общей суммы

        // Загрузка данных из таблицы goods
        try (Connection conn = DriverManager.getConnection(ChangeLogger.URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String goodName = rs.getString("goods_name");
                int soldAmount = rs.getInt("sold_amount");
                double soldPrice = rs.getDouble("sold_price");
                String date = rs.getString("date");

                // Добавляем строку данных в таблицу
                tableModel.addRow(new Object[]{goodName, soldAmount, soldPrice, date});

                // Суммируем продажи
                totalSales += soldPrice;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Добавляем итоговую строку в таблицу
        tableModel.addRow(new Object[]{"Итого", "", totalSales, ""});
    }
}
