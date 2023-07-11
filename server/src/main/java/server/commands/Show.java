package server.commands;

import common.utility.Visibility;
import common.utility.requests.Request;
import common.utility.response.Response;
import common.utility.response.ShowCommandResponse;
import server.CollectionManager;

/**
 * Класс, содержащий команду "show". Вывести на экран все коллекции
 */
public class Show extends  AbstractCommand {
    private final CollectionManager collMan;
    public Show(CollectionManager collMan){
        super("show","вывести все элементы коллекции", Visibility.LOGGED_USER);
        this.collMan=collMan;
    }
    /**
     *Выполняет команду
     * @param argument аргумент, введённый пользователем
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(Request request)  {
        try {
            return new ShowCommandResponse(collMan.sorted(), null);
        } catch (Exception e) {
            return new ShowCommandResponse(null, e.toString());
        }
    }
}
