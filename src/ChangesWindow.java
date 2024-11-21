import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangesWindow extends JFrame {
    public ChangesWindow() {
        setTitle("Изменения");
        setSize(400, 350);
        setLocationRelativeTo(null);

        JButton addQuantityButton = new JButton("Добавить количество к товару");
        JButton addNewGoodButton = new JButton("Добавить новый товар");
        JButton deleteGoodButton = new JButton("Удалить товар");
        JButton updateAmountButton = new JButton("Изменить количество");
        JButton updatePriceButton = new JButton("Изменить цену");
        JButton backButton = new JButton("Назад");

        // Кнопка для добавления нового товара
        addNewGoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String goodName = JOptionPane.showInputDialog("Введите название нового товара:");
                int price = Integer.parseInt(JOptionPane.showInputDialog("Введите цену товара:"));
                int amount = Integer.parseInt(JOptionPane.showInputDialog("Введите количество товара:"));
                GoodsDatabase.addGood(goodName, price, amount); // Вызов метода для добавления нового товара
            }
        });

        // Кнопка для добавления количества к существующему товару
        addQuantityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] goodsList = GoodsDatabase.getGoodsList();
                JComboBox<String> goodsComboBox = new JComboBox<>(goodsList);

                int option = JOptionPane.showConfirmDialog(null, goodsComboBox, "Выберите товар для добавления количества", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String goodName = (String) goodsComboBox.getSelectedItem();
                    int additionalAmount = Integer.parseInt(JOptionPane.showInputDialog("Введите количество для добавления:"));
                    GoodsDatabase.addQuantityToGood(goodName, additionalAmount);
                }
            }
        });

        deleteGoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] goodsList = GoodsDatabase.getGoodsList();
                JComboBox<String> goodsComboBox = new JComboBox<>(goodsList);

                int option = JOptionPane.showConfirmDialog(null, goodsComboBox, "Выберите товар для удаления", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String goodName = (String) goodsComboBox.getSelectedItem();
                    GoodsDatabase.deleteGood(goodName);
                }
            }
        });

        updateAmountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] goodsList = GoodsDatabase.getGoodsList();
                JComboBox<String> goodsComboBox = new JComboBox<>(goodsList);

                int option = JOptionPane.showConfirmDialog(null, goodsComboBox, "Выберите товар для изменения количества", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String goodName = (String) goodsComboBox.getSelectedItem();
                    int newAmount = Integer.parseInt(JOptionPane.showInputDialog("Введите новое количество:"));
                    GoodsDatabase.updateGoodAmount(goodName, newAmount);
                }
            }
        });

        updatePriceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] goodsList = GoodsDatabase.getGoodsList();
                JComboBox<String> goodsComboBox = new JComboBox<>(goodsList);

                int option = JOptionPane.showConfirmDialog(null, goodsComboBox, "Выберите товар для изменения цены", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String goodName = (String) goodsComboBox.getSelectedItem();
                    int newPrice = Integer.parseInt(JOptionPane.showInputDialog("Введите новую цену:"));
                    GoodsDatabase.updateGoodPrice(goodName, newPrice);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); //Закрыть существующее окно
            }
        });

        //Добавление кнопок на экран

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.add(addQuantityButton);
        panel.add(addNewGoodButton);
        panel.add(updateAmountButton);
        panel.add(updatePriceButton);
        panel.add(deleteGoodButton);
        panel.add(backButton);

        add(panel);
    }
}
//Кнопка которая показывает созданные изменения не работает, как решишь ошибку удали все комменты