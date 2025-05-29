package commandmanager.cmds;

import commandmanager.Command;
import commandmanager.CommandManager;

import java.util.Map;

public class HelpCommand implements Command {
    @Override
    public String name() {
        return "help";
    }

    @Override
    public void action(Map<String, String> args) {
        System.out.println("Command List:");
        for (Command cmd : CommandManager.getInstance().cmds) {
            String spaces = "";
            for (int i = 0; i < 15 - cmd.name().length(); i++) spaces = spaces + " "; // to make equal spacing among the commands
            System.out.println(cmd.name() + spaces + cmd.explanation());
        }
        System.out.println("");
    }

    @Override
    public String explanation() {
        return "Command List";
    }
}
