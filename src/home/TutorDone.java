package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TutorDone extends Page {

    public EventHandler<ActionEvent> homeFunc;
    HBox buttonHolder;
    String tutorName;
    Text centerText;

    private ArrayList<TimeSlot> timeSlots;

    public TutorDone(Stage stage, String name, ArrayList<TimeSlot> slots){
        tutorName = name;
        timeSlots = slots;

        createHeader("-fx-background-color: deepskyblue;", tutorName, Color.FLORALWHITE);
        homeFunc = createSceneChangeEvent(stage, Main.homePage.getScene());
        createCenterText();
        createHomeButton();
        createScene();
        stage.setScene(scene);
    }

    private void createCenterText(){
        VBox center = new VBox();
        center.setSpacing(30);
        center.setAlignment(Pos.CENTER);

        centerText = new Text("Thank you for updating your schedule!");
        centerText.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 36.0));
        centerText.setTextAlignment(TextAlignment.CENTER);
        centerText.setWrappingWidth(300.0);
        centerText.setFill(Color.DARKGRAY);

        HBox changedTimes = new HBox();
        changedTimes.setSpacing(20);
        for(int i = 0; i < timeSlots.size(); i++){
            changedTimes.getChildren().add(createTimeInput(i));
        }

        center.getChildren().addAll(centerText, changedTimes);
        pane.setCenter(center);
    }

    private FlowPane createTimeInput(int i){
        FlowPane timePane = new FlowPane();
        timePane.setOrientation(Orientation.VERTICAL);
        Text index = new Text("Time Slot " + (i + 1));
        Text day = new Text("Day: " + timeSlots.get(i).getDay());
        Text range = new Text(numToTimeConvert(timeSlots.get(i).getStartTIme()) +
                " - " + numToTimeConvert(timeSlots.get(i).getEndTime()));
        timePane.getChildren().addAll(index, day, range);
        return timePane;
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
