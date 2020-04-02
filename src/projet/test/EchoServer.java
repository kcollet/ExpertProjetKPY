package projet.test;

import projet.client.MainClient;
import projet.messages.request.*;
import projet.messages.response.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;


// A simple EchoServer class, just here for testing on my own if my Main Client can send requests,
// get responses, handle them or handle error...
public class EchoServer {

    private ServerSocket serverSocket;
    private Socket client;
    ObjectOutputStream out;
    ObjectInputStream in;

    public EchoServer(){
        try {
            serverSocket = new ServerSocket(MainClient.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Request Handlers

    private void onGameBeginRequest(GameBeginRequest request){
        GameBeginResponse response = new GameBeginResponse();

        if (Math.random() > .5){
            // MASTER
            response.setKeyword("PASSWORD");
            response.setMaster(true);

        } else {
            // GUESSER
            response.setMaster(false);
        }

        send(response);
    }
    private void onGameMessageRequest(GameMessageRequest request){
        send(new GameMessageResponse().setMessage(request.getMessage()));
    }

    private void onLobbyCreationRequest(LobbyCreationRequest request){
        LobbyCreationResponse response = new LobbyCreationResponse();
        response.setLobbyName(request.getLobbyName());
        send(response);
    }

    private void onLobbyDestructionRequest(LobbyDestructionRequest request){
        LobbyDestructionResponse response = new LobbyDestructionResponse();
        response.setLobbyName(request.getLobbyName());
        send(response);
    }

    private void onLobbyJoinRequest(LobbyJoinRequest request){
        LobbyJoinResponse response = new LobbyJoinResponse();
        response.setLobbyName(request.getLobbyName());
        send(response);
    }

    private void onLobbyLeaveRequest(LobbyLeaveRequest request){
        LobbyLeaveResponse response = new LobbyLeaveResponse();
        response.setLobbyName(request.getLobbyName());
        send(response);
    }

    private void onLobbyListRequest(LobbyListRequest request){
        LobbyListResponse response = new LobbyListResponse();
        response.setLobbys(Arrays.asList("PGM only", "4 vs 4 noobs only", "no scout rush plz", "one for all sion vs soraka"));
        send(response);
    }


    private void handleRequest(){
    //Handle the request sent by a client by calling the appropriate function.
        try {
            Object o = in.readObject();

            if (o instanceof GameBeginRequest) onGameBeginRequest((GameBeginRequest) o);
            if (o instanceof GameMessageRequest) onGameMessageRequest((GameMessageRequest) o);
            if (o instanceof LobbyCreationRequest) onLobbyCreationRequest((LobbyCreationRequest) o);
            if (o instanceof LobbyDestructionRequest) onLobbyDestructionRequest((LobbyDestructionRequest) o);
            if (o instanceof LobbyJoinRequest) onLobbyJoinRequest((LobbyJoinRequest) o);
            if (o instanceof LobbyLeaveRequest) onLobbyLeaveRequest((LobbyLeaveRequest) o);
            if (o instanceof LobbyListRequest) onLobbyListRequest((LobbyListRequest) o);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                client.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void send(Serializable o){
    // A simple function to avoid writing the try/catch everytime I want to send a message.
        try{
            out.writeObject(o);
            out.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private void Run(){
    //Setup the server socket, accept client connection, setup Stream and handle client Request
        while (!Thread.interrupted()){

            try {
                client = serverSocket.accept();
                out = new ObjectOutputStream(client.getOutputStream());
                out.flush();
                in = new ObjectInputStream(client.getInputStream());

                while (!client.isClosed()){
                    handleRequest();
                }

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new EchoServer().Run();
    }
}
