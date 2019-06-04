package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TutorScheduling extends Page{

    FlowPane calendar;
    HBox buttons;
    VBox calendarBox;
    String tutorName;
    EventHandler<ActionEvent> schedulePopUp;
    EventHandler<ActionEvent> backFunc;
    EventHandler<ActionEvent> advFunc;

    ArrayList<TimeSlot> timeSlots = new ArrayList<>();

    public static TimeSelectPopUp timeSelectPopUp;

    public TutorScheduling(Stage stage, String name){
        tutorName = name;
        createHeader("-fx-background-color: deepskyblue", tutorName, Color.FLORALWHITE);
        createButtonEvents(stage);
        retrieveTutorInfo();
        createCalendarBox();
        createTutorSchedulingButtons();
        createScene();
        stage.setScene(scene);
    }

    private void createCalendarBox(){
        calendarBox = new VBox();
        calendarBox.setSpacing(10);
        calendarBox.setPadding(new Insets(20));
        calendarBox.setAlignment(Pos.CENTER);
        Text calendarLabel = new Text("Edit Availability:");
        calendarLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        calendarLabel.setFill(Color.DEEPSKYBLUE);
        calendarBox.getChildren().add(calendarLabel);
//        calendarBox.setStyle("-fx-background-color: deepskyblue");

        updateCenter();

        pane.setCenter(calendarBox);
    }

    private void retrieveTutorInfo(){
        String firstName = tutorName.substring(0, tutorName.indexOf(" "));
        String lastName = tutorName.substring(tutorName.indexOf(" ") + 1);
        JavaToMySQL retrieveTimeSlotsQuery = new JavaToMySQL("SELECT day, start, end FROM timeslots " +
                "WHERE lasid IN(SELECT lasid FROM tutors " +
                "WHERE first_name = \"" + firstName + "\" AND last_name = \"" + lastName + "\");");
        retrieveTimeSlotsQuery.doQuery();
        timeSlots = retrieveTimeSlotsQuery.readTimeSlots();
//        System.out.println(timeSlots);
    }

    private void createButtonEvents(Stage stage){
        schedulePopUp = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Hyperlink clickedBtn = (Hyperlink) actionEvent.getSource();
                for(int i = 0; i < 7; i++){
                    for(int j = 0; j < timeSlots.size(); j++){
                        if(clickedBtn.getId().equals("day id" + i + ", slot id" + j)){
                            timeSelectPopUp = new TimeSelectPopUp(timeSlots.get(j), j, true);
                        }
                    }
                }
            }
        };
        advFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.tutorDone.setHeaderText(tutorName);
                Main.switchPages(stage, Main.tutorDone.getScene());
                System.out.println("Send to Tutor Done Page.");
                System.out.println("Update " + tutorName + "'s info in database" + tutorName);
            }
        };
        backFunc = createSceneChangeEvent(stage, Main.tutorLogin.getScene());
    }

    public void editTimeSlot(int index, TimeSlot timeSlot){
        for(int i = 0; i < timeSlots.size(); i++){
            if(index == i){
                TimeSlot newTime = new TimeSlot(timeSlot.getDay(), timeSlot.getStartTIme(), timeSlot.getEndTime());
                timeSlots.set(i, newTime);
            }
        }
    }
    public void updateCenter(){
        if(calendarBox.getChildren().size() > 1)
            calendarBox.getChildren().remove(calendar);

        calendar = new FlowPane();
        calendar.setAlignment(Pos.CENTER);
        calendar.setHgap(5);
        calendar.setVgap(5);

        Text day;
        for(int i = 0; i < 7; i++){
            VBox column = new VBox();
            column.setSpacing(10);
            column.setAlignment(Pos.TOP_CENTER);

            switch (i){
                case 0:
                    day = new Text("Sunday");
                    break;
                case 1:
                    day = new Text("Monday");
                    break;
                case 2:
                    day = new Text("Tuesday");
                    break;
                case 3:
                    day = new Text("Wednesday");
                    break;
                case 4:
                    day = new Text("Thursday");
                    break;
                case 5:
                    day = new Text("Friday");
                    break;
                default:
                    day = new Text("Saturday");
                    break;
            }
            column.getChildren().add(day);

            for(int j = 0; j < timeSlots.size(); j++){
                if(timeSlots.get(j).getDay().equals(day.getText())){
                    Hyperlink timeSlotLink = new Hyperlink(numToTimeConvert(timeSlots.get(j).getStartTIme()) + "-" +
                            numToTimeConvert(timeSlots.get(j).getEndTime()));
                    timeSlotLink.setId("day id" + i + ", slot id" + j);
                    timeSlotLink.setOnAction(schedulePopUp);
                    column.getChildren().add(timeSlotLink);
                }
            }

            calendar.getChildren().add(column);
        }
        calendarBox.getChildren().add(calendar);
    }

    private void createTutorSchedulingButtons(){
        buttons = new HBox();
        buttons.setPadding(new Insets(30));
        buttons.setSpacing(240);
        Button backBtn = createButton("Back", "-fx-background-color: indianred", backFunc);
        Button submitBtn = createButton("Submit", "-fx-background-color: deepskyblue", advFunc);
        buttons.getChildren().addAll(backBtn, submitBtn);
        buttons.setAlignment(Pos.CENTER);
        pane.setBottom(buttons);
    }
}
