package ui.scenes;

import dto.collection.ParamDTO;
import services.jsonservice.JsonService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ui.SceneManager;

import static services.jsonservice.JsonService.exportJson;
import static services.jsonservice.JsonService.importJson;

public class ControlMenu {
    private VBox layout = new VBox(50);
    ParamDTO configs;
    JsonService configService = new JsonService("param.json");
    TextField SellerTextField;
    TextField BuyerTextField;

    public ControlMenu() {
        ParamDTO configs = importJson();
        if(configs == null) throw new IllegalArgumentException("Invalid configuration: ");
        SellerTextField = new TextField(configs.numSellers().toString());
        BuyerTextField = new TextField(configs.numBuyers().toString());
    }
    public void StartMenu() {

        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start Simulation");
        Text headline = new Text("Confiquration Parameters:");

        layout.getChildren().addAll(
                headline,
                new Label("Number of Sellers", SellerTextField),
                new Label("Number of Buyers", BuyerTextField),
                startButton
        );

        startButton.setOnAction(e -> {
            setConfig();
            SceneManager.showSimulation(configs);
        });
    }
    private void getSimConfig() {
        this.configs = importJson();
    }

    public void setConfig() {
        ParamDTO Configs = new ParamDTO(
                Integer.parseInt(SellerTextField.getText()),
                Integer.parseInt(BuyerTextField.getText())
        );
        this.configs = Configs;
        exportJson(Configs);
    }

    public Parent getView() {
        getSimConfig();
        StartMenu();
        return layout;
    }
}
