package common.utility;

import java.util.ArrayDeque;

/**
 * Класс для работы с консолью
 */

public class CustomConsole {



    /**
     * Выводит принятый аргумент в стандартный поток вывода
     * @param toOut информация, которую необходимо вывести
     */
    public static void print(Object toOut){
        System.out.print(toOut);
    }

    /**
     * Выводит принятый аргумент на консоль
     * @param toOut информация, которую необходимо вывести
     */
    public static void printLn(Object toOut) {
        System.out.println(toOut);
    }

    /**
     * Выводит ошибку на консоль
     *@param toOut ошибка, которую необходимо вывести
     */
    public static void printError(Object toOut) {
        System.out.println("Ошибка: " + toOut);
    }

    /**
     * Функция для приёма ввода пользователя и исполнения команды (Если таковая существует)
     * @param userCommand ввод имени команды пользователем
     * @return статус команды
     */

}
