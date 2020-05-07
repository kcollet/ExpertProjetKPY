package projet.serveur;
import java.util.Random;
import projet.messages.request.*;
import projet.messages.response.*;
import projet.serveur.exceptions.MotTropProche;
import projet.messages.Error;

	public class Partie {
		private final Session maitre;
		private final Session chercheur;
		private final Salle salle;
		private final String motADeviner;
		private boolean attendIndice;
		private boolean attendProposition;
		private int propositionsRestantes = 5;
		
		public Partie (Salle salle){
			//Thread partie = new Thread (new Runnable())
			Random random = new Random ();
			if (random.nextInt(2)==0) {
				maitre = salle.getSessions().get(0);
				chercheur = salle.getSessions().get(1);
				}
			else {maitre = salle.getSessions().get(1);
			chercheur = salle.getSessions().get(0);
				
			}
			this.salle = salle;
			motADeviner = Choisir.choisir().toLowerCase(); 
			attendIndice = true;
			maitre.estEnPartie = true;
			chercheur.estEnPartie = true;
			maitre.reponse (new GameBeginResponse (true,motADeviner));
			chercheur.reponse(new GameBeginResponse(false,motADeviner));}//le mot à deviner est transmis au chercheur mais l'application client ne l'affiche pas

			public synchronized void fin (boolean victoire) {
				maitre.reponse(new GameEndResponse(victoire));
				chercheur.reponse (new GameEndResponse(victoire));
				maitre.estEnPartie = false;
				chercheur.estEnPartie = false;
				salle.partie = null;
				
			}
			public synchronized void gererRequete (Session joueur, Object req) {
			if (req instanceof GameMessageRequest) {
				if (joueur == maitre)
					indice(((GameMessageRequest) req).getMessage());
				else proposition (((GameMessageRequest)req).getMessage());}
			else joueur.reponse(new Error ("requete impossible pendant une partie"));
			
			
		}
		public void indice (String msg) {
			if (attendIndice) {
				try {
				GameMessageResponse rep = new GameMessageResponse (StringManagement.filtrer(msg,motADeviner));
				attendIndice = false;
				attendProposition = true;
				chercheur.reponse(rep);}
				catch (MotTropProche p) {
					maitre.reponse(new Error("mot trop proche"));
				}
			}
			else {
				maitre.reponse(new Error ("Attendez une proposition avant de donner le nouvel indice") );
			}
				
			}
		public void proposition (String pr) {
			if (attendProposition) {
				if (pr.toLowerCase().equals(motADeviner)) {
					fin(true);
					
				}
				else if (-- propositionsRestantes == 0) {
					fin(false);
					
				}
				GameMessageResponse rep = new GameMessageResponse (pr + " ?");
				attendIndice = true;
				attendProposition = false;
				maitre.reponse(rep);
			}
			else {
				chercheur.reponse(new Error ("Attendez un indice avant de faire une proposition") );
			}
		}
			
			
			}
	


