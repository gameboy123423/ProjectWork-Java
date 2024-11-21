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
            public void actionPerformed(ActionEvent e) {
                SalesSummary.showSales("day");
            }
        });

        logShowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChangesLogWindow().setVisible(true); // Исправлено
            }
        });

        salesWeekButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SalesSummary.showSales("week");
            }
        });

        salesMonthButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SalesSummary.showSales("month");
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