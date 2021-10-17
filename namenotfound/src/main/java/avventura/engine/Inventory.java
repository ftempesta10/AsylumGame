package engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 4533980696666065652L;
	private List<Item> list = new ArrayList<Item>();

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }

    public void add(Item o) {
        list.add(o);
    }

    public void remove(Item o) {
        list.remove(o);
    }
}
