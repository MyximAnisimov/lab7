package common.utility.response;

public class RemoveFirstCommandResponse extends Response{
    public RemoveFirstCommandResponse( String error){
        super("remove_first", error);
    }
}
