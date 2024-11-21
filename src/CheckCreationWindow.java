import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckCreationWindow extends JFrame {
    public CheckCreationWindow() {
        setTitle("Создать чек");
        setSize(400, 200);
        setLocationRelativeTo(null);

        JComboBox<String> goodsComboBox = new JComboBox<>(GoodsDatabase.getGoodsList());
        JTextField amountField = new JTextField(10);
        JButton createCheckButton = new JButton("Создать чек");
        JButton backButton = new JButton("Назад"); // Кнопка "Назад"

        createCheckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedGood = (String) goodsComboBox.getSelectedItem();
                int amount = Integer.parseInt(amountField.getText());

                // Проверяем наличие товара и создаём чек
                if (GoodsDatabase.processGoodAmount(selectedGood, amount)) {
                    JOptionPane.showMessageDialog(null, "Чек успешно создан!");
                } else {
                    JOptionPane.showMessageDialog(null, "Недостаточно товара на складе!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Действие для кнопки "Назад"
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрыть текущее окно
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Выберите товар:"));
        panel.add(goodsComboBox);
        panel.add(new JLabel("Количество:"));
        panel.add(amountField);
        panel.add(createCheckButton);
        panel.add(backButton); // Добавляем кнопку "Назад" на панель

        add(panel);
    }
}
//Кнопка которая показывает созданные изменения не работает, как решишь ошибку удали все комменты