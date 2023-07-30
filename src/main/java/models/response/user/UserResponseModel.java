package models.response.user;

public class UserResponseModel {

    public UserModel user;
    public boolean success;
    public String accessToken;
    public String refreshToken;

    public UserModel getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}