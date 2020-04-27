package projet.serveur
class NomInvalide extends Exception {
	String nomInvalide;
	public NomInvalide (String n){
		nomInvalide = n;
	}
	public String message (){
		return ("Le nom de salle : " + nomInvalide + " est incorrect");
	}
}