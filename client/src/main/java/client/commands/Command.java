package client.commands;

/**
 * Интерфейс для создание всех команд
 */
public interface Command {
    public String getName();
    public String getDescription();
public abstract boolean execute(String [] userCommand);
}
