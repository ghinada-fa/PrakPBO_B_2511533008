package shop.payment;

public class CreditCardPayment implements RefundablePayment, InstallmentPayment {
    private String cardHolder;
    private long creditLimit;

    public CreditCardPayment(String cardHolder, long creditLimit) {
        if (cardHolder == null || cardHolder.isEmpty()) {
            throw new IllegalArgumentException("Card holder name must not be empty");
        }
        if (creditLimit < 0) {
            throw new IllegalArgumentException("Credit limit must be non-negative");
        }
        this.cardHolder = cardHolder;
        this.creditLimit = creditLimit;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public long getCreditLimit() {
        return creditLimit;
    }

    public String getName() {
        return "Kartu Kredit (" + cardHolder + ")";
    }

    public boolean pay(long amount) {
        System.out.println("Paying " + amount + " using credit card: " + cardHolder);
        if (amount <= 0) {
            System.out.println("Invalid payment amount: " + amount);
            return false;
        }
        if (amount > creditLimit) {
            System.out.println("Insufficient credit limit for card holder: " + cardHolder);
            return false;
        }
        creditLimit -= amount;
        System.out.println("Payment successful. Remaining credit limit: " + creditLimit);
        return true;
    }

    public boolean refund(long amount) {
        System.out.println("Refunding " + amount + " to credit card: " + cardHolder);
        creditLimit += amount;
        System.out.println("Refund successful. New credit limit: " + creditLimit);
        return true;
    }

    public int maxInstallmentMonths() {
        return 12; 
    }

}
