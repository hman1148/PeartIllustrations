package models.UserRequestResponse;

public class UserCreateRequest {

    private String username;
    private String password;
    private String email;
    private final String role = "USER"; // Default role set to USER
    private String stripeCustomerId;

    public UserCreateRequest(String username, String password, String email, String stripeCustomerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.stripeCustomerId = stripeCustomerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }
}
