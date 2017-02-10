/**
 * Holds information on discounted items.
 */
public class Discount {
    private String barcode;
    private Integer limit;
    private Double kr;
    private Double ore;

    public Discount(String barcode, Integer limit, Double kr, Double ore)
    {
        this.barcode = barcode;
        this.limit = limit;
        this.kr = kr;
        this.ore = ore;
    }
    public String getBarcode() {
        return barcode;
    }

    public Integer getLimit() {
        return limit;
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
}
