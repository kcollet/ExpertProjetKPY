package projet.messages.request;

import java.io.Serializable;

public class GameMessageRequest implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
