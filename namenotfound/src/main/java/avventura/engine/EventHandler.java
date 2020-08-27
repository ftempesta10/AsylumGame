package engine;

import java.io.Serializable;
import java.util.function.Consumer;

public interface EventHandler extends Consumer<GameDescription>, Serializable {

}
