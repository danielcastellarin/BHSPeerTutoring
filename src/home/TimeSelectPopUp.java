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

public class TimeSelectPopUp extends Page{

    Stage popUpWindow;
    HBox navButtons;
    EventHandler<ActionEvent> cancelEvent;
    EventHandler<ActionEvent> submitEvent;

    public TimeSelectPopUp(){
        popUpWindow = createStage();
        createPopUpHeader("-fx-background-color: deepskyblue", "Time Select", Color.FLORALWHITE);
        createTimeSelector();
        createButtonEvents();
        createPopUpNavigationButtons();
        scene = new Scene(pane, 400, 400);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }

    private Stage createStage(){
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setTitle("Time Selector");
        return stage;
    }

    //User selects time for specific day
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
        submitEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Send Data back to Tutor Scheduling
                System.out.println("Change Applied");
                popUpWindow.close();
            }
        };
    }

    private void createPopUpNavigationButtons(){
        navButtons = new HBox();
        navButtons.setPadding(new Insets(20));
        navButtons.setSpacing(140);
        Button cancelBtn = createButton("Cancel", "-fx-background-color: indianred", cancelEvent);
        Button submitBtn = createButton("Submit", "-fx-background-color: deepskyblue", submitEvent);
        navButtons.getChildren().addAll(cancelBtn, submitBtn);
        navButtons.setAlignment(Pos.CENTER);
        pane.setBottom(navButtons);
    }

}
