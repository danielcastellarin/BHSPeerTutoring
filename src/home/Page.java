package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class Page {

    private Scene scene;
    BorderPane pane = new BorderPane();
    HBox header;

    public void createHeader(String headerColor, String headerText, Color textColor){
        header = new HBox();
        header.setPadding(new Insets(30));
        header.setStyle(headerColor);
        Text title = new Text(headerText);
        title.setFill(textColor);
        title.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 36.0));
        header.getChildren().add(title);
        header.setAlignment(Pos.CENTER);
        pane.setTop(header);
    }

    public EventHandler<ActionEvent> createSceneChangeEvent (Stage stage, Scene nextScene){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.switchPages(stage, nextScene);
            }
        };
        return event;
    }

    public Scene getScene(){
        return scene;
    }

}
