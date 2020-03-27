package projet.messages.response;

import java.io.Serializable;

public class LobbyCreationResponse implements Serializable {
    private String lobbyName;

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }
}
