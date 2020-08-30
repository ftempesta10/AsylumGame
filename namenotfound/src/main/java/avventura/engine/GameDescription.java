package engine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import hashedGraph.WeightedHashedGraph;

public abstract class GameDescription {

    private Room currentRoom;

    private WeightedHashedGraph<Room, Gateway> map;
     
    private List<Item> inventory = new ArrayList<Item>();
    
    private Item commandTarget;
    
    private List<Command> commands = new ArrayList<Command>();

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<Item> getInventory() {
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

    public abstract void init() throws Exception;

    public abstract void nextMove(ParserOutput p, PrintStream out);

}
