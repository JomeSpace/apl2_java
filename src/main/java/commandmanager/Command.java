package commandmanager;

import java.util.Map;

public interface Command {
    String name();
    void action(Map<String, String> args);
    String explanation();
}
