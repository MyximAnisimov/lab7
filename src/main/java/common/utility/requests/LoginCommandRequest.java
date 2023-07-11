package common.utility.requests;

public class LoginCommandRequest extends Request{
    public String user;
    public String password;
    public int user_id;
    public LoginCommandRequest(String user, String password, int user_id){
        super("login");
        this.password=password;
                this.user=user;
                this.user_id=user_id;
    }
    public void setUser(int user_id){
        this.user_id= user_id;
    }
    public String getUser(){
        return user;
    }
}
