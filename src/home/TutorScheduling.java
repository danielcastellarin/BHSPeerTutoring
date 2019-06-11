package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    EventHandler<ActionEvent> schedulePopUp;
    EventHandler<ActionEvent> backFunc;
    EventHandler<ActionEvent> advFunc;
    EventHandler<ActionEvent> addTS;

    ArrayList<TimeSlot> timeSlots = new ArrayList<>();
    ArrayList<TimeSlot> changedTimeSlots = new ArrayList<>();

    String tutorName;
    int lasid;
    String editTimeSlotsQuery = "";

    public static TimeSelectPopUp timeSelectPopUp;
    public static StudentTimePopUp addTimePopUp;
    public static TutorDone tutorDone;

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

        Button addBtn = new Button("Add");
        addBtn.setOnAction(addTS);
        calendarBox.getChildren().add(addBtn);


        pane.setCenter(calendarBox);
    }

    private void retrieveTutorInfo(){
        lasid = retrieveLasid();
        JavaToMySQL retrieveTimeSlotsQuery = new JavaToMySQL("SELECT day, start, end FROM timeslots " +
                "WHERE lasid = " + lasid + ";");
        retrieveTimeSlotsQuery.doQuery();
        timeSlots = retrieveTimeSlotsQuery.readTimeSlots();
    }

    private int retrieveLasid(){
        String firstName = tutorName.substring(0, tutorName.indexOf(" "));
        String lastName = tutorName.substring(tutorName.indexOf(" ") + 1);
        JavaToMySQL retrieveLasidQuery = new JavaToMySQL("SELECT lasid FROM tutors " +
                "WHERE first_name = \"" + firstName + "\" AND last_name = \"" + lastName + "\";");
        retrieveLasidQuery.doQuery();
        return retrieveLasidQuery.readLasid();
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
        addTS = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addTimePopUp = new StudentTimePopUp(true);
            }
        };
        advFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Send to Tutor Done Page.");
                if(!editTimeSlotsQuery.isEmpty()){
                    JavaToMySQL updateTutorTimeSlots = new JavaToMySQL(editTimeSlotsQuery);
                    updateTutorTimeSlots.doUpdate();
                }
                tutorDone = new TutorDone(stage, tutorName, changedTimeSlots);
            }
        };
        backFunc = createSceneChangeEvent(stage, Main.tutorLogin.getScene());
    }

    public void editTimeSlot(int index, TimeSlot timeSlot){
        for(int i = 0; i < timeSlots.size(); i++){
            if(index == i){
                TimeSlot newTime = new TimeSlot(timeSlot.getDay(), timeSlot.getStartTIme(), timeSlot.getEndTime());
                timeSlots.set(i, newTime);
                changedTimeSlots.add(newTime);
                break;
            }
        }
    }

    public void addTimeSlot(TimeSlot ts){
        timeSlots.add(ts);
        changedTimeSlots.add(ts);
//        JavaToMySQL appendTimeSlot = new JavaToMySQL("INSERT INTO timeslots(lasid, day, start, end) " +
//                "VALUES (" + lasid + ", \"" + ts.getDay() + "\", " + ts.getStartTIme() + ", " + ts.getEndTime() + ");");
//        appendTimeSlot.doUpdate();
        editTimeSlotsQuery += "INSERT INTO timeslots(lasid, day, start, end) VALUES " +
                "(" + lasid + ", \"" + ts.getDay() + "\", " + ts.getStartTIme() + ", " + ts.getEndTime() + "); ";
        updateCenter();
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
        calendarBox.getChildren().add(1, calendar);
    }


    public void addTimeSlotToQuery(TimeSlot ogTS, TimeSlot ts){
        boolean isDayDifferent = !ogTS.getDay().equals(ts.getDay());
        boolean isStartDifferent = ogTS.getStartTIme() != ts.getStartTIme();
        boolean isEndDifferent = ogTS.getEndTime() != ts.getEndTime();

        editTimeSlotsQuery += "UPDATE timeslots SET ";
        if(isDayDifferent)
            editTimeSlotsQuery += "day = \"" + ts.getDay() + "\"";
        if(isStartDifferent) {
            if (isDayDifferent)
                editTimeSlotsQuery += ", ";
            editTimeSlotsQuery += "start = " + ts.getStartTIme();
        }if(isEndDifferent){
            if(isDayDifferent || isStartDifferent)
                editTimeSlotsQuery += ", ";
            editTimeSlotsQuery += "end = " + ts.getEndTime();
        }

        editTimeSlotsQuery += " WHERE day = \"" + ogTS.getDay() + "\" AND start = " + ogTS.getStartTIme() +
                " AND end = " + ogTS.getEndTime() + " AND lasid = " + lasid + "; ";
        System.out.println(editTimeSlotsQuery);
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
