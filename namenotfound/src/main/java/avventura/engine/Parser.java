package engine;

import java.util.List;
import java.util.ArrayList;

interface Parser {
	List<String> articles = new ArrayList<String>();
	List<String> prepositions = new ArrayList<String>();
	
	boolean checkInDictionary(String token, List<String> dictionary) throws Exception;
	int checkForCommand(String token, List<Command> commands);
    int checkForObject(String token, List<Item> obejcts);
    
    public ParserOutput parse(String command,List<Command> commands, List<Item> objects, Inventory inv,
								List<AdventureCharacter> enemies) throws Exception;
}
