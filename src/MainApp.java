import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Интерфейс для выбора товаров и количества
        ComboBox<String> goodsComboBox = new ComboBox<>();
        TextField amountField = new TextField();
        amountField.setPromptText("Введите количество");
        Button submitButton = new Button("Создать чек");

        // Вывод товаров
        Button showGoodsButton = new Button("Показать товары");
        showGoodsButton.setOnAction(e -> GoodsManager.showGoods(goodsComboBox));

        // Основной контейнер
        VBox layout = new VBox(10);
        layout.getChildren().addAll(showGoodsButton, goodsComboBox, amountField, submitButton);

        // Настройки сцены
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Управление товарами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
