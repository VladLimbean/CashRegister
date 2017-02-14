/**
 * Holds information on discounted items.
 */
public class Discount {
    private String barcode;
    private String limit;
    private String kr;
    private String ore;

    public Discount(String barcode, String limit, String kr, String ore)
    {
        this.barcode = barcode;
        this.limit = limit;
        this.kr = kr;
        this.ore = ore;
    }
    public String getBarcode() {
        return barcode;
    }

    public Double getLimit() {
        return Double.parseDouble(limit);
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
}
