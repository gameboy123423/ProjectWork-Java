import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class GoodsDatabase {
    private static final String URL = "jdbc:sqlite:bot_database.db";

    // Создание таблицы при инициализации
    static {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            // Установка режима WAL для избежания блокировок
            stmt.execute("PRAGMA journal_mode=WAL;");

            // Создание таблицы товаров
            String createGoodsTableSQL = "CREATE TABLE IF NOT EXISTS goods (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," + // Добавление колонки цены продажи
                    "goods_name TEXT," + // Добавление колонки название товара
                    "amount INTEGER," + // Добавление колонки количество товара
                    "price INTEGER," + // Добавление колонки цена товара
                    "sold_amount INTEGER," + // Добавление колонки количество продаж
                    "sold_price INTEGER," + // Добавление колонки цены продажи
                    "date TEXT)"; // Добовление колонки дата продажи
            stmt.execute(createGoodsTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения списка товаров
    public static String[] getGoodsList() {
        ArrayList<String> goods = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT goods_name FROM goods")) {
            while (rs.next()) {
                goods.add(rs.getString("goods_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods.toArray(new String[0]);
    }

    // Обработка изменения количества товара и добавление продажи
    // Обработка изменения количества товара и добавление продажи
    public static boolean processGoodAmount(String goodsName, int amount) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT amount, price FROM goods WHERE goods_name = ?")) {
            pstmt.setString(1, goodsName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int currentAmount = rs.getInt("amount");
                int price = rs.getInt("price");
                if (currentAmount >= amount) {
                    try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE goods SET amount = ? WHERE goods_name = ?")) {
                        updateStmt.setInt(1, currentAmount - amount);
                        updateStmt.setString(2, goodsName);
                        updateStmt.executeUpdate();
                    }
                    SalesSummary.addSale(goodsName, amount, price);
                    return true; // Успешное создание чека
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Недостаточно товара
    }

    //Метод для добавления определенного количество к товару
    public static void addQuantityToGood(String goodsName, int additionalAmount) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE goods SET amount = amount + ? WHERE goods_name = ?")) {
            pstmt.setInt(1, additionalAmount);
            pstmt.setString(2, goodsName);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Количество товара обновлено!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Методы для добавления, удаления и обновления товаров
    public static void addNewGood(String goodsName, int price, int amount) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO goods (goods_name, price, amount) VALUES (?, ?, ?)")) {
            pstmt.setString(1, goodsName);
            pstmt.setInt(2, price);
            pstmt.setInt(3, amount);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Новый товар добавлен!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteGood(String goodsName) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM goods WHERE goods_name = ?")) {
            pstmt.setString(1, goodsName);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Товар удалён!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateGoodAmount(String goodsName, int newAmount) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE goods SET amount = ? WHERE goods_name = ?")) {
            pstmt.setInt(1, newAmount);
            pstmt.setString(2, goodsName);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Количество товара обновлено!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateGoodPrice(String goodsName, int newPrice) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE goods SET price = ? WHERE goods_name = ?")) {
            pstmt.setInt(1, newPrice);
            pstmt.setString(2, goodsName);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Цена товара обновлена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Показать все товары
    public static void showAllGoods() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM goods")) {
            StringBuilder goodsList = new StringBuilder();
            while (rs.next()) {
                goodsList.append(rs.getString("goods_name"))
                        .append(" | Количество: ").append(rs.getInt("amount"))
                        .append(" | Цена: ").append(rs.getInt("price"))
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, goodsList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
