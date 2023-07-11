package server.commands;

import common.utility.Visibility;
import common.utility.requests.Request;
import common.utility.response.Response;

/**
 * Интерфейс для создание всех команд
 */
public interface Command{
    public String getName();
    public String getDescription();
    public Visibility getVisibility();
public Response execute(Request request);
}
