package projet.serveur;
import java.util.ArrayList;

public class Salle {
	private static int nbDeSalles = 0;
	private static ArrayList<Salle> Liste;
	private String nomDeLaSalle;
	private Session [] sessions = {null,null};
public static Salle getInstance(String nom)	throws NomInvalide {
	for (int i = 0 ; i <= nbDeSalles ; i++) {
	
		if (Liste.get(i).getNom() == nom) 
			return Liste.get(i);
			}
	//procedure d'erreur
	throw new NomInvalide (nom);		

}

public static List<String> getListe(){
	retour = new List<String>();
	Liste.forEach (s -> retour.add(s.getNom()));
	return retour ;
}

public Session [] getSessions () {
	return sessions;
}
public Salle (Session createur) {
	sessions[0] = createur;
	createur.setSalle(this);
	Liste.add(this);
	nbDeSalles ++;
	nomDeLaSalle = "Salle numero " + nbDeSalles;
	createur.reponse(new LobbyCreationResponse(nomDeLaSalle));
	
}
public void AjouterJoueur (Session joueur) {
	sessions [1] = joueur;
	new Partie (this);
	
}
public void detruire (){
	if (sessions[0].isNotNull()){
		sessions[0].setSalle(null);
		sessions[0].reponse(new LobbyDestructionResponse(nomDeLaSalle));

	}
	if (sessions[1].isNotNull()){
		sessions[1].setSalle(null);
		sessions[1].reponse(new LobbyDestructionResponse(nomDeLaSalle));

	}
	
	Liste.remove(this);
	nbDeSalles -- ;
	nomDeLaSalle = null;

}
public String getNom() {
	return nomDeLaSalle;
	
}

}

