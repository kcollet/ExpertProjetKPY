package projet.messages.response;

import java.io.Serializable;

public class GameMessageResponse implements Serializable {
    
	private String message;

    public String getMessage() {
        return message;
    }

    public GameMessageResponse setMessage(String message) {
        this.message = message;
        return this;
    }
    public GameMessageResponse (String msg) {
    	message = msg;
    }
}
