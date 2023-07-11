package server.commands;

import common.utility.Visibility;
import common.utility.requests.Request;
import common.utility.response.NoCommandResponse;
import common.utility.response.Response;

public class NoCommand extends AbstractCommand{
    public NoCommand(){
        super("no_command","нет команды", Visibility.ALL_USERS);
    }
    public Response execute(Request request){

        return new NoCommandResponse("Зарегистрируйтесь прежде чем использовать команды!");
    }

}
