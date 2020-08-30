package engine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import hashedGraph.WeightedHashedGraph;

public abstract class GameDescription<T, W> {

    private Room currentRoom;

    private WeightedHashedGraph<T, W> map;
     
    private final List<Item> inventory = new ArrayList<Item>();

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public abstract void init() throws Exception;

    public abstract void nextMove(ParserOutput p, PrintStream out);

}