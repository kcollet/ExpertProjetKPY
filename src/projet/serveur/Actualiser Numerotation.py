from lxml import etree
#ce fichier permet de tenir à jour le nombre total de mots dans le lexique en cas d'ajout ou suppression d'un mot

lexique = etree.parse("Lexique.xml")
num=0
nouvelle_racine = etree.Element("racine")
for entree in lexique.xpath ("lexicalEntry"):
    entree.set("num",str(num))
    num = num+1
lexique.getroot().set("nbMots",str(num+1))  
nouveau_lexique = open ("Lexique.xml","w")
nouveau_lexique.write (etree.tostring(lexique,pretty_print = True).decode("latin1"))
nouveau_lexique.close()
print("Fait")
    
