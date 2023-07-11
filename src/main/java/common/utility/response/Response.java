package common.utility.response;

import java.io.Serializable;
import java.util.Objects;

public class Response implements Serializable {
    public boolean SuccLogin=false;
    private final String name;
    private final String error;
    private String password;
    public int user_id;
    public boolean SuccReg = false;
    private String errorMessage="Пользователь не зарегистрирован для использования данной команды!";

    public Response(String name, String error) {
        this.name = name;
        this.error = error;
    }
    public Response(String user, String password, int user_id, String error){
    this.name = user;
    this.error = error;
    this.password=password;
    this.user_id=user_id;
    }

    public String getName() {
        return name;
    }

    public String getError() {
        return errorMessage;
    }
    public boolean isErrorResponse() {
        return false;
    }
    public void setSuccReg(boolean SuccReg){
        this.SuccReg=SuccReg;
    }
    public void setSuccLogin(boolean SuccLogin){
        this.SuccLogin=SuccLogin;
    }
    public boolean isSuccLogin(){
        return SuccLogin;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(name, response.name) && Objects.equals(error, response.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, error);
    }

    @Override
    public String toString() {
        return "Response{" +
                "name='" + name + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
