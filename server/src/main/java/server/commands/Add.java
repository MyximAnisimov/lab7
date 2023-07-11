package server.commands;

import common.utility.UserIDNumber;
import common.utility.Visibility;
import common.utility.requests.AddCommandRequest;
import common.utility.requests.LoginCommandRequest;
import common.utility.requests.Request;
import common.utility.response.AddCommandResponse;
import common.utility.response.LoginCommandResponse;
import common.utility.response.Response;
import server.CollectionManager;
import server.DBhandler;
import server.UDPserver;

/**
 * Класс, содержащий команду "add". Добавляет новый элемент в коллекцию
 */
public class Add extends AbstractCommand {
    private final CollectionManager collMan;
    public Add( CollectionManager collMan){
        super("add ","Добавление нового элемента в коллекцию", Visibility.LOGGED_USER);
        this.collMan=collMan;
    }


        /**
         * Выполняет команду
         * @return Успешность выполнения команды.
         */
        @Override
        public Response execute(Request request) {
            var req = (AddCommandRequest) request;
            try {
                if (!req.person.validate()) {
                    return new AddCommandResponse(req.user_id,"Поля продукта не валидны! Продукт не добавлен!");
                }
                DBhandler dBhandler = new DBhandler();
                var person_id =0;
                person_id = dBhandler.addElement(req.person, req.user_id);
                if(person_id>-1){

                    req.person.setID(person_id);
                    req.person.setCreatorId(UDPserver.user_id.globalUserId);
                    collMan.add(req.person);
                    var response = new AddCommandResponse(person_id, null);
                    response.setNewId(req.person.getCreatorId());
                }
                return new AddCommandResponse(person_id, null);
            } catch (Exception e) {
                return new AddCommandResponse(-1, e.toString());
            }

        }
   }
