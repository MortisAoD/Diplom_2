package functions.user;

import api.user.UserDelete;

public class FunctionsUserDelete extends UserDelete {
    public static void getUserDelete(String token){
        if (token != null) {
            requestDelete(token);
        } else {
            requestDelete();
        }
    }
}