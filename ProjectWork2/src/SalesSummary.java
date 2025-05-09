public class SalesSummary {

    // Этот метод возвращает сводку продаж
    public static String getSalesSummary(String period) {
        if (period.equals("day")) {
            return "Продажи за день: ...";
        } else if (period.equals("week")) {
            return "Продажи за неделю: ...";
        } else if (period.equals("month")) {
            return "Продажи за месяц: ...";
        } else {
            return "Некорректный период!";
        }
    }

    // Этот метод выводит сводку продаж на экран
    public static void showSales(String period) {
        String salesSummary = getSalesSummary(period);
        System.out.println(salesSummary); // Можно заменить на вывод через JOptionPane или другой GUI элемент
    }
}
