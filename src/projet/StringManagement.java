package projet;

import java.util.Random;
import java.util.Scanner;

public class StringManagement {
	
	private static int delete_percent = 30;

	public static void main(String[] args) {
		
		String user_word = "";
		String filtered_word = "";
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Veuillez taper une phrase:");
		user_word = sc.nextLine();
		filtered_word = eraseLetters(user_word);
		System.out.println("Mot corrigé " + filtered_word);
		sc.close();
	}
	
	public static String eraseLetters(String string) {
		
		int string_size = string.length();
		char[] chars = string.toCharArray(); // Convert string in char array
		int char_to_delete = (string.length() * delete_percent / 100); // Number of char to delete
		System.out.println("Characters to delete : " +  char_to_delete);
		
		Random random = new Random();
		
		for (int i = 0; i < char_to_delete; i++)
		{
			int randomChar = random.nextInt(string_size- i);
			
			//Debug
			System.out.println(randomChar);
			System.out.println(chars);
			System.out.println("i = " + i);
			
			if (chars[randomChar] == ' ') {
				i --;
			} else {
				chars[randomChar] = ' '; // Supprime les lettres --> Corriger la fonction pour qu'un même indice
				//ne puisse pas être selectionner 2x. Faire le truc a l'envers --> Garder 70% des characters.
			}
		
		}
		
		String s = new String(chars);
		return s;
	}

}
