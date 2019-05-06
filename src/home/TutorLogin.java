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

    private Scene scene;
    ComboBox tutorDropDown;
    HBox buttons;
    public EventHandler<ActionEvent> backFunc;
    public EventHandler<ActionEvent> advFunc;

    public TutorLogin(Stage stage) {
        tutorDropDown = new ComboBox();
        tutorDropDown.getItems().addAll("Kristina Wolinski", "Gati Aher", "Mike Winters");      //Will import data through database eventually
        pane.setCenter(tutorDropDown);
        createHeader("-fx-background-color: deepskyblue;", "Tutor", Color.FLORALWHITE);
        backFunc = createSceneChangeEvent(stage, Main.getHomePage());
//        backFunc = new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Main.switchPages(stage, Main.homePage);
//            }
//        };
        advFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String tutorName = (String) tutorDropDown.getValue();
                if (tutorName != null) {
                    Main.switchPages(stage, Main.tutorScheduling.getScene());
                    System.out.println("Send to Tutor Scheduling Page. Name: " + tutorName);
                }else
                    System.out.println("Please select a name before proceeding.");
            }
        };
        createTutorSelectionButtons(stage);
        scene = new Scene(pane, 600, 500);
    }

    private void createTutorSelectionButtons(Stage stage){
        buttons = new HBox();
        buttons.setPadding(new Insets(30));
        buttons.setSpacing(240);
        Button backBtn = createButton("Back", "-fx-background-color: indianred", backFunc);
//        backBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Main.switchPages(stage, Main.getHomePage());
//                System.out.println("Send to Start Page");
//            }
//        });
        Button continueBtn = createButton("Continue", "-fx-background-color: deepskyblue", advFunc);
//        continueBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                String tutorName = (String) tutorDropDown.getValue();
//                if (tutorName != null) {
//                    Main.switchPages(stage, Main.tutorScheduling.getScene());
//                    System.out.println("Send to Tutor Scheduling Page. Name: " + tutorName);
//                }else
//                    System.out.println("Please select a name before proceeding.");
//            }
//        });
        buttons.getChildren().addAll(backBtn, continueBtn);
        buttons.setAlignment(Pos.CENTER);
        pane.setBottom(buttons);
    }

    public Scene getScene(){
        return scene;
    }

}
