import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;

public class GoodsManager {

    public static void showGoods(ComboBox<String> comboBox) {
        String sql = "SELECT goods_name FROM goods";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String goodsName = rs.getString("goods_name");
                comboBox.getItems().add(goodsName);  // Добавляем товары в ComboBox
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
