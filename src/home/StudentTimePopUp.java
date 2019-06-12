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

public class StudentTimePopUp extends Page{

    Stage popUpWindow;
    HBox navButtons;
    ComboBox dayChooser;
    EventHandler<ActionEvent> cancelEvent;
    EventHandler<ActionEvent> doneEvent;

    String day;
    int startTime;
    int endTime;
    boolean isTutor;

    public StudentTimePopUp(boolean isTut){
        isTutor = isTut;
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
        VBox vbox = new VBox();

        VBox dayBox = new VBox();
        Text dayLabel = new Text("Day of the Week:");
        dayChooser = new ComboBox();
        dayChooser.getItems().addAll("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        dayBox.getChildren().addAll(dayLabel, dayChooser);
        dayBox.setAlignment(Pos.CENTER);

        HBox timeBox = new HBox();
        VBox startBox = createTimeBox("Start");
        VBox endBox = createTimeBox("End");
        timeBox.getChildren().addAll(startBox, endBox);
        timeBox.setSpacing(10);
        timeBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(dayBox, timeBox);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        pane.setCenter(vbox);
    }

    private VBox createTimeBox(String str){
        VBox vbox = new VBox();
        Text label = new Text(str + " Time:");
        Text time = new Text();
        Slider slider = createSlider();
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

    private Slider createSlider(){
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(2400);
        slider.setValue(1200);
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
        doneEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Send Data back to Student Page
                day = (String) dayChooser.getValue();
                if(endTime > startTime && day != null){
                    TimeSlot timeSlot = new TimeSlot(day, startTime, endTime);
                    if(isTutor){
                        TutorLogin.tutorScheduling.addTimeSlot(timeSlot);
                    }else{
                        HomePage.studentPage.timeSlots.add(timeSlot);
                        HomePage.studentPage.addTimeInput(HomePage.studentPage.timeSlots.size() - 1);
                    }
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
        Button cancelBtn = createButton("Cancel", "-fx-background-color: indianred", cancelEvent,
                150, 50, 20);
        Button doneBtn = createButton("Submit", "-fx-background-color: darkolivegreen", doneEvent,
                150, 50, 20);
        navButtons.getChildren().addAll(cancelBtn, doneBtn);
        navButtons.setAlignment(Pos.CENTER);
        pane.setBottom(navButtons);
    }

}
