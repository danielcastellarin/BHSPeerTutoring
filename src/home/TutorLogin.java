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
    HBox buttons;

    ArrayList<String> tutorNames;

    public static TutorScheduling tutorScheduling;

    public TutorLogin(Stage stage) {
        createHeader("-fx-background-color: deepskyblue;", "Tutor", Color.FLORALWHITE);
        createCenter();
        backFunc = createSceneChangeEvent(stage, Main.getHomePage());
        advFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String tutorName = (String) tutorDropDown.getValue();
                if (tutorName != null) {
                    System.out.println("Send to Tutor Scheduling Page. Name: " + tutorName);
                    tutorScheduling = new TutorScheduling(stage, tutorName);

                }else
                    System.out.println("Please select a name before proceeding.");
            }
        };
        createTutorSelectionButtons();
        createScene();
    }

    private void createCenter(){
        JavaToMySQL retrieveTutorNames = new JavaToMySQL("SELECT DISTINCT first_name, last_name " +
                "FROM tutors ORDER BY last_name;");
        retrieveTutorNames.doQuery();
        tutorNames = retrieveTutorNames.readTutorNames();
        tutorDropDown = new ComboBox();
        tutorDropDown.getItems().addAll(tutorNames);
        pane.setCenter(tutorDropDown);
    }

    private void createTutorSelectionButtons(){
        buttons = new HBox();
        buttons.setPadding(new Insets(30));
        buttons.setSpacing(240);
        Button backBtn = createButton("Back", "-fx-background-color: indianred", backFunc);
        Button continueBtn = createButton("Continue", "-fx-background-color: deepskyblue", advFunc);
        buttons.getChildren().addAll(backBtn, continueBtn);
        buttons.setAlignment(Pos.CENTER);
        pane.setBottom(buttons);
    }

    public String getComboBoxString(){
        return String.valueOf(tutorDropDown.getValue());
    }

}
