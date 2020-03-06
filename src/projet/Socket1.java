package projet;

public class Socket1 {
	
	public static void checkTheWord(String keyWord, String carlAnswers) {
		carlAnswers = carlAnswers.toLowerCase();
		String[] sentence = carlAnswers.split(" ");
		boolean foundIt = false;
		for(String word: sentence) {
		    if(word.equals(keyWord))
		        foundIt = true;
		}
		if (foundIt)
			System.out.println("Carl a trouvé le bon mot !");
	    else
	    	System.out.println("Carl est un gros raté !");
	}
	
	public static void main(String[] args) {
		String motTest = "coucou";
		String phraseTest = "Coucou mes petits chats !";
		checkTheWord(motTest, phraseTest);
	}
}
