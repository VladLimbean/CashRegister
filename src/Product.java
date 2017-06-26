/**
 * Product class
 */
public class Product
{
    private String      barcode;
    private String      category;
    private String      name;
    private Double      kr;
    private Double      ore;

    private Discount    discount;
    private boolean     hasDiscount;

    public Product(String barcode, String category, String name, Double kr, Double ore)
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
        return kr;
    }
    public Double getOre()
    {
        return ore;
    }

    public void addDiscount(Discount newDiscount)
    {
        this.discount = newDiscount;
        this.hasDiscount = true;
    }

    public void removeDiscount(Discount discountToRemove)
    {
        if(this.discount.getBarcode().equals(discountToRemove.getBarcode()))
        {
            this.discount = null;
            this.hasDiscount = false;
        }
        else
        {
            System.out.println("Error: product barcode does not match discount barcode.");
        }
    }

    public Discount getDiscount() {
        if(hasDiscount)
        {
            return discount;
        }
        return null;
    }

    public Double getBasePrice()
    {
        return kr + (ore / 100);
    }
}
