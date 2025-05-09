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

        createCheckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedGood = (String) goodsComboBox.getSelectedItem();
                int amount = Integer.parseInt(amountField.getText());
                GoodsDatabase.processGoodAmount(selectedGood, amount);
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Выберите товар:"));
        panel.add(goodsComboBox);
        panel.add(new JLabel("Количество:"));
        panel.add(amountField);
        panel.add(createCheckButton);

        add(panel);
    }
}
