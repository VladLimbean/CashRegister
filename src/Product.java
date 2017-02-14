/**
 * Product class
 */
public class Product
{
    private String barcode;
    private String category;
    private String name;
    private String kr;
    private String ore;

    private Discount discount;

    public Product(String barcode,
                   String category,
                   String name,
                   String kr,
                   String ore)
    {
        this.barcode = barcode;
        this.category = category;
        this.name = name;
        this.kr = kr;
        this.ore = ore;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Double getKr() {
        return Double.parseDouble(kr);
    }

    public Double getOre() {
        return Double.parseDouble(ore);
    }

    public Double getPrice() {
        return Double.parseDouble(kr) + (Double.parseDouble(ore) / 100);
    }

    public Discount getDiscount() {
        return discount;
    }
}
