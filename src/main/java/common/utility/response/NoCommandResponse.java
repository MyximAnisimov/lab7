package common.utility.response;

public class NoCommandResponse extends Response{
    public final String error;
    public NoCommandResponse(String error) {
        super("no_command", error);
        this.error =error;
    }
}
