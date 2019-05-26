package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StudentPage extends Page{

    GridPane grid;
    ComboBox subjectChooser;
    HBox header;
    HBox buttons;
    EventHandler<ActionEvent> backFunc;
    EventHandler<ActionEvent> advFunc;
    EventHandler<ActionEvent> addTimeFunc;
    EventHandler<ActionEvent> editSlotFunc;
    EventHandler<ActionEvent> delSlotFunc;

    ArrayList<TimeSlot> timeSlots = new ArrayList<TimeSlot>();

    public static StudentTimePopUp addTimePopUp;
    public static StudentResult studentResult;

    public StudentPage(Stage stage){
        pane = new BorderPane();
        createStudentPageHeader();
        backFunc = createSceneChangeEvent(stage, Main.getHomePage());
        advFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // TODO: Fix error for when subject is left blank
                System.out.println("Send to Student Result Page");
                timeSlots.get(0).printTimeSlot();
                studentResult = new StudentResult(stage, (String) subjectChooser.getValue(), timeSlots);
            }
        };
        addTimeFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Student Time Chooser");
                addTimePopUp = new StudentTimePopUp();
            }
        };
        editSlotFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        };
        delSlotFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        };
        createInputGrid();                // Name, Subject, Time
        createStudentPageButtons();
        createScene();
    }

    private void createStudentPageHeader(){
        header = new HBox();
        header.setPadding(new Insets(30));
        header.setStyle("-fx-background-color: darkolivegreen;");
        Text title = new Text("Students");
        title.setFill(Color.FLORALWHITE);
        title.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 36.0));
        header.getChildren().add(title);
        header.setAlignment(Pos.CENTER);
        pane.setTop(header);
    }

    private void createStudentPageButtons(){
        buttons = new HBox();
        buttons.setPadding(new Insets(30));
        buttons.setSpacing(240);
        Button backBtn = createButton("Back", "-fx-background-color: indianred", backFunc);
        Button submitBtn = createButton("Submit", "-fx-background-color: darkolivegreen", advFunc);
        buttons.getChildren().addAll(backBtn, submitBtn);
        buttons.setAlignment(Pos.CENTER);
        pane.setBottom(buttons);
    }

    private void createInputGrid(){
        grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(100);
        Text studentNameLabel = new Text("Name:");
        studentNameLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        studentNameLabel.setFill(Color.DARKOLIVEGREEN);
        grid.add(studentNameLabel, 1,1);
        TextField studentNameInput = new TextField();
        grid.add(studentNameInput, 1,2);
        Text subjectChooserLabel = new Text("Subject:");
        subjectChooserLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        subjectChooserLabel.setFill(Color.DARKOLIVEGREEN);
        grid.add(subjectChooserLabel, 2, 1);
        subjectChooser = new ComboBox();
        subjectChooser.getItems().addAll("Algebra II", "Science", "English", "Social Studies");
        grid.add(subjectChooser, 2, 2);
        Text timeLabel = new Text("Time:");
        timeLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        timeLabel.setFill(Color.DARKOLIVEGREEN);
        grid.add(timeLabel, 1, 4);
//        Image plusSign = new Image(getClass().getResourceAsStream("flower.png"));
//        ImageView imageView = new ImageView(plusSign);
        Button addTimeBtn = new Button("Add");
        addTimeBtn.setOnAction(addTimeFunc);
        grid.add(addTimeBtn, 2, 4);

        grid.setAlignment(Pos.TOP_LEFT);
        pane.setCenter(grid);
    }



    public void addTimeInput(int index){
        FlowPane flow = new FlowPane();
        flow.setOrientation(Orientation.VERTICAL);
        Text indexLabel = new Text("Time Slot " + (index + 1));
        Text dayLabel = new Text("Day: " + timeSlots.get(index).getDay());
        Text startLabel = new Text("Start: " + numToTimeConvert(timeSlots.get(index).getStartTIme()));
        Text endLabel = new Text("End: " + numToTimeConvert(timeSlots.get(index).getEndTime()));
        HBox buttonHolder = new HBox();
        Button editSlot = new Button("Edit");
        Button deleteSlot = new Button("-");
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.setSpacing(20);
        buttonHolder.getChildren().addAll(editSlot, deleteSlot);

//        timeLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 16));
//        timeLabel.setFill(Color.DARKOLIVEGREEN);
//        startLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 16));
//        startLabel.setFill(Color.DARKOLIVEGREEN);
//        endLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 16));
//        endLabel.setFill(Color.DARKOLIVEGREEN);

        flow.getChildren().addAll(indexLabel, dayLabel, startLabel, endLabel, buttonHolder);
        grid.add(flow, 1 + index, 5);
    }
}
