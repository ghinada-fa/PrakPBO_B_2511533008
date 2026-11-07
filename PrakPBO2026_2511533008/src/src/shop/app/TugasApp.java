package shop.app;

import java.util.List;

import shop.StudentInfo;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.CustomerContact;
import shop.notification.EmailChannel;
import shop.notification.NotificationChannel;
import shop.notification.NotificationService;
import shop.notification.SmsChannel;
import shop.notification.WhatsAppChannel;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.ShippingService;

public class TugasApp {
    public static void main(String[] args) {
        System.out.println("Tugas oleh " + StudentInfo.NAME + " (" + StudentInfo.NIM + ")");

        List<Product> cart = List.of(
                new PhysicalProduct("KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300),
                new PhysicalProduct("RDG-002", "Rendang Kemasan 250g", 120_000, 400),
                new DigitalProduct("EBK-003", "E-book Resep Kue Nusantara", 50_000,
                        "https://toko-umkm.example/unduh/ebk-003"));
        CheckoutService checkout = new CheckoutService();
        ShippingService express = new ExpressShipping();
        PaymentMethod eWallet = new EWalletPayment("DompetKu", 1_000_000);
        PaymentMethod transfer = new BankTransferPayment("Bank Nagari");
        PaymentMethod creditCard = new CreditCardPayment("Siti Rahma", 1_000_000);
        List<PaymentMethod> payments = List.of(eWallet, transfer, creditCard);

        System.out.println("\n== T1: Total dengan biaya admin (Ekspres) ==");
        for (PaymentMethod payment : payments) {
            long total = checkout.grandTotal(cart, express, payment);
            long adminFee = payment.adminFee(checkout.total(cart, express));
            System.out.println(payment.getName() + " : Rp" + total + " (admin Rp" + adminFee + ")");
        }

        CreditCardPayment card = (CreditCardPayment) creditCard;
        EWalletPayment wallet = (EWalletPayment) eWallet;
        System.out.println("\nKebijakan kartu kredit : " + card.policy());
        System.out.println("Kebijakan e-wallet     : " + wallet.policy());

        NotificationService notifications = new NotificationService();
        notifications.register(new EmailChannel());
        notifications.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        notifications.register(new WhatsAppChannel());
        notifications.register(new NotificationChannel() {
            @Override
            public String channelName() {
                return "Console log";
            }

            @Override
            public boolean send(CustomerContact contact, String message) {
                System.out.println("[LOG] " + contact.getName() + ": " + message);
                return true;
            }
        });

        String message = "Pesanan anda sudah dikirim dengan kurir Ekspres. "
                + "Nomor resi akan muncul di aplikasi dalam 1x24 jam. "
                + "Terima kasih telah berbelanja produk yang anda butuhkan di toko kami! Semoga anda berbelanja lagi di toko kami. "
                + "Mohon menunggu informasi berikutnya melalui kanal notifikasi ini.";
        CustomerContact fathana = new CustomerContact("Fathana", "fathana@example.com", "081234567896");
        CustomerContact wafa = new CustomerContact("Wafa", "", "+6281234569870");

        System.out.println("\n== T2: Notifikasi status pesanan ==");
        int sitiDelivered = notifications.broadcast(fathana, message);
        System.out.println("Terkirim ke Fathana lewat " + sitiDelivered + " kanal");
        int budiDelivered = notifications.broadcast(wafa, message);
        System.out.println("Terkirim ke Wafa lewat " + budiDelivered + " kanal");
    }
}
