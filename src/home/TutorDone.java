package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TutorDone extends Page {

    public EventHandler<ActionEvent> homeFunc;
    HBox buttonHolder;
    String tutorName;

    public TutorDone(Stage stage){
        createHeader("-fx-background-color: deepskyblue;", tutorName, Color.FLORALWHITE);
        homeFunc = createSceneChangeEvent(stage, Main.getHomePage());
        createCenterText();
        createHomeButton();
        scene = new Scene(pane, 600, 500);
    }

    public void setHeaderText(String name){
        tutorName = name;
        title.setText(name);
    }

    private void createCenterText(){
        Text text = new Text("Thank you, " + tutorName + ", for updating your schedule!");
        text.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 36.0));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(300.0);
        text.setFill(Color.DARKGRAY);
        pane.setCenter(text);
    }

    private void createHomeButton(){
        buttonHolder = new HBox();
        Button homeButton = createButton("Home", "-fx-background-color: indianred", homeFunc);
        buttonHolder.setPadding(new Insets(30));
        buttonHolder.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHolder.getChildren().add(homeButton);
        pane.setBottom(buttonHolder);
    }

}
