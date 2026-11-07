package shop.notification;

public final class CustomerContact {
    private String name;
    private String email;
    private String phone;

    public CustomerContact(String name, String email, String phone) {
        this.name = name;
        this.email = email == null ? "" : email;
        this.phone = phone == null ? "" : phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

}
