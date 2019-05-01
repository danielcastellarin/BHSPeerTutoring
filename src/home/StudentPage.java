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

public class StudentPage {

    private Scene scene;
    BorderPane pane;
    ComboBox subjectDropDown;
    HBox header;
    HBox buttons;

    public StudentPage(Stage stage){
        pane = new BorderPane();
        createStudentPageHeader();
        createInputGrid();                // Name, Subject, Time
        createStudentPageButtons(stage);
        scene = new Scene(pane, 600, 500);
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

    private void createStudentPageButtons(Stage stage){
        buttons = new HBox();
        buttons.setPadding(new Insets(30));
        buttons.setSpacing(240);
        Button backBtn = createButton("Back", "-fx-background-color: indianred");
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.switchPages(stage, Main.getHomePage());
                System.out.println("Send to Start Page");
            }
        });
        Button submitBtn = createButton("Submit", "-fx-background-color: darkolivegreen");
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println("Send to Student Result Page");
            }
        });
        buttons.getChildren().addAll(backBtn, submitBtn);
        buttons.setAlignment(Pos.CENTER);
        pane.setBottom(buttons);
    }

    private Button createButton(String name, String btnColor){
        Button button = new Button(name);
        button.setPrefSize(150, 50);
        button.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        button.setTextFill(Color.FLORALWHITE);
        button.setStyle(btnColor);
        return button;
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

    public Scene getScene(){
        return scene;
    }

}
