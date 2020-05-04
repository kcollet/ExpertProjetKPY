package projet.client;

import java.io.IOException;


public class Recevoir extends Thread {
public MainClient client;
public void run () {
	
		try {

            Object o = client.in.readObject();
            while (! client.exiting) {
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