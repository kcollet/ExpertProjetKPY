package projet.client;

import projet.messages.request.*;
import projet.messages.response.*;
import projet.messages.Error;
import projet.util.CommandManager;
import projet.util.CommandParser;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class MainClient {

    //Public for testing, maybe back to private later.
    public static final String SERVER_ADRESS = "127.0.0.1";
    public static final int PORT = 6666;

    private Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    	boolean exiting = false;
    CommandManager commandManager;
    CommandParser parser;

    public MainClient(){
        parser = new CommandParser();
        commandManager = new CommandManager();
        InitSocket();
        InitCommands();
    }


    private void InitSocket(){
    // Initialize the client socket and the streams with the server.
        try {
            socket = new Socket(SERVER_ADRESS, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void InitCommands(){
        //Initialize the command map in command manager and also the descriptions map which holds commands descriptions.
        //commandManager.setCommand("exit", this::exitCommand);
        commandManager.setCommand("say", this::sayCommand);
        commandManager.setCommand("start", this::gameBeginCommand);
        commandManager.setCommand("lobby", this::lobbyCommand);

        commandManager.setDescription("exit", "Pas d'arguments, quitte le client.");
        commandManager.setDescription("say", "Commande par défaut.");
        commandManager.setDescription("start", "Se lance dans un lobby, démarre le partie.");
        commandManager.setDescription("lobby create", "Cree un nouveau lobby");//le nom du lobby est défini par le serveur pour eviter l'homonymie
        commandManager.setDescription("lobby destroy", "Détruit le lobby.");// peut-être plus tard "lobby destroy <name>", "Détruit le lobby <name>."
        commandManager.setDescription("lobby join <name>", "Rejoins le lobby <name>.");
        commandManager.setDescription("lobby leave", "Quitte le lobby");//peut-être plus tard "lobby leave <name>", "Quitte le lobby <name>."
        commandManager.setDescription("lobby list", "Donne la liste des lobbys.");
    }

    ///////////////////////////////////////////////////////////////////////////////

    // Commands Methods

    /*private void exitCommand(CommandParser parser){
        exiting = true;
    } //fonctionnalité non implémentée car cource de bug*/

    private void sayCommand(CommandParser parser){
        int param_numbers = parser.getParameterCount();
        String message = parser.joinParameters(1, param_numbers);
        GameMessageRequest request = new GameMessageRequest().setMessage(message);
        send(request);
    }

    private void gameBeginCommand(CommandParser parser){
        send(new GameBeginRequest());
    }

    private void lobbyCommand(CommandParser parser){
    // A simple command with the format [lobby <param1> "param2"]. param1 is the actual command [create|destroy|join|leave] and param2  is a lobby name.
        switch (parser.getStringParameter(1)){
            case "create":
                LobbyCreationRequest createRequest = new LobbyCreationRequest();
                send(createRequest);
                break;

            case "destroy":
                LobbyDestructionRequest destructionRequest = new LobbyDestructionRequest();
                //destructionRequest.setLobbyName(parser.getStringParameter(2)); les clients ne sont que dans une seule salle à la fois
                send(destructionRequest);
                break;

            case "join":
                LobbyJoinRequest joinRequest = new LobbyJoinRequest();
                joinRequest.setLobbyName(parser.getStringParameter(2));
                send(joinRequest);
                break;

            case "leave":
                LobbyLeaveRequest leaveRequest= new LobbyLeaveRequest();
                //leaveRequest.setLobbyName(parser.getStringParameter(2)); idem que plus haut
                send(leaveRequest);
                break;

            case "list":
                LobbyListRequest listRequest = new LobbyListRequest();
                send(listRequest);
                break;
            default:
                throw new RuntimeException();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Responses Handlers
    private void onGameBeginResponse (GameBeginResponse response){
        if (response.isMaster()){
            System.out.println("Tu es le master !");
            System.out.println("Voici le mot à faire deviner : " + response.getKeyword());
        } else {
            System.out.println("Tu es le guesser ! Attends le message du Master !");
        }
    }

    private void onGameEndResponse(GameEndResponse response){
        if (response.isVictory()){
            System.out.println("Vous avez gagné !");
        } else{
            System.out.println("Partie perdue...");
        }
    }

    private void onGameMessageResponse(GameMessageResponse response){
        System.out.println("Message : " + response.getMessage());
    }

    private void onLobbyCreationResponse(LobbyCreationResponse response){
        System.out.println("Lobby Created : " + response.getLobbyName());
    }

    private void onLobbyDestructionResponse(LobbyDestructionResponse response){
        System.out.println("Lobby Destroyed : " + response.getLobbyName());
    }

    private void onLobbyJoinResponse(LobbyJoinResponse response){
        System.out.println("Joined Lobby : " + response.getLobbyName());
    }

    private void onLobbyLeaveResponse(LobbyLeaveResponse response){
        System.out.println("Left Lobby : " + response.getLobbyName());
    }

    private void onLobbyListResponse(LobbyListResponse response){
        List<String> lobbys = response.getLobbys();
        int i = 1;
        System.out.println("Lobby list :");
        for (String lobbyName : lobbys){
            System.out.println("\t" + i + ") " + lobbyName);
            i++;
        }
    }


    	void handleResponse(Object o){
    // A function that handle the server Response to the previous request by calling the appropriate function.
       
            if (o instanceof GameBeginResponse) onGameBeginResponse((GameBeginResponse) o);
            else if (o instanceof GameEndResponse) onGameEndResponse((GameEndResponse) o);
            else if (o instanceof GameMessageResponse) onGameMessageResponse((GameMessageResponse) o);
            else if (o instanceof LobbyCreationResponse) onLobbyCreationResponse((LobbyCreationResponse) o);
            else if (o instanceof LobbyDestructionResponse) onLobbyDestructionResponse((LobbyDestructionResponse) o);
            else if (o instanceof LobbyJoinResponse) onLobbyJoinResponse((LobbyJoinResponse) o);
            else if (o instanceof LobbyLeaveResponse) onLobbyLeaveResponse((LobbyLeaveResponse) o);
            else if (o instanceof LobbyListResponse) onLobbyListResponse((LobbyListResponse) o);
            else if (o instanceof Error) System.out.println ("Erreur: " + ((Error) o).getMessage());

       
    }


    private void send(Serializable o){
    // A simple function to send a message.
        try{
            out.writeObject(o);
            out.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    public void Run (){
        //Main loop of a main client. Get user input, and handle it as a command request if possible. /exit to close.

        // An equivalent of scanner.
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (!exiting){
            try {
                String line = reader.readLine().trim();
                if(line.startsWith("/")){
                    line = line.substring(1); //"remove" the first character
                } else {
                    line = "say " + line;
                }
                parser.parse(line);
                commandManager.executeCommand(parser);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String... args){
    	MainClient client = new MainClient();
    	new Recevoir (client).start();
       client.Run();
    }
}
