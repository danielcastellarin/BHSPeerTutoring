package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StudentResult extends Page {

    EventHandler<ActionEvent> homeFunc;
    VBox centerBoxes;
    HBox studentInputBox;
    HBox timeInputs;
    Button tutorProfile;
    FlowPane matchedTutors;
    HBox buttonHolder;

    public StudentResult(Stage stage){
        createHeader("-fx-background-color: darkolivegreen", "Student Results", Color.FLORALWHITE);
        createCenter();
        homeFunc = createSceneChangeEvent(stage, Main.getHomePage());
        createHomeButton();
        createScene();
    }
    private void createCenter(){
        centerBoxes = new VBox();
        createStudentInput();
        createTimeInputs();
        createMatchedTutorsGrid();
    }

    private void createStudentInput(){
        studentInputBox = new HBox();
        //Pass in student input from last page
        centerBoxes.getChildren().add(studentInputBox);
    }

    private void createTimeInputs(){
        timeInputs = new HBox();
        //Pass in time input from last page
        centerBoxes.getChildren().add(timeInputs);
    }

    private void createMatchedTutorsGrid(){
        matchedTutors = new FlowPane();
        //Pass in tutors from database
        centerBoxes.getChildren().add(matchedTutors);
    }

    private void createHomeButton(){
        buttonHolder = new HBox();
        Button homeButton = createButton("Home", "-fx-background-color: indianred", homeFunc);
        buttonHolder.setPadding(new Insets(30));
        buttonHolder.setAlignment(Pos.BOTTOM_LEFT);
        buttonHolder.getChildren().add(homeButton);
        pane.setBottom(buttonHolder);
    }
}
