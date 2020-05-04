package projet.serveur;

import java.net.Socket;
import java.io.*;
import projet.messages.Error;
import projet.messages.request.*;
import projet.messages.response.*;


//import projet.serveur.exceptions.*;


public class Session extends Thread {
	//information sur le client
	Salle salle;
	private  ObjectInputStream entree;
	private  ObjectOutputStream sortie;
	private final Socket socket;
	boolean estEnPartie = false;
	
	private void match (Object req) { 
	try{//fonction qui évalue la nature de la requête est agit en conséquence
		
		if (req instanceof LobbyCreationRequest) new Salle (this);
		else if (req instanceof LobbyDestructionRequest) detruireSalle();
		else if (req instanceof LobbyListRequest) reponse (new LobbyListResponse(Salle.getListe())); //getListe renvoie  List<String>
		else if (req instanceof LobbyJoinRequest) Salle.nouveauDans(this,((LobbyJoinRequest)req).getLobbyName());
		else if (req instanceof GameBeginRequest) salle.nouvellePartie();
		else if (req instanceof LobbyLeaveRequest) salle.retirerJoueur(this);
		else reponse (new Error ("requete invalide, avez vous pensé au /lobby ?"));
		}
	catch (NullPointerException n) {
		//reponse (new Error ("Peut-être n'êtes vous pas dans une salle"));
		n.printStackTrace(System.out);
	}
	
	catch (Exception e){
		reponse (new Error ("Erreur inatendue"));
		//
	}
	}

	public void reponse (Object rep){
		try {
		sortie.writeObject (rep);
		sortie.flush();
		System.out.println ("Envoie" + rep);
		if (rep instanceof Error)
			System.out.println(((Error)rep).getMessage());}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public Salle getSalle () {return salle;}
	public void setSalle (Salle salle) {
		this.salle = salle;
	}
	public void setEnPartie (boolean b) {
		estEnPartie = b ;
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
		
		System.out.println("Session créée");

			}
		
		public void run () {
	           try {
	        	  
	        	   Object msg ;
	              
	              msg = entree.readObject();
	              //tant que le client est connecté
	              while(msg!=null){
	                 System.out.println("Client : " + msg);
	                 if (! estEnPartie)
	                	 match (msg);
	                 else
	                	 salle.partie.gererRequete(this, msg);
	                 msg = entree.readObject();
	              }
	              //sortir de la boucle si le client a déconecté
	              //fermer le flux et la session socket
	              //out.close();
	              socket.close();
	           } 
	           catch (NullPointerException n) {
	        	   n.printStackTrace(System.out);
	           }
	           catch (Exception e) {
	                if (salle != null) {
	                	if (salle.partie != null)
	                		salle.partie.fin(false);
	                salle.retirerJoueur(this);		
	                }   
	           }
	          
	           
	       }
			
	}


