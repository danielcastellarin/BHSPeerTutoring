package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class StudentPage extends Page{

    VBox center;
    ComboBox subjectChooser;
    FlowPane timeInputs;
    EventHandler<ActionEvent> backFunc;
    EventHandler<ActionEvent> advFunc;
    EventHandler<ActionEvent> addTimeFunc;
    EventHandler<ActionEvent> editSlotFunc;
    EventHandler<ActionEvent> delSlotFunc;

    ArrayList<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
    ArrayList<String> subjectNames = new ArrayList<>();

    public static StudentResult studentResult;
    public static TimePopUp timePopUp;

    public StudentPage(Stage stage){
        createHeader("-fx-background-color: darkolivegreen;", "Students", Color.FLORALWHITE);
        createButtonEvents(stage);
        createInputGrid();                // Name, Subject, Time
        createNavButtonBox("-fx-background-color: darkolivegreen", backFunc, advFunc);
        createScene();
        stage.setScene(scene);
    }

    private void createInputGrid(){
        center = new VBox();
        center.setAlignment(Pos.TOP_CENTER);
        center.setSpacing(40);
        center.setPadding(new Insets(50));

        HBox subjectBox = new HBox();

        Text subjectChooserLabel = new Text("Subject:");
        subjectChooserLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 36));
        subjectChooserLabel.setFill(Color.DARKOLIVEGREEN);

        JavaToMySQL retrieveSubjects = new JavaToMySQL("SELECT DISTINCT subject FROM subjects;");
        retrieveSubjects.doQuery();
        subjectNames = retrieveSubjects.readSubjects();
        subjectChooser = new ComboBox();
        subjectChooser.getItems().addAll(subjectNames);

        subjectBox.setAlignment(Pos.CENTER);
        subjectBox.setSpacing(100);
        subjectBox.getChildren().addAll(subjectChooserLabel, subjectChooser);


        HBox timeBox = new HBox();

        Text timeLabel = new Text("Time:");
        timeLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 36));
        timeLabel.setFill(Color.DARKOLIVEGREEN);

        Button addTimeBtn = new Button("Add");
        addTimeBtn.setOnAction(addTimeFunc);

        timeBox.setAlignment(Pos.CENTER);
        timeBox.setSpacing(100);
        timeBox.getChildren().addAll(timeLabel, addTimeBtn);

        timeInputs = new FlowPane();
        timeInputs.setHgap(10);
        timeInputs.setVgap(10);

        center.getChildren().addAll(subjectBox, timeBox, timeInputs);
        pane.setCenter(center);
    }

    public void addTimeInput(int index){
        FlowPane singleTimeInput = new FlowPane();
        singleTimeInput.setOrientation(Orientation.VERTICAL);
        singleTimeInput.setPrefWrapLength(150);
        singleTimeInput.setColumnHalignment(HPos.CENTER);
        Text indexLabel = new Text("Time Slot " + (index + 1));
        indexLabel.setFont(Font.font("System", FontWeight.NORMAL, 20));
        Text dayLabel = new Text("Day: " + timeSlots.get(index).getDay());
        dayLabel.setFont(Font.font("System", FontWeight.NORMAL, 20));
        Text rangeLabel = new Text(numToTimeConvert(timeSlots.get(index).getStartTime()) +
                " - " + numToTimeConvert(timeSlots.get(index).getEndTime()));
        rangeLabel.setFont(Font.font("System", FontWeight.NORMAL, 20));
        HBox btnHolder = new HBox();
        Button editSlot = createButton("Edit", "-fx-background-color: darkolivegreen", editSlotFunc,
                60, 26, 18);
        editSlot.setId("edit id" + index);
        Button deleteSlot = createButton("Delete", "-fx-background-color: firebrick", delSlotFunc,
                76, 26, 18);
        deleteSlot.setId("del id" + index);
        btnHolder.setAlignment(Pos.CENTER);
        btnHolder.setSpacing(20);
        btnHolder.getChildren().addAll(editSlot, deleteSlot);
        singleTimeInput.getChildren().addAll(indexLabel, dayLabel, rangeLabel, btnHolder);
        timeInputs.getChildren().add(singleTimeInput);
    }

    public void updateTimeInputs(){
        center.getChildren().remove(timeInputs);
        timeInputs = new FlowPane();
        timeInputs.setHgap(10);
        timeInputs.setVgap(10);
        center.getChildren().add(timeInputs);
        for(int i = 0; i < timeSlots.size(); i++){
            addTimeInput(i);
        }
    }

    public void editTimeSlot(int index, TimeSlot timeSlot){
        for(int i = 0; i < timeSlots.size(); i++){
            if(index == i){
                TimeSlot newTime = new TimeSlot(timeSlot.getDay(), timeSlot.getStartTime(), timeSlot.getEndTime());
                timeSlots.set(i, newTime);
            }
        }
    }

    private void createButtonEvents(Stage stage){
        backFunc = createSceneChangeEvent(stage, Main.homePage.getScene());
        advFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                studentResult = new StudentResult(stage, (String) subjectChooser.getValue(), timeSlots);
            }
        };
        addTimeFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                timePopUp = new TimePopUp(true);
            }
        };
        editSlotFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button clickedBtn = (Button) actionEvent.getSource();
                for(int i = 0; i < timeSlots.size(); i++){
                    if(clickedBtn.getId().equals("edit id" + i)){
                        timePopUp = new TimePopUp(true, timeSlots.get(i), i);
                    }
                }
            }
        };
        delSlotFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button clickedBtn = (Button) actionEvent.getSource();
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this TimeSlot?",
                        ButtonType.YES, ButtonType.CANCEL);
                conf.setHeaderText("");
                Optional<ButtonType> result = conf.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.YES){
                    for(int i = 0; i < timeSlots.size(); i++){
                        if(clickedBtn.getId().equals("del id" + i)){
                            timeSlots.remove(i);
                            updateTimeInputs();
                            break;
                        }
                    }
                }
            }
        };
    }
}
