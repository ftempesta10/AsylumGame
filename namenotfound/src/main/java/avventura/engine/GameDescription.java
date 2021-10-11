package engine;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hashedGraph.WeightedHashedGraph;

public abstract class GameDescription implements Serializable {
    private Room currentRoom;
    private WeightedHashedGraph<Room, Gateway> map;
    private Inventory inventory;
    private Item commandTarget;
    private Enemy currentEnemy;

    private List<Command> commands = new ArrayList<Command>();

	public Room getCurrentRoom() {
        return currentRoom;
    }

    public Enemy getCurrentEnemy() {
		return currentEnemy;
	}

	public void setCurrentEnemy(Enemy currentEnemy) {
		this.currentEnemy = currentEnemy;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Command> getCommands(){
        return commands;
    }

    public void setCommands(List<Command> commands){
        this.commands = commands;
    }

    public Item getCommandTarget(){
        return commandTarget;
    }

    public void setCommandTarget(Item target){
        commandTarget = target;
    }


    public WeightedHashedGraph<Room, Gateway> getMap() {
		return map;
	}

	public void setMap(WeightedHashedGraph<Room, Gateway> map) {
		this.map = map;
	}

	public abstract void init() throws Exception;

    public abstract void nextMove(ParserOutput p, PrintStream out);

}
