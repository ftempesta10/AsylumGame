package engine;

public class Gateway {
	
	private final int id;

	private boolean locked = false;
	
	private Gateway south = null;

	private Gateway north = null;

	private Gateway east = null;

	private Gateway west = null;
	
	public Gateway(int id) {
	        this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setLocked(boolean locked) {
	        this.locked = locked;
	}

	public boolean isLocked() {
    return locked;
	}
	
	public void unlock() {
		locked = false;
	}
	
	public void lock() {
		locked = true;
	}

	public Gateway getNorth() {
	return north;
	}

	public void setNorth(Gateway north) {
	this.north = north;
	}

	public Gateway getEast() {
	return east;
	}

	public void setEast(Gateway east) {
	this.east = east;
	}

	public Gateway getWest() {
	return west;
	}

	public void setWest(Gateway west) {
	this.west = west;
	}

	public Gateway getSouth() {
	return south;
	}

	public void setSouth(Gateway south) {
	this.south = south;
	}
}