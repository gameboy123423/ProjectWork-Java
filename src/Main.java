import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}
//Кнопка которая показывает созданные изменения не работает, как решишь ошибку удали все комменты