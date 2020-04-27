package projet.messages.request;

import java.io.Serializable;

public class LobbyDestructionRequest implements Serializable {
    private String lobbyName;

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }
   
}
