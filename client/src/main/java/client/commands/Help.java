package client.commands;

import client.UDPclient;
import common.exceptions.WrongAmountOfElementsException;
import common.utility.CustomConsole;
import common.utility.requests.HelpCommandRequest;
import common.utility.response.HelpCommandResponse;

import java.io.IOException;

/**
 * Класс, содержащий команду "help". Выводит справку по командам
 */
public class Help extends AbstractCommand {
    UDPclient client;
    public Help(UDPclient client){
        super("help"," вывести все доступные команды");
        this.client = client;
    }
    /**
     *Выполняет команду
     * @param argument аргумент, введённый пользователем
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String [] argument){
        try {
            if (!argument[1].isEmpty()) throw new WrongAmountOfElementsException();
            var response = (HelpCommandResponse) client.sendAndReceiveCommand(new HelpCommandRequest());
            CustomConsole.printLn(response.helpMessage);
        } catch (WrongAmountOfElementsException exception) {
            CustomConsole.printLn("Использование: '" + getName() + "'");
        }
        catch (IOException e){
            CustomConsole.printError("Ошибка в считывании команды");
        }
        return false;
    }

}
