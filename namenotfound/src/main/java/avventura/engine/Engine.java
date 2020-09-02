package engine;

import java.util.Scanner;

public class Engine {
	
	private final GameDescription game;

	private final Parser parser;
	 
    public Engine(GameDescription game) {
        this.game = game;
        try {
            this.game.init();
            
        } catch (Exception ex) {
            System.err.println(ex);
        }
        parser = new Parser();
    }

    public void run() {
        System.out.println(game.getCurrentRoom().getName());
        System.out.println("================================================");
        System.out.println(game.getCurrentRoom().getDescription());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            ParserOutput p = parser.parse(command, game.getCommands(),game.getObjects(), game.getInventory(),
            		game.getEnemies());
            if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {
                System.out.println("Addio!");
                break;
            } else {
                game.nextMove(p, System.out);
                System.out.println("================================================");
            }
        }
    }
    
    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.run();
    }
    
}
