package common.utility.response;

public class SignUpCommandResponse extends Response{
    public String token;
    public SignUpCommandResponse(String message, String error) {
        super("sign_up", error);
    }
}
