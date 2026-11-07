package shop.notification;

public class WhatsAppChannel extends AbstractChannel {
    @Override
    public String channelName() {
        return "WhatsApp";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String phone = contact.getPhone();
        if (phone.length() < 10) {
            return null;
        }
        return phone.startsWith("08") || phone.startsWith("+62") ? phone : null;
    }

    @Override
    protected String prepare(String message) {
        return "[Toko UMKM] " + message;
    }

    @Override
    protected void deliver(String recipient, String message) {
        System.out.println("[WhatsApp to " + recipient + " ] " + message);
    }
}
