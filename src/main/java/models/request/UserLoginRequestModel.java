package models.request;

public class UserLoginRequestModel {
    public String email;
    public String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserLoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}