package server.commands;

import common.utility.Visibility;
import common.utility.requests.Request;
import common.utility.requests.UpdateByIdCommandRequest;
import common.utility.response.Response;
import common.utility.response.UpdateByIdCommandResponse;
import server.CollectionManager;
import server.DBhandler;
import server.UDPserver;

/**
 * Класс, содержащий команду "update_id". Позволяет обновить элемент коллекции по введённому пользователем id
 */
public class Update_by_id extends AbstractCommand {
    private final CollectionManager collMan;
    public Update_by_id(CollectionManager collMan){
        super("update_by_id","изменение элемента коллекции по его id", Visibility.LOGGED_USER);
        this.collMan=collMan;
    }
    /**
     * Выполняет команду
     * @param argument аргумент, введённый пользователем
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(Request request){
        var req = (UpdateByIdCommandRequest) request;
        try {
            if (!collMan.checkExist(req.person_id)) {
                return new UpdateByIdCommandResponse("Продукта с таким ID в коллекции нет!");
            }
            if (!req.updatedPerson.validate()) {
                return new UpdateByIdCommandResponse( "Поля продукта не валидны! Продукт не обновлен!");
            }
            DBhandler dBhandler = new DBhandler();
            dBhandler.updateElement(req.updatedPerson,req.user_id, req.person_id);
            if(!dBhandler.updateElement(req.updatedPerson,req.user_id, req.person_id)){
                return new UpdateByIdCommandResponse("Вы не можете обновлять не свой элемент коллекции");
            }
            else{
            collMan.getById(req.person_id).update(req.updatedPerson);
            return new UpdateByIdCommandResponse(null);}
        } catch (Exception e) {
            return new UpdateByIdCommandResponse(e.toString());
        }
    }
}
