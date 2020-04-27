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
public static void TenterDeDetruireSalle (String nom) {
	try{
	getInstance(nom).detruire();}
	catch (NomInvalide n) {

	}
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
	
}
public void AjouterJoueur (Session joueur) {
	sessions [1] = joueur;
	new Partie (this);
	
}
public void Detruire (){

	Liste.remove(this);
}
public String getNom() {
	return nomDeLaSalle;
	
}

}

