package userinterface.scenes;

import dtos.ParamDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.jsonservice.JsonService;

import static simulation.ParameterRanges.checkParameterRanges;
import static userinterface.SceneManager.showSimulation;

/**
 * Represents the control menu scene for configuring simulation parameters.
 * Allows users to input the number of sellers and buyers, validates input,
 * and starts the simulation with the configured parameters.
 */
public class ControlMenu {

    private final VBox layout = new VBox(50);
    private final JsonService jsonService = new JsonService("src/main/resources/param.json");

    private ParamDTO configs;
    private TextField sellerTextField;
    private TextField buyerTextField;

    /**
     * Constructs the ControlMenu, loads configuration, and initializes the UI.
     */
    public ControlMenu() {
        getSimConfig();
        startMenu();
    }

    /**
     * Initializes the menu layout with input fields and start button.
     */
    public void startMenu() {
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Text headline = new Text("Configure Simulation Parameters:");

        Label sellerTextFieldLabel = new Label("Number of Sellers:");
        sellerTextField = new TextField(configs.numSellers().toString());
        HBox paramInput1 = new HBox(sellerTextFieldLabel, sellerTextField);
        paramInput1.setAlignment(Pos.CENTER);
        paramInput1.setSpacing(10);

        Label buyerTextFieldLabel = new Label("Number of Buyers:");
        buyerTextField = new TextField(configs.numBuyers().toString());
        HBox paramInput2 = new HBox(buyerTextFieldLabel, buyerTextField);
        paramInput2.setAlignment(Pos.CENTER);
        paramInput2.setSpacing(10);

        Button startButton = new Button("Start Simulation");
        startButton.setOnAction(e -> {
            setConfig();
            showSimulation(configs);
        });

        layout.getChildren().addAll(headline, paramInput1, paramInput2, startButton);
    }

    /**
     * Returns the root node of this scene.
     *
     * @return the VBox layout containing the menu controls
     */
    public Parent getView() {
        return layout;
    }

    /**
     * Loads the simulation configuration from a JSON file.
     */
    private void getSimConfig() {
        this.configs = jsonService.importJson();
    }

    /**
     * Reads user input from text fields, validates the input,
     * updates the configuration, and saves it back to the JSON file.
     * Handles invalid input and out-of-range values with console messages.
     */
    private void setConfig() {
        try {
            int numSellers = Integer.parseInt(sellerTextField.getText());
            int numBuyers = Integer.parseInt(buyerTextField.getText());

            ParamDTO newConfigs = new ParamDTO(numSellers, numBuyers);

            // Throws IllegalArgumentException if parameters are invalid
            checkParameterRanges(newConfigs);

            this.configs = newConfigs;
            jsonService.exportJson(configs);
            System.out.println("Parameters saved successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid integers for sellers and buyers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameter: " + e.getMessage());
        }
    }
}
