/**
 * Product class
 */
public class Product
{
    private String barcode;
    private String category;
    private String name;
    private Double kr;
    private Double ore;

    private Discount discount;

    public Product(String barcode, String category, String name, Double kr, Double ore, Discount discount)
    {
        Discount itemDiscount = new Discount();
        this.discount = itemDiscount;

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
        return kr;
    }

    public Double getOre() {
        return ore;
    }

    public Double getPrice() {
        return kr + (ore / 100);
    }

    public Discount getDiscount() {
        return discount;
    }
}
