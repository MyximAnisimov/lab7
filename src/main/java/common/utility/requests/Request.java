package common.utility.requests;


import common.utility.VisibilityArgument;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    public int user_id;
    public VisibilityArgument VA;

    public Request(String commandName) {
        this.commandName = commandName;
    }
    public String getName() {
        return commandName;
    }
    public Request(){

    }
    public void setUserId(int id){
        this.user_id=id;
    }
    public int getUserId(){
        return user_id;
    }
}
