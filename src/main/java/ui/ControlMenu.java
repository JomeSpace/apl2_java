package ui;

import dto.collection.ParamDTO;
import file.services.jsonService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControlMenu {
    private VBox layout = new VBox(50);
    ParamDTO configs;
    jsonService configService = new jsonService("param.json");
    TextField SellerTextField;
    TextField BuyerTextField;

    public ControlMenu() {
        ParamDTO configs = configService.importJson();
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
            SceneManager.showSimulation();
        });
    }
    private void getSimConfig() {
        ParamDTO paramDTO = configService.importJson();
        configs = paramDTO;
    }

    public void setConfig() {
        ParamDTO Configs = new ParamDTO(
                Integer.parseInt(SellerTextField.getText()),
                Integer.parseInt(BuyerTextField.getText())
        );
        configService.exportJson(Configs);
    }

    public Parent getView() {
        getSimConfig();
        StartMenu();
        return layout;
    }
}
