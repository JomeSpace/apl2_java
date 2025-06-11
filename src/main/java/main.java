import services.jsonservice.JsonService;

public class main {
    // Main method to run the application
    public static void main(String[] args) {
        JsonService jsonService = new JsonService("param.json");
        // Initialize the command manager
        commandmanager.CommandManager cm = new commandmanager.CommandManager();
        cm.start();
        
    }
}
