import java.sql.*;
import javax.swing.JOptionPane;

public class SalesSummary {
    private static final String URL = "jdbc:sqlite:bot_database.db";

    // Метод для добавления продажи в таблицу goods (обновляем данные о проданных товарах)
    public static void addSale(String goodsName, int amount, int price) {
        String selectSQL = "SELECT amount, sold_amount FROM goods WHERE goods_name = ?";
        String updateSQL = "UPDATE goods SET amount = ?, sold_amount = ?, sold_price = ?, date = datetime('now') WHERE goods_name = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {

            // Получаем текущее количество товара и проданных единиц
            selectStmt.setString(1, goodsName);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int currentAmount = rs.getInt("amount");
                int soldAmount = rs.getInt("sold_amount");

                // Проверяем, что на складе достаточно товара для продажи
                if (currentAmount >= amount) {
                    // Обновляем количество товара и данных о продаже
                    updateStmt.setInt(1, currentAmount - amount);
                    updateStmt.setInt(2, soldAmount + amount);  // Обновляем количество проданных товаров
                    updateStmt.setInt(3, price);  // Цена продажи
                    updateStmt.setString(4, goodsName);

                    updateStmt.executeUpdate();
                } else {
                    JOptionPane.showMessageDialog(null, "Недостаточно товара на складе для продажи!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Товар не найден!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения сводки продаж за указанный период
    public static String getSalesSummary(String period) {
        String query = "";
        switch (period) {
            case "day":
                query = "SELECT SUM(sold_amount * sold_price) FROM goods WHERE date >= date('now', 'localtime', 'start of day')";
                break;
            case "week":
                query = "SELECT SUM(sold_amount * sold_price) FROM goods WHERE date >= date('now', 'localtime', 'start of week')";
                break;
            case "month":
                query = "SELECT SUM(sold_amount * sold_price) FROM goods WHERE date >= date('now', 'localtime', 'start of month')";
                break;
            default:
                return "Некорректный период!";
        }

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                double totalSales = rs.getDouble(1);
                return "Продажи за " + period + ": " + (totalSales > 0 ? totalSales : 0.0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Нет продаж за этот период.";
    }

    // Метод для отображения сводки продаж в диалоговом окне
    public static void showSales(String period) {
        String salesSummary = getSalesSummary(period);
        JOptionPane.showMessageDialog(null, salesSummary, "Сводка продаж", JOptionPane.INFORMATION_MESSAGE);
    }
}
//Кнопка которая показывает созданные изменения не работает, как решишь ошибку удали все комменты