package projet.messages;

import java.io.Serializable;

public class Error implements Serializable {
   private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Error () {
    	setMessage ("Erreur");
    }
    public Error (String m) {
    	setMessage (m);
    }
}
