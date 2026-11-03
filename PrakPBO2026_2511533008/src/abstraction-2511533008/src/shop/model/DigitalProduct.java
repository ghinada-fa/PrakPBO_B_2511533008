package shop.model;

import shop.discount.Discountable;

public class DigitalProduct extends Product implements Discountable{
    public String downloadUrl;

    public DigitalProduct(String sku, String name, long price, String downloadUrl) {
        super(sku, name, price);
        if (downloadUrl == null || downloadUrl.isBlank()) {
            throw new IllegalArgumentException("Download URL must not be blank");
        }
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String category() { return "Digital"; }

    @Override
    public int discountPercent() {
        return 10; // 10% discount for digital products
    }

    @Override 
    public long getFinalPrice() {
        return applyDiscount(getPrice());
    }

}
