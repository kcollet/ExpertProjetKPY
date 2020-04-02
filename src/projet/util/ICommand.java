package projet.util;

@FunctionalInterface
public interface ICommand {
    public void execute(CommandParser parser);
}
