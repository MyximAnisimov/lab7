package client.commands;

import client.UDPclient;
import common.utility.CustomConsole;
import common.utility.Visibility;
import common.utility.requests.LoginCommandRequest;
import common.utility.requests.Request;
import common.utility.response.LoginCommandResponse;

import java.io.IOException;
import java.util.Scanner;

public class Login extends AbstractCommand {
    private final UDPclient client;
    public static Request request = new Request();
    public Login(UDPclient client){
        super("login","войти в аккаунт пользователя");
        this.client=client;
    }
    /**
     *Выполняет команду
     * @param argument аргумент, введённый пользователем
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String [] arguments){
        Scanner sc = new Scanner(System.in);
            CustomConsole.printLn("Введите имя пользователя: ");
            var user = sc.nextLine().trim();
        CustomConsole.printLn("Введите пароль пользователя: ");
            var password = sc.nextLine().trim();
            try {
                var response = (LoginCommandResponse) client.sendAndReceiveCommand(new LoginCommandRequest(user, password, 0));
if(response.SuccLogin){
    request.setUserId(response.user_id);
    UDPclient.VA.globalArgument = Visibility.LOGGED_USER;
    CustomConsole.printLn("Вы успешно залогинились!");
}
else
{
    CustomConsole.printError("Проверьте корркетность логина и пароля!");
            }}
            catch(IOException e)
        {
            CustomConsole.printLn("Ошибка в работе с базой данных!");
        }

        return false;
    }
}
