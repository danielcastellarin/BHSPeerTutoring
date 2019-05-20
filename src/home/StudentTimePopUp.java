package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    }

    private Stage createStage(){
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setTitle("Add Times");
        return stage;
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

}
