import file.services.jsonService;

public class main {
    // Main method to run the application
    public static void main(String[] args) {
        jsonService jsonService = new jsonService("param.json");
        // Initialize the command manager
        commandmanager.CommandManager cm = new commandmanager.CommandManager();
        cm.start();
        
    }
}
