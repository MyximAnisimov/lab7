package common.utility.requests;

public class SignUpCommandRequest extends Request{
    public String user;
    public String password;
    public SignUpCommandRequest(String user, String password){
        super("sign_up");
        this.user=user;
        this.password=password;

    }
}
