package models.request;

public class UserUpdateRequestModel {
    public String name;
    public String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserUpdateRequestModel(String name, String email) {
        this.name = name;
        this.email = email;
    }
}