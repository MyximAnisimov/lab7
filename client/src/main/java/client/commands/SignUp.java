package client.commands;

import client.UDPclient;
import common.utility.CustomConsole;
import common.utility.requests.SignUpCommandRequest;
import common.utility.response.SignUpCommandResponse;

import java.io.IOException;
import java.util.Scanner;

public class SignUp extends AbstractCommand{
    private final UDPclient client;
    public SignUp(UDPclient client){
        super("sign_up","регистрация пользователя");
        this.client=client;
    }
    @Override

    public boolean execute(String [] arguments){
        Scanner sc = new Scanner(System.in);
        CustomConsole.printLn("Введите имя пользователя: ");
        var user = sc.nextLine().trim();
        CustomConsole.printLn("Введите пароль пользователя: ");
        var password = sc.nextLine().trim();
        try {
          var response = (SignUpCommandResponse) client.sendAndReceiveCommand(new SignUpCommandRequest(user, password));
            if(response.SuccReg){
                CustomConsole.printLn("Вы успешно зарегистрировались!");
            }
            else
            {
                CustomConsole.printLn("Пользователь с таким именем уже существует");
            }}
        catch(IOException e)
        {
            CustomConsole.printLn("Ошибка");
        }

        return false;
    }
}

