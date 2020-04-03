package serveur;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
//code repris en partie de la page http://www.codeurjava.com/2014/11/java-socket-clientserveur-mini-chat.html
public class Serveur {
	public static void demarrer () {
	ServerSocket serversocket;
	Socket socketduserveur;
	
	final PrintWriter out;
	final Scanner sc=new Scanner(System.in);
	Thread recevoir= new Thread(new Runnable() {
        Object msg ;
        final ObjectInputStream in = new ObjectInputStream ();//ligne à corriger, il faut ajouter le fichier
        public void run() {
           try {
              msg = in.readObject();
              //tant que le client est connecté
              while(msg!=null){
                 System.out.println("Client : "+msg);
                 msg = in.readObject();
              }
              //sortir de la boucle si le client a déconecté
              System.out.println("Client déconecté");
              //fermer le flux et la session socket
              out.close();
           } catch (IOException e) {
                e.printStackTrace();   
           }
           catch (ClassNotFoundException e) {
        	   e.printStackTrace();
           }
       }
    });}
	
}

		  
	  
	  
	    
