package serveur;

public class Salle {
	private Session [] sessions = {null,null};
		Partie nouvellePartie () {
			return new Partie (this);
		}
	
 

public Session [] getSessions () {
	return sessions;
}
}
