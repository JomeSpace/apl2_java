package ui.elements;

import commandmanager.CommandManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import java.util.function.Consumer;

public class CommandBar {
    Runnable pauseThread;

    CommandManager commandManager = new CommandManager();

    public CommandBar(Runnable pauseThread) {
        this.pauseThread = pauseThread;
    }
    /**
     * Creates a command bar with a TextField and a Submit Button next to it.
     * @param placeholder The placeholder text for the input field.
     * @return A Node (HBox) containing the input and button, ready to be added to the UI.
     */
    public Node createCommandBar(String placeholder) {
        TextField inputField = new TextField();
        inputField.setPromptText(placeholder);
        inputField.setPrefWidth(300);

        Label label = new Label("CommandLine:");
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String text = inputField.getText();
            if (!text.isEmpty()) {
                this.pauseThread.run();
                this.commandManager.manageCommand(inputField.getText().split(" "));
                inputField.clear();
            }
        });

        HBox commandBox = new HBox(10, inputField, submitButton);
        commandBox.setPadding(new Insets(10));
        commandBox.setAlignment(Pos.CENTER);
        return commandBox;
    }
}

