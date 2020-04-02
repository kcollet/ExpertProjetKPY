package projet.util;

import java.util.HashMap;
import java.util.Map;

public class CommandManager  {
    private Map<String, ICommand> commands;
    private Map<String, String> descriptions;

    //Ajoute une nouvelle commande à la table des commandes : commands.
    public void setCommand(String keyword, ICommand command){
        commands.put(keyword, command);
    }
    public void setDescription(String command, String description){
        descriptions.put(command, description);
    }

    public CommandManager(){
        commands = new HashMap<>();
        descriptions = new HashMap<>();
    }

    public boolean executeCommand(CommandParser parser){

        ICommand command = commands.get(parser.getStringParameter(0));
        if (command != null){
            try {
                command.execute(parser);
                return true;
            } catch (Throwable e){
                System.err.println("Paramètre de commandes invalides.");
                displayCommands();
                return false;
            }

        }
        System.err.println("Commande introuvable ! Voici la liste des commandes, à utiliser avec un / : ");
        displayCommands();
        return false;
    }

    public boolean isCommand (String keyword){
        return commands.containsKey(keyword);
    }

    public void displayCommands(){
        for (Map.Entry<String, String> entry : descriptions.entrySet()){
            System.err.println(String.format("%30s : %s", entry.getKey(), entry.getValue()));
        }
    }
}
