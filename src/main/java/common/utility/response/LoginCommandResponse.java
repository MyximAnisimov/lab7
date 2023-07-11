package common.utility.response;

public class LoginCommandResponse extends Response{
    public String infoMessage;

    public String token;


    public LoginCommandResponse(String user, String password, int user_id, String error) {
      super(user, password, user_id, error);

    }

}
