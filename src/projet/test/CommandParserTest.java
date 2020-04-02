package projet.test;

import projet.util.CommandParser;
import static projet.test.Assertion.*;

public class CommandParserTest {

    public static void main (String... args){

        CommandParser parser = new CommandParser();

        parser.parse("coucou 3 petits chats");
        AssertEqual(parser.getIntParameter(1), 3);
    }
}
