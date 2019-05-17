package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StudentPage extends Page{

    ComboBox subjectDropDown;
    HBox header;
    HBox buttons;
    EventHandler<ActionEvent> backFunc;
    EventHandler<ActionEvent> advFunc;

    public StudentPage(Stage stage){
        pane = new BorderPane();
        createStudentPageHeader();
        createInputGrid();                // Name, Subject, Time
        backFunc = createSceneChangeEvent(stage, Main.getHomePage());
        advFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Send to Student Result Page");
            }
        };
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
        GridPane grid = new GridPane();
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
        ComboBox subjectChooser = new ComboBox();
        subjectChooser.getItems().addAll("Math", "Science", "English", "Social Studies");
        grid.add(subjectChooser, 2, 2);
        Text timeLabel = new Text("Time:");
        timeLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        timeLabel.setFill(Color.DARKOLIVEGREEN);
        grid.add(timeLabel, 1, 4);

        grid.setAlignment(Pos.TOP_LEFT);
        pane.setCenter(grid);
    }
}
