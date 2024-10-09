import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangesWindow extends JFrame {
    public ChangesWindow() {
        setTitle("Изменения");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JButton addGoodButton = new JButton("Добавить товар");
        JButton deleteGoodButton = new JButton("Удалить товар");
        JButton updateAmountButton = new JButton("Изменить количество");
        JButton updatePriceButton = new JButton("Изменить цену");

        addGoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String goodName = JOptionPane.showInputDialog("Введите имя товара:");
                GoodsDatabase.addGood(goodName);
            }
        });

        deleteGoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String goodName = JOptionPane.showInputDialog("Введите имя товара для удаления:");
                GoodsDatabase.deleteGood(goodName);
            }
        });

        updateAmountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String goodName = JOptionPane.showInputDialog("Введите имя товара для изменения количества:");
                int newAmount = Integer.parseInt(JOptionPane.showInputDialog("Введите новое количество:"));
                GoodsDatabase.updateGoodAmount(goodName, newAmount);
            }
        });

        updatePriceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String goodName = JOptionPane.showInputDialog("Введите имя товара для изменения цены:");
                int newPrice = Integer.parseInt(JOptionPane.showInputDialog("Введите новую цену:"));
                GoodsDatabase.updateGoodPrice(goodName, newPrice);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(addGoodButton);
        panel.add(deleteGoodButton);
        panel.add(updateAmountButton);
        panel.add(updatePriceButton);

        add(panel);
    }
}
