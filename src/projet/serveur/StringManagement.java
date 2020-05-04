package projet.serveur;
import java.util.Random;
import projet.serveur.exceptions.MotTropProche;


public class StringManagement {
	
	private static final int delete_percent = 30;
	public static String filtrer (String indice, String cible) throws MotTropProche {
		if (! indice.equals(cible) & pasTropProche(indice,cible)) 
		return eraseLetters (indice); 
		else 
			throw new MotTropProche ();
	}
	public static boolean chaineIdentique (String indice, int i, String cible, int c) { //on refuse l'indice s'il a une suite de 4 lettres commune avec la suite
		int i1 = i;
		int c1 = c;
		int correspondance = 0;
		while (i1 < indice.length() & c1 <cible.length()) {
			if (cible.charAt(c) == indice.charAt(i1)) { 
					if (++ correspondance > 4)
						return true;
					}
			else 
				return false;
			i1 ++;
			c1 ++;
			}
		return false;}
			
		
		
	
	public static boolean pasTropProche (String indice, String cible) {
		for (int c = 0 ; c < cible.length(); c++ ) {
			for (int i = 0 ; i < indice.length(); i++) {
				if (chaineIdentique(indice,i,cible,c))
					return false;
				}}
		return true;
	}
	public static String eraseLetters(String string)
	{
		Random random = new Random();
		int string_size = string.length();
		char[] chars = string.toCharArray(); // Convert string in char array
		int[] matrice = new int[string_size];
		int char_to_delete = (string.length() * delete_percent / 100); // Number of char to delete
		
		for (int i = 0; i < matrice.length; i++) {
			
			int rand = random.nextInt(2);  // 0 or 1
			
			if (char_to_delete > 0 && rand == 0) {
				matrice[i] = 0;
				char_to_delete -= 1;
			} else {
				matrice[i] = 1;
			}
		}
		
		for (int i = 0; i < matrice.length; i++)
		{	
			if (matrice[i] ==  0) 
				chars[i] = '*';
		}	
		return new String(chars);	
	}
}
