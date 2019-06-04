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

public class TutorLogin extends Page{

    private EventHandler<ActionEvent> advFunc;
    private EventHandler<ActionEvent> backFunc;
    ComboBox tutorDropDown;
    HBox buttons;

    public static TutorScheduling tutorScheduling;

    public TutorLogin(Stage stage) {
        tutorDropDown = new ComboBox();
        tutorDropDown.getItems().addAll("Kristina Wolinski", "Gati Aher", "Michael Winters");      //Will import data through database eventually
        pane.setCenter(tutorDropDown);
        createHeader("-fx-background-color: deepskyblue;", "Tutor", Color.FLORALWHITE);
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
