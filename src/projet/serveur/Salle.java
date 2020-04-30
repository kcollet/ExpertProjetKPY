package projet.serveur;
import java.util.Vector;
import projet.messages.response.*;
import projet.serveur.exceptions.NomInvalide;
/* COMMENTAIRE MULTILIGNE POUR TESTER UN TRUC */

public class Salle {
	private static Vector<Salle> liste = new Vector<Salle>();
	private static int nbSallesCrees = 0;
	private String nomDeLaSalle;
	private Vector<Session> sessions;
	private int capacite = 2;
	private boolean sallePleine = false; 

public static void nouveauDans (Session joueur, String nomDeSalle)  {
	try {
		getInstance (nomDeSalle).ajouterJoueur (joueur);
	}
	catch (NomInvalide n) {
		joueur.reponse(new Error (n.getMessage()));
	}
}	
public static Salle getInstance(String nom)	throws NomInvalide {
	
	for (Salle s : liste) {
		if (s.getNom()==nom)
			return s;
	}
	throw new NomInvalide (nom);		

}

public static Vector<String> getListe(){
	Vector<String> retour = new Vector<String>();
	for (Salle s : liste) {
		if (s.sallePleine)
			retour.add(s.getNom()+ " (salle pleine)");
		else 
			retour.add(s.getNom());
		
	}
	System.out.println("appel de Salle.getListe()");
	return retour ;
}

public Vector<Session> getSessions () {
	return sessions;
}
public Salle (Session createur) {
	capacite = 2;
	Vector<Session> sessions = new Vector<Session> (capacite);
	sessions.add(createur);
	createur.setSalle(this);
	liste.add(this);
	nomDeLaSalle = "salle"+ (nbSallesCrees ++);  
	createur.reponse(new LobbyCreationResponse(nomDeLaSalle));
	
}
public synchronized void ajouterJoueur (Session joueur)  {
	if (sallePleine == false & joueur.getSalle()!= this) {
	sessions.add(joueur);
	if (sessions.size()== capacite)
		sallePleine = true;
		joueur.reponse(new LobbyJoinResponse(this.getNom()));}
	else 
		joueur.reponse(new Error ("La salle est pleine ou vous êtes déjà dedans"));}
	
	
	

public synchronized void  retirerJoueur (Session joueur) {
if (sessions.remove(joueur))
	{joueur.quitterSalle();
		}

	
}
public synchronized void detruire (){
	sessions.forEach(j->retirerJoueur(j));
	liste.remove(this);
	nomDeLaSalle = null;

}
public String getNom() {
	return nomDeLaSalle;
	
}

}

