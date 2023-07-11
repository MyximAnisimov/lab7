package server.commands;

import common.utility.CustomConsole;
import common.utility.Tokenizer;
import common.utility.Visibility;
import common.utility.requests.Request;
import common.utility.requests.SignUpCommandRequest;
import common.utility.response.LoginCommandResponse;
import common.utility.response.Response;
import common.utility.response.SignUpCommandResponse;
import server.CommandExecutor;
import server.DBhandler;
import server.UDPserver;
import server.managers.CommandManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;


public class SignUp extends AbstractCommand{
    public SignUp() {
        super("sign_up", "зарегистрировать пользователя", Visibility.ALL_USERS);
    }
    @Override
            public Response execute(Request request){
    var req = (SignUpCommandRequest) request;

      try{
        CommandExecutor CE = new CommandExecutor(new CommandManager());
        DBhandler dBhandler = new DBhandler();
        Tokenizer tokenizer = new Tokenizer();
        int new_user_id = dBhandler.setUser(req.user, req.password);
      if(new_user_id!=-1){
            var token = tokenizer.SHA384(req.user + req.password + Date.from(Instant.parse("1970-01-01T10:15:00.00Z")));
            CommandExecutor.token.add(token);
            var response =new SignUpCommandResponse(req.user, req.password);
            response.setSuccReg(true);
            response.token = token;

            CustomConsole.printLn("Авторизация прошла успешно!");
            return response;
        } else {
            var response =new SignUpCommandResponse(req.user, req.password);
            response.SuccReg = false;
            CustomConsole.printLn("Неправильный логин или пароль");
            return response;
        }

    }
        catch(
    NoSuchAlgorithmException e){
        CustomConsole.printLn("Ошибка алгоритма");
    }
        catch(
    SQLException e) {
        CustomConsole.printLn("Ошибка в получении данных из таблицы");
    }
        catch (NumberFormatException exception) {
        CustomConsole.printError("ID должен быть представлен числом!");
    }
        return null;
}
}
