package projet.serveur.exceptions;
public class NomInvalide extends Exception {
	String nomInvalide;
	public NomInvalide (String n){
		nomInvalide = n;
	}
	public String getMessage (){
		return ("Le nom de salle : " + nomInvalide + " est incorrect");
	}
}