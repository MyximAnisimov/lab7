package common.utility.requests;

public class RemoveGreaterCommandRequest extends Request{
    public final int height;
    public int id;

    public RemoveGreaterCommandRequest(int user_id,int height){
        super("remove_greater");
        this.height=height;
        this.user_id=user_id;
    }
}
