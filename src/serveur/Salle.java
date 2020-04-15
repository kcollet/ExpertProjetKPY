package serveur;
import java.util.ArrayList;

public class Salle {
	private static int nbDeSalles = 0;
	private static ArrayList<Salle> Liste;
	private String nomDeLaSalle;
	private Session [] sessions = {null,null};
	
public static void TenterDeDetruireSalle (String nom) {
	boolean succes = false;
	for (int i = 0 ; i <= nbDeSalles ; i++) {
	
		if (Liste.get(i).getNom() == nom) {
			Liste.remove(i);
			succes = true;
			break;}}
	//instruction en cas d'échec
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
	
}
public void SetSecondJoueur (Session joueur) {
	sessions [1] = joueur;
	new Partie (this);
	
}
public String getNom() {
	return nomDeLaSalle;
	
}
}

