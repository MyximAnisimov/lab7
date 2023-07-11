package server.commands;

import common.utility.Visibility;
import common.utility.requests.Request;
import common.utility.response.ClearCommandResponse;
import common.utility.response.Response;
import server.CollectionManager;
import server.DBhandler;

/**
 * Класс, содержащий команду "clear". Очищает коллекцию
 */
public class Clear extends AbstractCommand{
    private final CollectionManager collMan;

    public Clear(CollectionManager collMan) {
        super("clear", "очистить коллекцию", Visibility.LOGGED_USER);
        this.collMan=collMan;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(Request request) {
        try {
            collMan.clearCollection();
            DBhandler dBhandler = new DBhandler();
            dBhandler.clearCollection(request.user_id);
            return new ClearCommandResponse(null);
        } catch (Exception e) {
            return new ClearCommandResponse(e.toString());
        }
    }
    }

