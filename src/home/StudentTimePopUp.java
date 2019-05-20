package home;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StudentTimePopUp extends Page{

    Stage popUpWindow;
    HBox navButtons;
    EventHandler<ActionEvent> cancelEvent;
    EventHandler<ActionEvent> doneEvent;
    EventHandler<ActionEvent> addTimeEvent;
    int startSliderVal;
    int endSliderVal;

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
        HBox hbox = new HBox();

        //create layout here

        Text dayLabel = new Text("Day of the Week:");

        ComboBox dayChooser = new ComboBox();
        dayChooser.getItems().addAll("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

        Text startValueLabel = new Text();

        Slider startTime = new Slider();
        startTime.setMin(0);
        startTime.setMax(2400);
        startTime.setValue(1200);
        startTime.setShowTickMarks(true);
        startTime.setBlockIncrement(25);
        startTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                startValueLabel.setText(numToTimeConvert(t1.intValue()));
                System.out.println("OG val: " + t1);
                System.out.println("Time: " + startValueLabel.getText());
            }
        });

        Slider endTime = new Slider();


        hbox.getChildren().add(dayChooser);
        hbox.getChildren().add(startTime);
        hbox.setAlignment(Pos.CENTER);
        pane.setCenter(hbox);
//        pane.setCenter();
    }

    /*
    Each interval is 15 minutes
    Slider increments by 25
    minute conversion --> og * 3/5

    divide by hundred to get rid of last 2 digits
    analyze to determine morning/evening

     */
    private String numToTimeConvert(int num){
        String time;
        boolean isEvening = false;


        int hours = num / 100;
        int minutes = (num - (hours * 100)) * 3/5;

        if(hours > 12){
            hours -= 12;
            isEvening = true;
        }else if (hours == 0){
            hours = 12;
        }

        if(minutes == 0){
            if (isEvening){
                time = hours + ":" + minutes + "0pm";
            }else{
                time = hours + ":" + minutes + "0am";
            }
        }else{
            if (isEvening){
                time = hours + ":" + minutes + "pm";
            }else{
                time = hours + ":" + minutes + "am";
            }
        }

        return time;
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
