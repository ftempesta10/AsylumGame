package engine;

import java.io.Serializable;
import java.util.function.Consumer;

public interface EventHandler extends Consumer<GameDescription>, Serializable {

	public static void pickUp(Item i, GameDescription g) throws InvalidCommandException {
		if(g.getCurrentRoom().getObjects().contains(i)) {
			g.getInventory().add(i);
			g.getCurrentRoom().getObjects().remove(i);
		} else throw new InvalidCommandException();
	}


	public static void drop(Item i, GameDescription g) {
		g.getInventory().remove(i);
		g.getCurrentRoom().getObjects().add(i);
	}
}
