package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import engine.launcher.Launcher;

public class Engine {

	private final GameDescription game;
	static Locale locale = Locale.getDefault();
	private final Parser parser;

    public Engine(GameDescription game) {
    	locale = Launcher.locale;
        this.game = game;
        try {
            this.game.init();

        } catch (Exception ex) {
            System.err.println(ex);
        }
        parser = null;
        /*
        if() parser = new Parser(this.loadDictionary("./resources/articoli.txt"),
        							this.loadDictionary("./resources/preposizioni.txt"));
        else parser = new Parser(this.loadDictionary("./resources/articles.txt"),
        							this.loadDictionary("./resources/preposition.txt"));*/
    }

    public void run() throws Exception {
        System.out.println(game.getCurrentRoom().getName());
        System.out.println("================================================");
        System.out.println(game.getCurrentRoom().getDescription());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getObjects(),
            		game.getInventory(), game.getCurrentRoom().getEnemies());
            if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {
            	//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            	if(locale == Locale.ITALIAN) System.out.println("Addio");
                else System.out.println("Goodbye");
            	scanner.close();
                break;
            } else {
                game.nextMove(p, System.out);
                System.out.println("================================================");
            }
        }
    }

    private List<String> loadDictionary(String filename) throws FileNotFoundException{
    	List<String> dictionary = new ArrayList<String>();
		// TODO Auto-generated method stub
		Scanner s = null;
		String in;
		boolean find = false;
    	try {
    		s = new Scanner(new BufferedReader(new FileReader(filename)));
    		while(s.hasNext() && find == false) {
    		in = s.nextLine();
    		dictionary.add(in);
    		}
    	} finally {
    			s.close();
    	}
    	return dictionary;
    }
}
