package review.fixed;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

interface Payable {
    boolean pay(long amount);
}

interface Refundable {
    boolean refund(long amount);
}

interface Shippable {
    long ship(int weightGrams);
}

interface InvoicePrintable {
    String printInvoice();
}

class GiftVoucher implements Payable, InvoicePrintable {
    private long balance = 100_000;

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "Voucher, remaining balance " + balance;
    }
}

enum ShippingType {
    REGULAR(10_000),
    EXPRESS(18_000),
    SAME_DAY(30_000);

    private final long ratePerKg;

    ShippingType(long ratePerKg) {
        this.ratePerKg = ratePerKg;
    }

    long ratePerKg() {
        return ratePerKg;
    }
}

abstract class Courier {
    public long cost(ShippingType type, int kilograms) {
        Objects.requireNonNull(type, "Shipping type must not be null");
        if (kilograms < 0) {
            throw new IllegalArgumentException("Weight must not be negative");
        }
        return type.ratePerKg() * kilograms;
    }
}

class JneCourier extends Courier {
}

class Cart {
    private final List<Item> items = new ArrayList<>();

    static final class Item {
        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Item name must not be blank");
            }
            if (price < 0 || quantity <= 0) {
                throw new IllegalArgumentException("Price must be non-negative and quantity must be positive");
            }
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        long subtotal() {
            return price * quantity;
        }

        String getName() {
            return name;
        }
    }

    void add(Item item) {
        items.add(Objects.requireNonNull(item, "Item must not be null"));
    }

    long total() {
        long total = 0;
        for (Item item : items) {
            total += item.subtotal();
        }
        return total;
    }
}

final class Address {
    private final String street;
    private final String city;

    Address(String street, String city) {
        if (street == null || street.isBlank() || city == null || city.isBlank()) {
            throw new IllegalArgumentException("Street and city must not be blank");
        }
        this.street = street;
        this.city = city;
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

class SimpleOrder {
    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Order id must not be blank");
        }
        this.id = id;
        this.address = Objects.requireNonNull(address, "Address must not be null");
    }

    Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return id + " -> " + address;
    }
}

interface Discount {
    long apply(long price);
}

class MemberDiscount implements Discount {
    @Override
    public long apply(long price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        return price - price * 5 / 100;
    }
}

class FlashSaleDiscount implements Discount {
    @Override
    public long apply(long price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        return price - price * 30 / 100;
    }
}

public class BadShopRefactored {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));
        System.out.println("Total cart: " + cart.total());

        Courier courier = new JneCourier();
        System.out.println("Ongkir EXPRESS 2 kg: " + courier.cost(ShippingType.EXPRESS, 2));

        SimpleOrder first = new SimpleOrder("ORD-1", new Address("Jl. Sudirman 10", "Padang"));
        SimpleOrder second = new SimpleOrder("ORD-2", new Address("Jl. Sudirman 10", "Bukittinggi"));
        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount();
        System.out.println("Harga flash sale: " + flashSale.apply(100_000));

        GiftVoucher voucher = new GiftVoucher();
        boolean paid = voucher.pay(30_000);
        System.out.println(paid + " | " + voucher.printInvoice());
    }
}
