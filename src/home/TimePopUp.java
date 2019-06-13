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

public class TimePopUp extends Page {

    Stage popUpWindow;
    ComboBox dayChooser;
    HBox navButtons;
    EventHandler<ActionEvent> cancelEvent;
    EventHandler<ActionEvent> doneEvent;

    String day;
    int startTime;
    int endTime;
    boolean isStudent;
    int slotIndex;
    TimeSlot origTS;
    String color;

    // When creating a timeslot, this constructor is used
    public TimePopUp(boolean isStudent){
        this.isStudent = isStudent;
        startTime = 1200;
        endTime = 1200;
        if(isStudent)
            color = "-fx-background-color: darkolivegreen";
        else
            color = "-fx-background-color: deepskyblue";
        popUpWindow = createStage("Add Availability");
        createPopUpHeader(color, "Add Availability", Color.FLORALWHITE);
        createTimeSelector();
        createButtonEvents(false);
        createNavButtons(color);
        scene = new Scene(pane, 400, 400);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }


    // When editing a timeslot, this constructor is used
    public TimePopUp(boolean isStudent, TimeSlot ts, int i){
        day = ts.getDay();
        startTime = ts.getStartTIme();
        endTime = ts.getEndTime();
        slotIndex = i;
        this.isStudent = isStudent;
        origTS = ts;

        popUpWindow = createStage("Time Editor");
        if(isStudent)
            color = "-fx-background-color: darkolivegreen";
        else
            color = "-fx-background-color: deepskyblue";
        createPopUpHeader(color, "Time Editor", Color.FLORALWHITE);
        createTimeSelector();
        createButtonEvents(true);
        createNavButtons(color);
        scene = new Scene(pane, 400, 400);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }

    private Stage createStage(String title){
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setTitle(title);
        return stage;
    }

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

    private VBox createTimeBox(String str, double initialVal){
        VBox vbox = new VBox();
        Text label = new Text(str + " Time:");
        Text time = new Text();
        Slider slider = createSlider(initialVal);
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

    private Slider createSlider(double initialVal){
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(2400);
        slider.setValue(initialVal);
        slider.setBlockIncrement(25);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(100);
        slider.setSnapToTicks(true);

        return slider;
    }

    private void createButtonEvents(boolean isEditor){
        cancelEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popUpWindow.close();
            }
        };
        doneEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                day = (String) dayChooser.getValue();
                if(endTime > startTime && day != null){
                    TimeSlot timeSlot = new TimeSlot(day, startTime, endTime);
                    if(isEditor){   // For editing a TimeSlot
                        if(isStudent){
                            HomePage.studentPage.editTimeSlot(slotIndex, timeSlot);
                            HomePage.studentPage.updateTimeInputs();
                        }else{
                            TutorLogin.tutorScheduling.editTimeSlot(slotIndex, timeSlot);
                            TutorLogin.tutorScheduling.updateCenter();
                            TutorLogin.tutorScheduling.addTimeSlotToQuery(origTS, timeSlot);
                        }
                    }else{          // For adding availability
                        if(isStudent){
                            HomePage.studentPage.timeSlots.add(timeSlot);
                            HomePage.studentPage.addTimeInput(HomePage.studentPage.timeSlots.size() - 1);
                        }else{
                            TutorLogin.tutorScheduling.addTimeSlot(timeSlot);
                        }
                    }
                    popUpWindow.close();
                }else{
                    System.out.println("Please enter a valid time range and/or day.");
                }
            }
        };
    }

    private void createNavButtons(String themeColor){
        navButtons = new HBox();
        navButtons.setPadding(new Insets(20));
        navButtons.setSpacing(140);
        Button cancelBtn = createButton("Cancel", "-fx-background-color: indianred", cancelEvent,
                150, 50, 20);
        Button doneBtn = createButton("Submit", themeColor, doneEvent, 150, 50, 20);
        navButtons.getChildren().addAll(cancelBtn, doneBtn);
        navButtons.setAlignment(Pos.CENTER);
        pane.setBottom(navButtons);
    }
}
