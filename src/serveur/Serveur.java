package serveur;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;
//code repris en partie de la page http://www.codeurjava.com/2014/11/java-socket-clientserveur-mini-chat.html
public class Serveur {
	
	ServerSocket serversocket;
	Socket socketduserveur;
	
	//final PrintWriter out;
	private void match (Object msg) { //fonction qui évalue la nature de la requête est agit en conséquence
		switch (msg.getClass().getName()) {
		case "LobbyCreationRequest" : new Salle (new Session());
		}
	}

	
    public void run () {
           try {
        	   Object msg ;
               FileInputStream fis = new FileInputStream("requete.tmp");
               File fichier = new File ("tmp/serveur.ser");
               ObjectInputStream in = new ObjectInputStream (new FileInputStream (fichier)); 
              msg = in.readObject();
              //tant que le client est connecté
              while(msg!=null){
                 System.out.println("Client : "+msg);
                 match (msg);
                 msg = in.readObject();
              }
              //sortir de la boucle si le client a déconecté
              System.out.println("Client déconecté");
              //fermer le flux et la session socket
              //out.close();
           } catch (IOException e) {
                e.printStackTrace();   
           }
           catch (ClassNotFoundException e) {
        	   e.printStackTrace();
           }
       }
    ;}
	


		  
	  
	  
	    
