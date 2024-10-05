import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Создание кнопок
        Button createCheckButton = new Button("Создать чек");
        Button showAllButton = new Button("Показать всё");
        Button changesButton = new Button("Изменения");

        // Поле для вывода данных
        Label outputLabel = new Label();

        createCheckButton.setOnAction(e -> outputLabel.setText("Создаем чек..."));
        showAllButton.setOnAction(e -> outputLabel.setText("Показываем все товары..."));
        changesButton.setOnAction(e -> outputLabel.setText("Изменяем данные..."));

        // Вертикальный контейнер для элементов интерфейса
        VBox layout = new VBox(10);
        layout.getChildren().addAll(createCheckButton, showAllButton, changesButton, outputLabel);

        // Создание сцены и запуск
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Меню управления");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
