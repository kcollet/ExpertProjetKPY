package projet.serveur;
import java.io.IOException;
import java.net.ServerSocket;
//code repris en partie de la page https://defaut.developpez.com/tutoriel/java/serveur/multithread/
public class ServeurPrincipal {
private static ServerSocket ss;


public static void main (String [] args) {
	try {Integer port;
	if (args.length <=0) port = Integer.parseInt ("6666");
	else port = Integer.parseInt (args[0]);
	ss = new ServerSocket (port.intValue());
	while (true) // attente en boucle de connexion (bloquant sur ss.accept)
      {
        new Session (ss.accept()).start(); // un client se connecte, un nouveau thread client est lancé
      }
	
	
    }
	
	catch (Exception e) {
		if (ss != null & ! ss.isClosed())
			try {
			ss.close();}
		catch (IOException i) {
			i.printStackTrace();
		}
	}
		
	}
	
}