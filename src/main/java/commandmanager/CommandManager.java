package commandmanager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;

public class CommandManager extends Thread {

    public List<Command> cmds;
    private static CommandManager managerInstance;
    public static CommandManager getInstance() { return managerInstance;}
    public CommandManager() {
        cmds = new ArrayList<>();
        try {
            File commandFolder = new File(new File(CommandManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()),  "\\commandmanager\\cmds\\");
            for (File f : commandFolder.listFiles()) {
                Class<?> clazz = Class.forName("commandmanager.cmds." + f.getName().replace(".class", ""));
                Constructor<?> constructor = clazz.getConstructor();
                Object obj = constructor.newInstance(new Object[] {});
                cmds.add((Command) obj);
            }
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Converting Class files to Classes failed! (0)");
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            System.out.println("Converting Class files to Classes failed! (1)");
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
            System.out.println("Converting Class files to Classes failed! (2)");
        } catch (InstantiationException ex) {
            ex.printStackTrace();
            System.out.println("Converting Class files to Classes failed! (3)");
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            System.out.println("Converting Class files to Classes failed! (4)");
        }
        // all of this is basically just so new commands are automatically recognized from the cmds folder and don't have to be added manually
        // should hopefully reduce unexpected bugs
        managerInstance = this;
        this.start();
    }

    @Override
    public void run() {
        Scanner inputStream = new Scanner(System.in);

        while (true) {
            String[] input = inputStream.nextLine().split(" ");
            manageCommand(input);
        }
    }

    void manageCommand(String[] input) {
        String cmdName = input[0];
        Command selCmd = null;
        for (Command cmd : cmds) if (cmd.name().equalsIgnoreCase(cmdName)) { selCmd = cmd; break;}
        if (selCmd == null) {
            System.out.println("Command not found!");
            return;
        }
        Map<String, String> args = new HashMap<>();

        String currentArg = "";
        String currentArgKey = "";
        for (int i = 1; i < input.length; i++) {
            String s = input[i];
            if (s.startsWith("-")) {
                args.put(currentArgKey, currentArg);
                currentArgKey = "";
                currentArg = "";

                currentArgKey = s.replace("-", "");

            } else currentArg = currentArg + " " + s;
        }
        if (currentArgKey != "") args.put(currentArgKey, currentArg);
        selCmd.action(args);
    }
}
