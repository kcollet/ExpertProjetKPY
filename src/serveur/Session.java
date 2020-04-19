package serveur;

import java.net.Socket;
import java.io.*;


public class Session implements Runnable {
	//information sur le client
	private Salle salle;
	private Thread lireMessage;
	private ObjectInputStream entree;
	private PrintWriter sortie;
	private int numeroDeSession;
	private Socket socket;
	private Thread thread;
	
	private void match (Object msg) { //fonction qui évalue la nature de la requête est agit en conséquence
		switch (msg.getClass().getName()) {
		case "LobbyCreationRequest" : new Salle (this);
		}
	}

	public void setSalle (Salle salle) {
		this.salle = salle;
	}
	//code repris en partie de la page https://defaut.developpez.com/tutoriel/java/serveur/multithread/
	public Session (Socket socket) 
	{	this.socket = socket;
		thread = new Thread ();
		thread.start();
			}
		
		public void run () {
	           try {
	        	  
	        	   Object msg ;
	               entree = new ObjectInputStream(socket.getInputStream());
	              msg = entree.readObject();
	              //tant que le client est connecté
	              while(msg!=null){
	                 System.out.println("Client : "+msg);
	                 match (msg);
	                 msg = entree.readObject();
	              }
	              //sortir de la boucle si le client a déconecté
	              System.out.println("Fin de la session " + numeroDeSession);
	              //fermer le flux et la session socket
	              //out.close();
	           } catch (IOException e) {
	                e.printStackTrace();   
	           }
	           catch (ClassNotFoundException e) {
	        	   e.printStackTrace();
	           }
	       }
			
	}


