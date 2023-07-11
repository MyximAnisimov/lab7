package common.utility.requests;

import common.collections.Person;
public class AddCommandRequest extends Request{
    public final int user_id;
    public final Person person;
    public AddCommandRequest(int user_id,Person person){
        super("add");
        this.person=person;
        this.user_id=user_id;
    }
}
