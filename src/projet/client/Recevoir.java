package projet.client;

import java.io.IOException;

/*ce thread s'occupe d'écouter les réponses du serveur et de les afficher, 
cette duplication des thread côté clients permet de réagir à tout moment aux réponses du serveur */
public class Recevoir extends Thread {
public MainClient client;
public void run () {
	
		try {

            Object o = client.in.readObject();
            while (! client.exiting) { //exiting toujours à false dans la version actuelle de l'application 
            client.handleResponse(o);
            o = client.in.readObject();}
            

          

        } catch (IOException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
		
}
public Recevoir (MainClient mc) {
	client = mc;
}
}