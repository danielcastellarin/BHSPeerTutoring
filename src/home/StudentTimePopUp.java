package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StudentTimePopUp extends Page{

    Stage popUpWindow;
    HBox navButtons;
    EventHandler<ActionEvent> cancelEvent;
    EventHandler<ActionEvent> doneEvent;
    EventHandler<ActionEvent> addTimeEvent;

    public StudentTimePopUp(){
        popUpWindow = createStage();
        createPopUpHeader("-fx-background-color: darkolivegreen", "Add Availability", Color.FLORALWHITE);
        createTimeSelector();
        createButtonEvents();
        createNavButtons();
        scene = new Scene(pane, 400, 400);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }

    private Stage createStage(){
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setTitle("Add Times");
        return stage;
    }

    private void createTimeSelector(){

    }

    private void createButtonEvents(){
        cancelEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Action cancelled");
                popUpWindow.close();
            }
        };
        doneEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Send Data back to Student Page
                System.out.println("Change Applied");
                popUpWindow.close();
            }
        };
        addTimeEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        };
    }

    private void createNavButtons(){
        navButtons = new HBox();
        navButtons.setPadding(new Insets(20));
        navButtons.setSpacing(140);
        Button cancelBtn = createButton("Cancel", "-fx-background-color: indianred", cancelEvent);
        Button doneBtn = createButton("Submit", "-fx-background-color: darkolivegreen", doneEvent);
        navButtons.getChildren().addAll(cancelBtn, doneBtn);
        navButtons.setAlignment(Pos.CENTER);
        pane.setBottom(navButtons);
    }

}
