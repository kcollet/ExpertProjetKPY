package projet;
import javax.xml.parsers.*;

import javax.xml.xpath.*;

import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import java.util.Random;



public class Choisir {
	public static String choisir () {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
		String retour = "0";	
		DocumentBuilder builder = factory.newDocumentBuilder();
		File fichierLexique = new File ("Lexique.xml");
		Document xml=builder.parse(fichierLexique);
		Element racine = xml.getDocumentElement();
		XPathFactory xpf = XPathFactory.newInstance();
		XPath path = xpf.newXPath();
		int nombreDeMots = Integer.parseInt(path.evaluate("@nbMots",racine));
		Random generateur = new Random ();
		int rang = generateur.nextInt(nombreDeMots) + 1;
		System.out.println(rang);
		retour = path.evaluate("(lexicalEntry["+ rang +"]/@id)", racine);
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
