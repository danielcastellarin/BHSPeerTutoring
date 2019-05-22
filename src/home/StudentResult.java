package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.Flow;

public class StudentResult extends Page {

    EventHandler<ActionEvent> homeFunc;
    VBox centerBoxes;
    HBox studentInputBox;
    FlowPane timeInputs;
    Button tutorProfile;
    FlowPane matchedTutors;
    HBox buttonHolder;

    private String subject;
    private ArrayList<TimeSlot> timeSlots;


    public StudentResult(Stage stage, String subj, ArrayList<TimeSlot> slots){
        subject = subj;
        timeSlots = slots;

        createHeader("-fx-background-color: darkolivegreen", "Student Results", Color.FLORALWHITE);
        createCenter();
        homeFunc = createSceneChangeEvent(stage, Main.getHomePage());
        createHomeButton();
        createScene();
        stage.setScene(scene);
    }

    private void createCenter(){
        centerBoxes = new VBox();
        createStudentInput();
        createMatchedTutorsGrid();
        centerBoxes.setAlignment(Pos.CENTER);
        pane.setCenter(centerBoxes);
    }

    private void createStudentInput(){
        studentInputBox = new HBox();
        studentInputBox.setSpacing(20);

        VBox subjectBox = new VBox();
        Text subjectLabel = new Text("Subject:");
        Text subjectInput = new Text(subject);
        subjectBox.getChildren().addAll(subjectLabel, subjectInput);
        studentInputBox.getChildren().add(subjectBox);

        for(int i = 0; i < timeSlots.size(); i++){
            studentInputBox.getChildren().add(createTimeInput(i));
        }

        studentInputBox.setAlignment(Pos.CENTER);
        centerBoxes.getChildren().add(studentInputBox);
    }

    private FlowPane createTimeInput(int i){
        FlowPane timePane = new FlowPane();
        timePane.setOrientation(Orientation.VERTICAL);
        Text index = new Text("Time Slot " + (i + 1));
        Text day = new Text("Day: " + timeSlots.get(i).getDay());
        Text start = new Text("Start: " + timeSlots.get(i).getStartTIme());
        Text end = new Text("End: " + timeSlots.get(i).getEndTime());
        timePane.getChildren().addAll(index, day, start, end);
        return timePane;
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
