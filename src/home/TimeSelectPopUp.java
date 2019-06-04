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
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TimeSelectPopUp extends Page{

    Stage popUpWindow;
    HBox navButtons;
    ComboBox dayChooser;
    EventHandler<ActionEvent> cancelEvent;
    EventHandler<ActionEvent> submitEvent;

    String day;
    int startTime;
    int endTime;

    public TimeSelectPopUp(TimeSlot ts){
        day = ts.getDay();
        startTime = ts.getStartTIme();
        endTime = ts.getEndTime();

        popUpWindow = createStage();
        createPopUpHeader("-fx-background-color: deepskyblue", "Time Select", Color.FLORALWHITE);
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
        stage.setTitle("Time Selector");
        return stage;
    }

    //User selects time for specific day
    private void createTimeSelector(){
        VBox vbox = new VBox();

        VBox dayBox = new VBox();
        Text dayLabel = new Text("Day of the Week:");
        dayChooser = new ComboBox();
        dayChooser.getItems().addAll("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        dayChooser.getSelectionModel().select(day);
        dayBox.getChildren().addAll(dayLabel, dayChooser);
        dayBox.setAlignment(Pos.CENTER);

        HBox timeBox = new HBox();
        VBox startBox = createTimeBox("Start", startTime);
        VBox endBox = createTimeBox("End", endTime);
        timeBox.getChildren().addAll(startBox, endBox);
        timeBox.setSpacing(10);
        timeBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(dayBox, timeBox);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        pane.setCenter(vbox);
    }

    private VBox createTimeBox(String str, double startingVal){
        VBox vbox = new VBox();
        Text label = new Text(str + " Time:");
        Text time = new Text();
        Slider slider = createSlider(startingVal);
        time.setText(numToTimeConvert((int) slider.getValue()));
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                time.setText(numToTimeConvert(t1.intValue()));
                if(str.equals("Start"))
                    startTime = t1.intValue();
                else
                    endTime = t1.intValue();
            }
        });
        vbox.getChildren().addAll(label, slider, time);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private Slider createSlider(double startingVal){
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(2400);
        slider.setValue(startingVal);
        slider.setBlockIncrement(25);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(100);
        slider.setSnapToTicks(true);

        return slider;
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
                day = (String) dayChooser.getValue();
                if(endTime > startTime && day != null){

                    popUpWindow.close();
                }else{
                    System.out.println("Please enter a valid time range and/or day.");
                }
            }
        };
    }

    private void createNavButtons(){
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
