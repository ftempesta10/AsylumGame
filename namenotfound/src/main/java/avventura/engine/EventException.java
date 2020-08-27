package engine;

public class EventException extends Exception {
	private EventHandler handler;

	public EventException(EventHandler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
	}

	public EventException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EventException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public EventException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public EventHandler getHandler() {
		return handler;
	}
}
