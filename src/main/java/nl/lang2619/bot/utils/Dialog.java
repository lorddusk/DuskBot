package nl.lang2619.bot.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by lang2 on 1/18/2016.
 */
public class Dialog extends Stage {

    public Dialog(Stage owner, String header, String message) {
        super();
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle(header);

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 300, 250, Color.LIGHTSKYBLUE);
        setScene(scene);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        root.getChildren().add(gridPane);
        AnchorPane.setBottomAnchor(gridPane, 10.0);
        AnchorPane.setTopAnchor(gridPane, 10.0);
        AnchorPane.setRightAnchor(gridPane, 10.0);
        AnchorPane.setLeftAnchor(gridPane, 10.0);

        TextArea taMessage = new TextArea();
        taMessage.setText(message);
        taMessage.setWrapText(true);
        taMessage.setEditable(false);
        GridPane.setRowSpan(taMessage, 4);
        gridPane.add(taMessage, 0, 0);

        Button btClose = new Button("Close");
        btClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });

        BorderPane buttonRegion = new BorderPane();
        buttonRegion.setRight(btClose);
        gridPane.add(buttonRegion, 0, 4);
    }

    public static void showDialog(Stage stage, String type, String message){
        Stage myDialog = new Dialog(stage,type,message);
        myDialog.show();
    }
}