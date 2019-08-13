package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TutorLogin extends Page{

    private EventHandler<ActionEvent> advFunc;
    private EventHandler<ActionEvent> backFunc;
    ComboBox tutorDropDown;

    ArrayList<String> tutorNames;

    public static TutorScheduling tutorScheduling;

    public TutorLogin(Stage stage) {
        createHeader("-fx-background-color: deepskyblue", "Tutors", Color.FLORALWHITE);
        createCenter();
        backFunc = createSceneChangeEvent(stage, Main.homePage.getScene());
        advFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String tutorName = (String) tutorDropDown.getValue();
                if (tutorName != null) {
                    tutorScheduling = new TutorScheduling(stage, tutorName);
                }else
                    System.out.println("Please select a name before proceeding.");
            }
        };
        createNavButtonBox("-fx-background-color: deepskyblue", backFunc, advFunc, "Submit");
        createScene();
        stage.setScene(scene);
    }

    private void createCenter(){
        JavaToMySQL retrieveTutorNames = new JavaToMySQL("SELECT DISTINCT first_name, last_name " +
                "FROM tutors ORDER BY last_name;");
        retrieveTutorNames.doQuery();
        tutorNames = retrieveTutorNames.readTutorNames();
        tutorDropDown = new ComboBox();
        tutorDropDown.getItems().addAll(tutorNames);
        tutorDropDown.setStyle("-fx-font: 20px \"Serif\";");
        pane.setCenter(tutorDropDown);
    }
}
