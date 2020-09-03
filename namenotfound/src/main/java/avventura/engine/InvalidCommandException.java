package engine;

import java.util.ResourceBundle;

public class InvalidCommandException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidCommandException() {
		super(ResourceBundle.getBundle("InvalidCommandException", Engine.locale).getString("standard"));
	}

	public InvalidCommandException(final String msg) {
		super(msg);
	}
}
