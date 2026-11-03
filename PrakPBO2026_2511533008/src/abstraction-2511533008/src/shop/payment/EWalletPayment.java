package shop.payment;

public class EWalletPayment implements RefundablePayment {
   private String provider;
   private long balance;

    public EWalletPayment(String provider, long balance) {
        if (provider == null || provider.isEmpty()) {
            throw new IllegalArgumentException("Provider name must not be empty");
        } 
        if (balance < 0) {
            throw new IllegalArgumentException("Balance must be non-negative");
        }
        this.provider = provider;
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public String getName() {
        return provider;
    }

    @Override
    public boolean pay(long amount) {
        System.out.println("Paying " + amount + " using e-wallet: " + provider);
        if (amount > balance) {
            System.out.println("Insufficient balance in e-wallet: " + provider);
            return false;
        }
        balance -= amount;
        System.out.println("Payment successful. Remaining balance: " + balance);
        return true;
    }

    public boolean refund(long amount) {
        System.out.println("Refunding " + amount + " to e-wallet: " + provider);
        balance += amount;
        System.out.println("Refund successful. New balance: " + balance);
        return true;
    }

}
