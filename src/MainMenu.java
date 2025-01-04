import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Главное меню");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Кнопки главного меню
        JButton createCheckButton = new JButton("Создать чек");
        JButton changesButton = new JButton("Изменения");
        JButton showAllButton = new JButton("Показать всё");
        JButton salesDayButton = new JButton("Продажи за день");
        JButton salesWeekButton = new JButton("Продажи за неделю");
        JButton salesMonthButton = new JButton("Продажи за месяц");
        JButton logShowButton = new JButton("Показать изменения");

        createCheckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CheckCreationWindow().setVisible(true);
            }
        });

        changesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChangesWindow().setVisible(true);
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GoodsDatabase.showAllGoods();
            }
        });

        salesDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT goods_name, sold_amount, sold_price, date " +
                        "FROM goods WHERE date(date) = date('now')";
                SalesSummaryWindow window = new SalesSummaryWindow("день", query);
                window.setVisible(true);
            }
        });

        logShowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChangesLogWindow().setVisible(true); // Исправлено
            }
        });

        salesWeekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT goods_name, sold_amount, sold_price, date " +
                        "FROM goods WHERE date(date) >= date('now', '-7 days')";
                SalesSummaryWindow window = new SalesSummaryWindow("неделю", query);
                window.setVisible(true);
            }
        });

        salesMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT goods_name, sold_amount, sold_price, date " +
                        "FROM goods WHERE strftime('%Y-%m', date) = strftime('%Y-%m', 'now')";
                SalesSummaryWindow window = new SalesSummaryWindow("месяц", query);
                window.setVisible(true);
            }
        });

        // Панель для кнопок
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));
        panel.add(createCheckButton);
        panel.add(changesButton);
        panel.add(showAllButton);
        panel.add(salesDayButton);
        panel.add(salesWeekButton);
        panel.add(salesMonthButton);
        panel.add(logShowButton);

        add(panel);
    }
}
//Кнопка которая показывает созданные изменения не работает, как решишь ошибку удали все комменты