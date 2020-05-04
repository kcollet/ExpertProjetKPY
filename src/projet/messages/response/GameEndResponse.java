package projet.messages.response;

import java.io.Serializable;

public class GameEndResponse implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean victory;

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }
    public GameEndResponse(boolean vic) {
    	victory = vic;
    }
}
