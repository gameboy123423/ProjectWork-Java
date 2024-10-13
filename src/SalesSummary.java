import java.sql.*;
import javax.swing.JOptionPane;

public class SalesSummary {
    private static final String URL = "jdbc:sqlite:bot_database.db";


    // Метод для добавления продажи в таблицу sales
    public static void addSale(String goodsName, int amount, int price) {
        String sql = "INSERT INTO sales (goods_name, amount, price, date) VALUES (?, ?, ?, datetime('now'))";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, goodsName);
            pstmt.setInt(2, amount);
            pstmt.setInt(3, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения сводки продаж
    public static String getSalesSummary(String period) {
        String query = "";
        switch (period) {
            case "day":
                query = "SELECT SUM(amount * price) FROM sales s " +
                        "JOIN goods g ON s.goods_name = g.goods_name " +
                        "WHERE date >= date('now', 'localtime', 'start of day')";
                break;
            case "week":
                query = "SELECT SUM(amount * price) FROM sales s " +
                        "JOIN goods g ON s.goods_name = g.goods_name " +
                        "WHERE date >= date('now', 'localtime', 'start of week')";
                break;
            case "month":
                query = "SELECT SUM(amount * price) FROM sales s " +
                        "JOIN goods g ON s.goods_name = g.goods_name " +
                        "WHERE date >= date('now', 'localtime', 'start of month')";
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
