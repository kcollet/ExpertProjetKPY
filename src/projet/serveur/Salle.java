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
					Partie partie;

public static void nouveauDans (Session joueur, String nomDeSalle)  {
	try {
		getInstance (nomDeSalle).ajouterJoueur (joueur);
	}
	catch (NomInvalide n) {
		n.printStackTrace();
		joueur.reponse(new Error (n.getMessage()));
	}
}	
public static Salle getInstance(String nom)	throws NomInvalide {
	System.out.println("appel de getInstance("+ nom + ")");
	for (Salle s : liste) {
		if (s.getNom().contentEquals (nom))
			return s;
	}
	throw new NomInvalide (nom);		

}

public static Vector<String> getListe(){
	Vector<String> retour = new Vector<String>();
	for (Salle s : liste) {
		if (s.sallePleine)
			retour.add(s.nomDeLaSalle+ " (salle pleine)");
		else 
			retour.add(s.nomDeLaSalle);
	
	}
	System.out.println("appel de Salle.getListe()");
	return retour ;
}

public Vector<Session> getSessions () {
	return sessions;
}
public Salle (Session createur) {
	capacite = 2;
	sessions = new Vector<Session> ();
	sessions.add(createur);
	createur.salle = this;
	liste.add(this);
	nomDeLaSalle = "salle"+ (nbSallesCrees ++);  
	createur.reponse(new LobbyCreationResponse(nomDeLaSalle));
	
}
public synchronized void ajouterJoueur (Session joueur)  {
	if (sallePleine == false & joueur.getSalle()!= this) {
	sessions.add(joueur);
	joueur.salle = this; 
	if (sessions.size()== capacite)
		sallePleine = true;
		joueur.reponse(new LobbyJoinResponse(this.getNom()));}
	else 
		joueur.reponse(new Error ("La salle est pleine ou vous êtes déjà dedans"));}
	
	
	

public synchronized void  retirerJoueur (Session joueur) { //appelée dans detruire et dans Session.match()
System.out.println("appel de retirerJoueur");
if (sessions.remove(joueur))
	{joueur.reponse(new LobbyLeaveResponse(nomDeLaSalle));
	joueur.salle = null;
		}

	
}
public synchronized void detruire (){
	sessions.forEach(j->retirerJoueur(j));
	liste.remove(this);
	nomDeLaSalle = null;

}
public synchronized void nouvellePartie() {
	if (partie == null & sessions.size() >= 2)
	partie = new Partie(this);
	else 
		System.out.println("pas de nouvelle partie");
}

public String getNom() { //un accesseur inutile
	return nomDeLaSalle;
	
}

}

