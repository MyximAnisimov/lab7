package server.commands;

import common.utility.Visibility;
import common.utility.requests.Request;
import common.utility.response.HelpCommandResponse;
import common.utility.response.Response;
import server.managers.CommandManager;

/**
 * Класс, содержащий команду "help". Выводит справку по командам
 */
public class Help extends AbstractCommand {
    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам", Visibility.ALL_USERS);
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(Request request) {
        var helpMessage = new StringBuilder();

        commandManager.getCommands().values().forEach(command -> helpMessage.append(" %-35s%-1s%n".formatted(command.getName(), command.getDescription())));
helpMessage.append(" %-35s%-1s%n".formatted("exit","выход из программы"));
helpMessage.append(" %-35s%-1s%n".formatted("execute_script", "исполнение скрипта из текстовго файла"));
helpMessage.append("Внимание! Не зарегистрированный пользователь может использовать только команды help, exit, sign_up!!");
        return new HelpCommandResponse(helpMessage.toString(), null);
    }
}
