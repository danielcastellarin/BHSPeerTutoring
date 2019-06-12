package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HomePage extends Page {

    EventHandler<ActionEvent> goStudent;
    EventHandler<ActionEvent> goTutor;

    public static StudentPage studentPage;
    public static TutorLogin tutorLogin;

    public HomePage(Stage stage){
        stage.setTitle("Peer Tutoring Portal");
        createHeader("-fx-background-color: firebrick", "Peer Tutoring Portal", Color.FLORALWHITE);
        createButtonEvents(stage);
        createCenter();
        scene = new Scene(pane, 800, 650);
        stage.setScene(scene);
        stage.show();
    }

    private void createButtonEvents(Stage stage){
        goStudent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                studentPage = new StudentPage(stage);
            }
        };
        goTutor = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tutorLogin = new TutorLogin(stage);
            }
        };
    }

    private void createCenter(){
        VBox centerBox = new VBox();
        centerBox.setStyle("-fx-background-color: silver");
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(40);
        Button studentBtn = createButton("Student", "-fx-background-color: darkolivegreen", goStudent);
        studentBtn.setPrefSize(200, 75);
        studentBtn.setFont(Font.font("Constantia", FontWeight.NORMAL, 24));
        Button tutorBtn = createButton("Tutor", "-fx-background-color: deepskyblue", goTutor);
        tutorBtn.setPrefSize(200, 75);
        tutorBtn.setFont(Font.font("Constantia", FontWeight.NORMAL, 24));
        centerBox.getChildren().addAll(studentBtn, tutorBtn);
        pane.setCenter(centerBox);
    }
}
