package projet.messages.response;

import java.io.Serializable;

public class GameEndResponse implements Serializable {
    private boolean victory;

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }
}
