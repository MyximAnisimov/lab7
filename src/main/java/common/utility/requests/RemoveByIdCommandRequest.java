package common.utility.requests;


public class RemoveByIdCommandRequest extends Request{
    public final int id;
    private int user_id;
    public RemoveByIdCommandRequest(int id){
        super("remove_by_id");
        this.id=id;
    }
}
