package server.commands;

import common.utility.Visibility;
import common.utility.requests.Request;
import common.utility.response.PrintFieldDescendingPassportIdCommandResponse;
import common.utility.response.Response;
import server.CollectionManager;

/**
 * Класс, содержащий команду "print_field_descending_passport_id". Сортирует элементы коллекции по ID паспорта в порядке убывания
 */
public class Print_field_descending_passport_id extends AbstractCommand {
    private final CollectionManager collMan;
    public Print_field_descending_passport_id(CollectionManager collMan){
        super("print_field_descending_passport_id","вывод значения поля passportID всех элементов в порядке убывания", Visibility.LOGGED_USER);
        this.collMan=collMan;
    }
    /**
     *Выполняет команду
     * @param argument аргумент, введённый пользователем
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(Request request){
        try {
            return new PrintFieldDescendingPassportIdCommandResponse(collMan.getSorted(), null);
        } catch (Exception e) {
            return new PrintFieldDescendingPassportIdCommandResponse(null, e.toString());
        }
}
    }
