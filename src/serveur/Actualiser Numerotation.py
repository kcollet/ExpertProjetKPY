from lxml import etree
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
    
