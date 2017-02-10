import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Creates and stores the virtual store inventory and
 */
public class Inventory {
    private DiscountDB discountMap;

    public Inventory(){

    }

    public static List<Product> productList(String arg) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(arg));

        List<Product> result = new ArrayList<>();

        while(sc.hasNext())
        {
            String[] productParam;
            String currentLine = sc.nextLine();

            productParam = currentLine.split(",");
        }
        return null;
    }
}
