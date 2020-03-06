package projet;
import java.util.Random;
import java.util.Scanner;

public class StringManagement {
	
	private static final int delete_percent = 30;
	
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
