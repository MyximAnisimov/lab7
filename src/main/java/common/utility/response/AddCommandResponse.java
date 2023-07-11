package common.utility.response;

public class AddCommandResponse extends Response{
    public int newId;

    public AddCommandResponse(int newId, String error) {
        super("add", error);
        this.newId = newId;
    }
    public void setNewId(int id){
        this.newId=id;
    }
}
