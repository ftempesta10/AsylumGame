package engine;

public class InvalidCommandException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public InvalidCommandException() {
		super("Commando non valido");
	}
	
	public InvalidCommandException(final String msg) {
		super(msg);
	}
}
