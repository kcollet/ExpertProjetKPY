package projet.messages.response;

import java.io.Serializable;
import java.util.List;

public class LobbyListResponse implements Serializable {
    private List<String> lobbys;

    public void setLobbys(List<String> lobbys) {
        this.lobbys = lobbys;
    }

    public List<String> getLobbys() {
        return lobbys;
    }
    public LobbyListResponse (List<String> l){
    	setLobbys(l);

    }
}
