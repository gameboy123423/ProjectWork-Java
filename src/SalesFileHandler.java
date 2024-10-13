import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SalesFileHandler {
    public static void writeSale(String goodsName, int amount, int price) {
        try (FileWriter writer = new FileWriter("sales.txt", true)) {
            String saleData = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    + " - Товар: " + goodsName + ", Количество: " + amount + ", Цена: " + (amount * price) + " тенге\n";
            writer.write(saleData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
