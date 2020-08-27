package engine;

import java.io.Serializable;
import java.util.function.Function;

public interface CommandHandler extends Function<CommandType, EventHandler>, Serializable {

}
