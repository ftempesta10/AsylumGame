package hashedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is an implementation of directed graph (without labeled arcs) using the hashing to index the set of nodes
 * @author AlessandroPaparella
 *
 * @param <T> this is the type of object that will represent the nodes (there mustn't be duplicated values) i.e. Character, Integer... and must implement an hashing method.
 */
public class HashedGraph<T> {
	private Set<T> nodes= new HashSet<T>();
	private Map<T, Set<T>> arcs = new HashMap<T, Set<T>>();

	/**
	 * check if the graph is empty
	 * @return true if empty, false otherwise
	 */
	public Boolean isEmpty() {
		Boolean empty=true;
		if(nodes.isEmpty()==false) {
			empty=false;
		}
		return empty;
	}

	/**
	 * Add a node to the graph, if already exists, it will be ignored
	 * @param node the node to add in to the graph
	 */
	public void insNode(T node) {
		if(nodes.add(node)==true)
			arcs.put(node, new HashSet<T>());
	}

	/**
	 * Check if a node exists in the graph
	 * @param node the node to check
	 * @return true if the node exists in the graph
	 */
	public Boolean contains(T node) {
		return nodes.contains(node);
	}

	/**
	 * Remove the node and all the arcs related to it
	 * @param node
	 */
	public void removeNode(T node) {
		nodes.remove(node);
		arcs.remove(node);
		for(T k: arcs.keySet()) {
			arcs.get(k).remove(node);
		}
	}

	/**
	 *
	 * @param node
	 * @return the set of adjacents node to the node passed as parameter
	 * @throws hashedGraphException if the graph does not contain the node
	 */
	public Set<T> getAdjacents(T node) throws hashedGraphException {
		if(!this.contains(node)) {
			throw new hashedGraphException("Node doesn't exist!");
		}
		return arcs.get(node);
	}

	/**
	 *
	 * @param start the node from which the arc starts
	 * @param end the node to which the arc is directed
	 * @return true if an arc from "start" to "end" exists
	 * @throws hashedGraphException if node start and/or end does not exist
	 */
	public Boolean containsArc(T start, T end) throws hashedGraphException {
		if(!this.contains(start) || !this.contains(end)) {
			throw new hashedGraphException("Node doesn't exist!");
		}
		Boolean exists = false;
		Set<T> adjacents=arcs.get(start);
		if(adjacents.contains(end))
			exists = true;
		return exists;
	}

	/**
	 * Add a new arc to the graph, if already exists it will be ignored
	 * @param start the node from which the arc have to start
	 * @param end the node to which the arc has to be directed
	 */
	public void insArc(T start, T end) {
		if(!this.contains(start)) {
			this.insNode(start);
		}
		if(!this.contains(end)) {
			this.insNode(end);
		}
		Set<T> adjacents = arcs.get(start);
		adjacents.add(end);
	}

	/**
	 * If exists an arc from "start" to "end" it will be removed from the graph
	 * @param start
	 * @param end
	 */
	public void removeArc(T start, T end) {
		Set<T> adjacents=arcs.get(start);
		adjacents.remove(end);
	}
}
