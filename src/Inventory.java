import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Creates and stores the virtual store inventory and
 */
public class Inventory {
    private List<Discount> discounts;
    private List<Product> prodcuts;

    public Inventory(String prices, String discounts){
        try
        {
            this.discounts = discountList(discounts);
            this.prodcuts = productList(prices);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Discount> discountList(String arg) throws FileNotFoundException
    {
        List<Discount> result = new ArrayList<>();
        try
        {
            Scanner sc = new Scanner(new File(arg));

            while (sc.hasNext()) {
                //creates string array which stores discount information
                String[] discountParam;
                String currentLine = sc.nextLine();

                discountParam = currentLine.split(",");

                //validate barcode and number values and creates Discount object
                validateBarcode(discountParam[0]);
                for (int i = 1; i < discountParam.length; ++i) {
                    validateNumeric(discountParam[i]);
                }
                Discount currentDiscount = new Discount(discountParam[0], discountParam[1], discountParam[2], discountParam[3]);

                //add discount to list
                result.add(currentDiscount);
            }
        }
        catch (FileNotFoundException e)
        {
        e.printStackTrace();
        }
        return result;
    }

    private static List<Product> productList(String arg) throws FileNotFoundException {

        List<Product> result = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(arg));

            while (sc.hasNext()) {
                String[] productParam;
                String currentLine = sc.nextLine();

                productParam = currentLine.split(",");

                // validate barcode
                validateBarcode(productParam[0]);
                //validate numeric values
                for(int i = 3; i < productParam.length; ++i)
                {
                    validateNumeric(productParam[i]);
                }
                //ensure name and category fields are not null
                if(productParam[1] != null && productParam[2] != null)
                {
                    //create new product
                    Product currentProduct = new Product(
                            productParam[0],
                            productParam[1],
                            productParam[2],
                            productParam[3],
                            productParam[4]);

                    // add product to list
                    result.add(currentProduct);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            //exception in case something explodes, may be moved later
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Validates that barcode only contains numeric values & is not null.
     *
     * @param barcode                   String representation of a barcode
     * @throws IllegalFormatException   Informs user of barcode format error.
     */
    private static void validateBarcode(String barcode) throws IllegalFormatException
    {
        if(barcode.matches("[a-zA-Z]"))
        {
            throw new IllegalArgumentException("A barcode can only contain numeric values. Found: " + barcode);
        }
        if(barcode == null)
        {
            throw new NullPointerException("Barcode cannot be null");
        }
    }

    /**
     * Used to validate number values in Product and Discount objects.
     *
     * A product's price and assigned discount and limit are to be stored as positive doubles. Any other format
     * is illegal.
     */
    private static void validateNumeric(String numeric) throws IllegalFormatException
    {
        //if string representation of the double contains any alphabetic character
        //then throw exception
        if(numeric.matches("[\\p{Lower}\\p{Upper}]"))
        {
            throw new IllegalArgumentException("Price or limit values can only be numeric. Found: " + numeric);
        }
        if(numeric.equals(""))
        {
            throw new IllegalArgumentException("Price or limit values cannot be empty.");
        }
    }

    /**
     * Creates a hashmap of the store inventory.
     *
     * The hashmap holds a unique barcode as key, a product is added as value.
     * Before putting the product value in the hashmap the barcode is cross-referenced with Discount barcodes.
     * If a product has a discount it is stored in the respective product instance.
     *
     * @return A hashmap of the store inventory.
     */
    public Map<String, Product> inventoryMap()
    {
        return null;
    }
}
