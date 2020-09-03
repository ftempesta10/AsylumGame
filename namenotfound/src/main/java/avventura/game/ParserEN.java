package game;

import java.util.List;

import engine.AdventureCharacter;
import engine.InvalidCommandException;
import engine.Inventory;
import engine.Item;
import engine.Parser;
import engine.ParserOutput;

public class ParserEN implements Parser{

	public ParserEN() {
		this.loadArticles();
		this.loadPrepositions();
	}
	
	
	public boolean checkInDictionary(String token, List<String> dictionary) throws Exception {
		// TODO Auto-generated method stub
		if(dictionary.contains(token)) return true;
		else return false;
	} 
	
	public int checkForCommand(String token, List<Command> commands) {
	        for (int i = 0; i < commands.size(); i++) {
	            if (commands.get(i).getName().equals(token) || commands.get(i).getAlias().contains(token)) {
	                return i;
	            }
	        }
	        return -1;
	}
	public int checkForObject(String token, List<Item> obejcts) {
        for (int i = 0; i < obejcts.size(); i++) {
            if (obejcts.get(i).getName().equals(token) || obejcts.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }

	public ParserOutput parse(String command, List<Command> commands, List<Item> objects, Inventory inv,
			List<AdventureCharacter> enemies) throws Exception {
		// TODO Auto-generated method stub
		String cmd = command.toLowerCase().trim();
        String[] tokens = cmd.split("\\s+");
        switch(tokens.length) {
        case 1 :
        	//Verb
        	int ic = checkForCommand(tokens[0], commands);
        	if(ic > -1) return new ParserOutput(commands.get(ic)); 
        	else throw new InvalidCommandException();
        	
        case 2 : 
        	//Verb object or verb enemy
        	int com2 = checkForCommand(tokens[0], commands);
        	if(com2 > -1) {
            	int obj2 = checkForObject(tokens[1], objects);
            	if(obj2 > -1 && inv.getList().contains(objects.get(obj2))) {
            		return new ParserOutput(commands.get(com2), objects.get(obj2));
            	} else {
            		for(AdventureCharacter a : enemies) {
            			if(a.getName().equals(tokens[1])) {
            				return new ParserOutput(commands.get(com2), a);
            			}
            		}
            		throw new InvalidCommandException();
            	} 
        	} else throw new InvalidCommandException();
        case 3 : 
        	//verb article object o verb article enemy
        	int com3 = checkForCommand(tokens[0], commands);
        	if(com3 > -1) {
        		if(articles.contains(tokens[1])) {
        			int obj3 = checkForObject(tokens[2], objects);
                	if(obj3 > -1 && inv.getList().contains(objects.get(obj3))) {
                		return new ParserOutput(commands.get(com3), objects.get(obj3));
                	} else {
                		for(AdventureCharacter a : enemies) {
                			if(a.getName().equals(tokens[2])) {
                				return new ParserOutput(commands.get(com3), a);
                			}
                		}
                		throw new InvalidCommandException();
                	} 
        		} else throw new InvalidCommandException();
        	} else throw new InvalidCommandException();
        	
        case 4 :
        	//verb object preposition object
        	int com4 = checkForCommand(tokens[0], commands);
        	if(com4 > -1) {
            	int obj4 = checkForObject(tokens[1], objects);
            	if(obj4 > -1 && inv.getList().contains(objects.get(obj4))) {
            		if(prepositions.contains(tokens[2])) {
            			int obje4 = checkForObject(tokens[3], objects);
                    	if(obje4 > -1 && inv.getList().contains(objects.get(obje4))) {
                    		return new ParserOutput(commands.get(com4), objects.get(obj4), objects.get(obje4));
                    	} else throw new InvalidCommandException();
            		} else throw new InvalidCommandException();	
            	} else throw new InvalidCommandException(); 
        	} else throw new InvalidCommandException();
        	
        case 5 : 
        	//verb object compound preposition object
        	//verb article object preposition object
        	int com5 = checkForCommand(tokens[0], commands);
        	if(com5 > -1) {
        		if(articles.contains(tokens[1])) {
        			int obj5 = checkForObject(tokens[2], objects);
                	if(obj5 > -1 && inv.getList().contains(objects.get(obj5))) {
                		if(prepositions.contains(tokens[3])) {
                			int obje5 = checkForObject(tokens[4], objects);
                        	if(obje5 > -1 && inv.getList().contains(objects.get(obje5))) {
                        		return new ParserOutput(commands.get(com5), objects.get(obj5), objects.get(obje5));
                        	} else throw new InvalidCommandException();
                		} else throw new InvalidCommandException();
                	} else throw new InvalidCommandException();
        		} else {
        			int obj5 = checkForObject(tokens[1], objects);
                	if(obj5 > -1 && inv.getList().contains(objects.get(obj5))) {
                		if(prepositions.contains(tokens[2]) && articles.contains(tokens[3])) {
                			int obje5 = checkForObject(tokens[4], objects);
                        	if(obje5 > -1 && inv.getList().contains(objects.get(obje5))) {
                        		return new ParserOutput(commands.get(com5), objects.get(obj5), objects.get(obje5));
                        	} else throw new InvalidCommandException();
                		} else throw new InvalidCommandException();	
                	} else throw new InvalidCommandException();
                }
        	} else throw new InvalidCommandException();
        	
        case 6:
        	//verb article object compound preposition object
        	int com6 = checkForCommand(tokens[0], commands);
        	if(com6 > -1) {
        		if(articles.contains(tokens[1])) {
        			int obj6 = checkForObject(tokens[2], objects);
                	if(obj6 > -1 && inv.getList().contains(objects.get(obj6))) {
                		if(prepositions.contains(tokens[3]) && articles.contains(tokens[4])) {
                			int obje6 = checkForObject(tokens[5], objects);
                        	if(obje6 > -1 && inv.getList().contains(objects.get(obje6))) {
                        		return new ParserOutput(commands.get(com6), objects.get(obj6), objects.get(obje6));
                        	} else throw new InvalidCommandException();
                		} else throw new InvalidCommandException();
                	} else throw new InvalidCommandException();
        		} else throw new InvalidCommandException();
        	} else throw new InvalidCommandException();
        	
        	
        	
        	
        default:
        	throw new InvalidCommandException();
        }
	}


	public void loadArticles() {
		// TODO Auto-generated method stub
		articles.add("the");	
	}


	public void loadPrepositions() {
		// TODO Auto-generated method stub
		prepositions.add("with");
		prepositions.add("about");
		prepositions.add("for");
		prepositions.add("to");
		prepositions.add("of");
		prepositions.add("from");
		//preposition compose
		prepositions.add("with the");
		prepositions.add("about the");
		prepositions.add("for the");
		prepositions.add("to the");
		prepositions.add("of the");
		prepositions.add("from the");		
	}
}
