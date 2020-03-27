package serveur;
import javax.xml.parsers.*;

import javax.xml.xpath.*;

import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import java.util.Random;



public class Choisir {
	public static String choisir () {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//objet qui va permettre de build le parser d'xml
		
		try {
		String retour = "0";	
		DocumentBuilder builder = factory.newDocumentBuilder();//objet qui va servir
		File fichierLexique = new File ("Lexique.xml");// fichier xml simple un mot/noeud
		Document xml=builder.parse(fichierLexique);// converti le fichier en arbre exploitable par java
		Element racine = xml.getDocumentElement();//récupère la racine de l'arbre
		XPathFactory xpf = XPathFactory.newInstance();//création de l'objet qui permettra de créer l'instance chargée des requêtes xpath
		XPath path = xpf.newXPath();//la classe XPath permet d'utiliser le langage Xpath à proprement parler
		int nombreDeMots = Integer.parseInt(path.evaluate("@nbMots",racine));//donne le total de mot présents dans le lexique
		Random generateur = new Random ();
		int rang = generateur.nextInt(nombreDeMots) + 1;
		System.out.println(rang);
		retour = path.evaluate("(lexicalEntry["+ rang +"]/@id)", racine); // donne le rang-ième mot du lexique
		return retour;
		
		
		
		
		
		}catch (ParserConfigurationException e) {
	         e.printStackTrace();
	         return null;
	      } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

}
