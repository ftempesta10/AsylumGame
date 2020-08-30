package game;

import java.io.PrintStream;

import engine.GameDescription;
import engine.Gateway;
import engine.ParserOutput;
import engine.Room;
import hashedGraph.WeightedHashedGraph;

public class Asylum extends GameDescription {
	Integer health, maxMoves;
	Boolean gasVuln, breathedGas;

	public Asylum() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws Exception {
		WeightedHashedGraph<Room, Gateway> map = new WeightedHashedGraph();

	}

	@Override
	public void nextMove(ParserOutput p, PrintStream out) {
		// TODO Auto-generated method stub

	}

}
