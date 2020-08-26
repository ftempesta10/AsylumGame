package hashedGraph;

/**
 * This class is an implementation of undirected graph (without labeled arcs) using the hashing to index the set of nodes
 * @author AlessandroPaparella
 *
 * @see HashedGraph
 */
public class UndirectedHashedGraph<T> extends HashedGraph<T> {

	public UndirectedHashedGraph() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	/**
	 * Add an undirected arc, "start" to "end" = "end" to "start"
	 */
	public void insArc(T start, T end) {
		super.insArc(start, end);
		super.insArc(end, start);
	}

	@Override
	public void removeArc(T start, T end) {
		super.removeArc(start, end);
		super.removeArc(end, start);
	}

}
