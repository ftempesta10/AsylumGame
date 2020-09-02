package engine;

import java.util.Locale;
import java.util.Scanner;

public class Engine {
	
	private final GameDescription game;
	private Locale locale = Locale.getDefault();
	private final Parser parser;
	 
    public Engine(GameDescription game, Locale locale) {
        this.game = game;
        this.setLocale(locale);
        try {
            this.game.init();
            
        } catch (Exception ex) {
            System.err.println(ex);
        }
        if(locale == Locale.ITALIAN) parser = new Parser();
        else parser = new Parser();
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
            	if(locale == Locale.ITALIAN) System.out.println("Fine del gioco");
                else System.out.println("End of the game");
                break;
            } else {
                game.nextMove(p, System.out);
                System.out.println("================================================");
            }
        }
    }

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
 
}
