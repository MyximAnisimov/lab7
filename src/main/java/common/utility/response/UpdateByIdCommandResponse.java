package common.utility.response;

public class UpdateByIdCommandResponse extends Response{
        public UpdateByIdCommandResponse(String error) {
            super("update_by_id", error);
        }
}
