package projet.messages.response;

import java.io.Serializable;

public class GameMessageResponse implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
