package projet.serveur;

import java.net.Socket;
import java.io.*;
import projet.messages.Error;
import projet.messages.request.*;
import projet.messages.response.*;


//import projet.serveur.exceptions.*;


public class Session implements Runnable {
	//information sur le client
	private Salle salle;
	private ObjectInputStream entree;
	private ObjectOutputStream sortie;
	private Socket socket;
	private Thread thread;
	
	private void match (Object req) { 
	try{//fonction qui évalue la nature de la requête est agit en conséquence
		
		if (req instanceof LobbyCreationRequest) new Salle (this);
		else if (req instanceof LobbyDestructionRequest) detruireSalle();
		else if (req instanceof LobbyListRequest) reponse (new LobbyListResponse(Salle.getListe())); //getListe renvoie  List<String>
		else if (req instanceof LobbyJoinRequest) Salle.nouveauDans(this,((LobbyJoinRequest)req).getLobbyName());
		else reponse (new Error ("requete invalide"));
		}
	
	catch (Exception e){
		//
	}
	}

	public void reponse (Object rep){
		try {
		sortie.writeObject (rep);
		sortie.flush();
		System.out.println ("Envoie" + rep);}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public void setSalle (Salle salle) {
		this.salle = salle;
	}
	public synchronized void quitterSalle () {
		
		reponse(new LobbyDestructionResponse("Vous avez quitté la salle : " + salle.getNom() + " ou celle-ci a été détruite"));
		salle = null;
	}
	public void detruireSalle () {
		try {
		salle.detruire();
		salle = null;
		}
		catch (NullPointerException n) {reponse (new Error ("Vous n'êtes dans aucune salle") );}
		
	}
	//code repris en partie de la page https://defaut.developpez.com/tutoriel/java/serveur/multithread/
	public Session (Socket socket) 
	{	this.socket = socket;
		try {
			sortie = new ObjectOutputStream (socket.getOutputStream());
			entree = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread = new Thread (this);
		thread.start();
			}
		
		public void run () {
	           try {
	        	  
	        	   Object msg ;
	              
	              msg = entree.readObject();
	              //tant que le client est connecté
	              while(msg!=null){
	                 System.out.println("Client : " + msg);
	                 match (msg);
	                 msg = entree.readObject();
	              }
	              //sortir de la boucle si le client a déconecté
	              if (salle != null)
	            	  quitterSalle();
	              System.out.println("Fin de la session");
	              //fermer le flux et la session socket
	              //out.close();
	              socket.close();
	           } catch (IOException e) {
	                e.printStackTrace();   
	           }
	           catch (ClassNotFoundException e) {
	        	   e.printStackTrace();
	           }
	       }
			
	}


