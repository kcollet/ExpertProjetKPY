package projet.serveur;
import java.util.Random;

	public class Partie {
		final Session maitre;
		final Session chercheur;
		final Salle salle;
		final String motADeviner;
		Partie (Salle salle){
			//Thread partie = new Thread (new Runnable())
			Random random = new Random ();
			if (random.nextInt(2)==0) {
				maitre = salle.getSessions()[0];
				chercheur = salle.getSessions()[1];
				}
			else {maitre = salle.getSessions()[1];
			chercheur = salle.getSessions()[0];
				
			}
			this.salle = salle;
			motADeviner = Choisir.choisir(); 
			
			
			
			
			
			
		}

}
