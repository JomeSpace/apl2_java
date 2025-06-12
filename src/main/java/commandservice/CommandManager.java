package commandservice;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * --Disclaimer-- This code is written by my brother and not me credit to Gustav --Disclaimer--
 * ---------------------------------------------------------------------------------------------
 * he CommandManager dynamically loads and manages command classes from a specified package.
 * It listens for command-line input and executes matching commands with given arguments.
 */
public class CommandManager extends Thread {

    private static final String COMMANDS_PACKAGE = "commandservice.cmds";
    private static final String COMMANDS_FOLDER = "commandservice/cmds";

    public final List<Command> cmds;
    private static CommandManager instance;

    /**
     * Returns the singleton instance of CommandManager.
     */
    public static CommandManager getInstance() {
        return instance;
    }

    /**
     * Initializes and loads available commands from the commands directory.
     * Starts the thread to listen for input.
     */
    public CommandManager() {
        cmds = new ArrayList<>();
        loadCommands();
        instance = this;
        this.start();
    }

    /**
     * Dynamically loads all command classes from the command folder.
     */
    private void loadCommands() {
        try {
            Path commandPath = Paths.get(
                    CommandManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).resolve(COMMANDS_FOLDER);

            File commandFolder = commandPath.toFile();

            for (File file : Objects.requireNonNull(commandFolder.listFiles())) {
                if (!file.getName().endsWith(".class")) continue;

                String className = file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(COMMANDS_PACKAGE + "." + className);
                Constructor<?> constructor = clazz.getConstructor();
                Object commandInstance = constructor.newInstance();
                cmds.add((Command) commandInstance);
            }

        } catch (URISyntaxException | ClassNotFoundException |
                 NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.err.println("Error loading commands: " + e.getClass().getSimpleName());
        }
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String[] input = scanner.nextLine().trim().split(" ");
                if (input.length > 0) {
                    manageCommand(input);
                }
            }
        }
    }

    /**
     * Parses and executes a command with arguments from input.
     *
     * @param input the full user input split into command and arguments
     */
    public void manageCommand(String[] input) {
        String commandName = input[0];
        Command selectedCommand = cmds.stream()
                .filter(cmd -> cmd.name().equalsIgnoreCase(commandName))
                .findFirst()
                .orElse(null);

        if (selectedCommand == null) {
            System.out.println("Command not found!");
            return;
        }

        Map<String, String> args = new HashMap<>();
        StringBuilder currentArg = new StringBuilder();
        String currentKey = null;

        for (int i = 1; i < input.length; i++) {
            String token = input[i];
            if (token.startsWith("-")) {
                if (currentKey != null) {
                    args.put(currentKey, currentArg.toString().trim());
                }
                currentKey = token.substring(1);
                currentArg = new StringBuilder();
            } else {
                currentArg.append(token).append(" ");
            }
        }

        if (currentKey != null) {
            args.put(currentKey, currentArg.toString().trim());
        }

        selectedCommand.action(args);
    }
}
