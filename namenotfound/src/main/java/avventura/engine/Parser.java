package avventura.engine;

import java.util.List;

import avventura.type.AdventureObject;
import avventura.type.Command;


public class Parser {

	private int checkForCommand(String token, List<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).getName().equals(token) || commands.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }

    private int checkForObject(String token, List<AdventureObject> obejct) {
        for (int i = 0; i < obejct.size(); i++) {
            if (obejct.get(i).getName().equals(token) || obejct.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }
    
    public ParserOutput parse(String command, List<Command> commands, List<AdventureObject> object, List<AdventureObject> inventory) {
        String cmd = command.toLowerCase().trim();
        String[] tokens = cmd.split("\\s+");
        if (tokens.length > 0) {
            int ic = checkForCommand(tokens[0], commands);
            if (ic > -1) {
                if (tokens.length > 1) {
                    int io = checkForObject(tokens[1], object);
                    int ioinv = -1;
                    if (io < 0 && tokens.length > 2) {
                        io = checkForObject(tokens[2], object);
                    }
                    if (io < 0) {
                        ioinv = checkForObject(tokens[1], inventory);
                        if (ioinv < 0 && tokens.length > 2) {
                            ioinv = checkForObject(tokens[2], inventory);
                        }
                    }
                    if (io > -1 && ioinv > -1) {
                        return new ParserOutput(commands.get(ic), object.get(io), inventory.get(ioinv));
                    } else if (io > -1) {
                        return new ParserOutput(commands.get(ic), object.get(io), null);
                    } else if (ioinv > -1) {
                        return new ParserOutput(commands.get(ic), null, inventory.get(ioinv));
                    } else {
                        return new ParserOutput(commands.get(ic), null, null);
                    }
                } else {
                    return new ParserOutput(commands.get(ic), null);
                }
            } else {
                return new ParserOutput(null, null);
            }
        } else {
            return null;
        }
    }
}
