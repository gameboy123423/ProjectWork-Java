import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GoodsDatabase {
    private static final String URL = "jdbc:sqlite:bot_database.db";

    // Создание таблицы товаров при инициализации базы данных
    static {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS goods (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                             "goods_name TEXT UNIQUE, " +
                             "amount INTEGER, " +
                             "price INTEGER)")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для добавления нового товара
    public static void addGood(String goodName, int initialAmount, int price) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO goods (goods_name, amount, price) VALUES (?, ?, ?)")) {
            pstmt.setString(1, goodName);
            pstmt.setInt(2, initialAmount);
            pstmt.setInt(3, price);
            pstmt.executeUpdate();

            // Логирование создания нового товара
            ChangeLogger.logChange("Создание товара", goodName, "Отсутствовал",
                    "Создан: количество = " + initialAmount + ", цена = " + price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для добавления количества к существующему товару
    public static void addQuantityToGood(String goodName, int additionalAmount) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT amount FROM goods WHERE goods_name = ?")) {
            selectStmt.setString(1, goodName);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int currentAmount = rs.getInt("amount");
                int newAmount = currentAmount + additionalAmount;

                try (PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE goods SET amount = ? WHERE goods_name = ?")) {
                    updateStmt.setInt(1, newAmount);
                    updateStmt.setString(2, goodName);
                    updateStmt.executeUpdate();

                    // Логирование изменения количества
                    ChangeLogger.logChange("Добавление количества", goodName,
                            String.valueOf(currentAmount), String.valueOf(newAmount));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для удаления товара
    public static void deleteGood(String goodName) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM goods WHERE goods_name = ?")) {
            pstmt.setString(1, goodName);
            pstmt.executeUpdate();

            // Логирование удаления товара
            ChangeLogger.logChange("Удаление товара", goodName, "Существовал", "Удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для изменения количества товара
    public static void updateGoodAmount(String goodName, int newAmount) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT amount FROM goods WHERE goods_name = ?")) {
            selectStmt.setString(1, goodName);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int oldAmount = rs.getInt("amount");

                try (PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE goods SET amount = ? WHERE goods_name = ?")) {
                    updateStmt.setInt(1, newAmount);
                    updateStmt.setString(2, goodName);
                    updateStmt.executeUpdate();

                    // Логирование изменения количества
                    ChangeLogger.logChange("Изменение количества", goodName,
                            String.valueOf(oldAmount), String.valueOf(newAmount));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для изменения цены товара
    public static void updateGoodPrice(String goodName, int newPrice) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT price FROM goods WHERE goods_name = ?")) {
            selectStmt.setString(1, goodName);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int oldPrice = rs.getInt("price");

                try (PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE goods SET price = ? WHERE goods_name = ?")) {
                    updateStmt.setInt(1, newPrice);
                    updateStmt.setString(2, goodName);
                    updateStmt.executeUpdate();

                    // Логирование изменения цены
                    ChangeLogger.logChange("Изменение цены", goodName,
                            String.valueOf(oldPrice), String.valueOf(newPrice));
                }
            }
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
                String goodName = rs.getString("goods_name");
                if (goodName != null) {
                    goods.add(goodName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods.toArray(new String[0]);
    }

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
                    // Обновляем количество товара на складе
                    int newAmount;
                    try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE goods SET amount = ? WHERE goods_name = ?")) {
                        updateStmt.setInt(1, currentAmount - amount);
                        updateStmt.setString(2, goodsName);
                        updateStmt.executeUpdate();
                        newAmount = currentAmount - amount;
                    }

                    // Записываем данные о продаже в базу данных
                    SalesSummary.addSale(goodsName, amount, price);

                    // Логируем изменение
                    ChangeLogger.logChange("Продажа товара", goodsName, String.valueOf(currentAmount), String.valueOf(newAmount));

                    return true; // Успех
                } else {
                    return false; // Неудача: недостаточно товара
                }
            } else {
                return false; // Неудача: товар не найден
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Неудача: ошибка SQL
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
            // Если список товаров пуст, добавим соответствующее сообщение
            if (goodsList.length() == 0) {
                goodsList.append("Товары отсутствуют.");
            }
            JOptionPane.showMessageDialog(null, goodsList.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка при получении товаров: " + e.getMessage());
        }
    }

//Кнопка которая показывает созданные изменения не работает, как решишь ошибку удали все комменты

}
