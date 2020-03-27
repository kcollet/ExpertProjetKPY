package projet.messages.response;

import java.io.Serializable;

public class GameBeginResponse implements Serializable {
    private boolean master;
    private String keyword;

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
