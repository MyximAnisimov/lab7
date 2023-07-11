package server;

import common.utility.Tokenizer;
import common.utility.Visibility;
import common.utility.requests.Request;
import common.utility.response.BadCredentialsResponse;
import common.utility.response.ErrorResponse;
import common.utility.response.NoCommandResponse;
import common.utility.response.Response;
import org.apache.logging.log4j.Logger;
import server.commands.AbstractCommand;
import server.commands.Command;
import server.managers.CommandManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class CommandExecutor {
    private final CommandManager manager;

    public CommandExecutor(CommandManager manager) {
        this.manager = manager;
    }
    public static HashSet<String> token = new HashSet<>();

    public Response handle(Request request) {

        var command = manager.getCommands().get(request.getName());
        if (command.getVisibility() == Visibility.ALL_USERS || command.getVisibility() == request.VA.globalArgument)
            return command.execute(request);
        else return new NoCommandResponse("Пользователь не зарегестрирован!");
}}

