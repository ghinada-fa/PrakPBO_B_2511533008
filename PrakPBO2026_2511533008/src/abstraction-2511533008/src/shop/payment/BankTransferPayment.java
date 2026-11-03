package shop.payment;

public class BankTransferPayment implements PaymentMethod {
    private String bankName;

    public BankTransferPayment(String bankName) {
        if (bankName == null || bankName.isEmpty()) {
            throw new IllegalArgumentException("Bank name must not be empty");
        }
        this.bankName = bankName;
    }

    @Override
    public String getName() {
        return bankName;
    }

    @Override
    public boolean pay(long amount) {
        System.out.println("Paying " + amount + " using bank transfer: " + bankName);

        System.out.println("Payment successful via bank transfer.");
        return amount > 0;
    }

}
