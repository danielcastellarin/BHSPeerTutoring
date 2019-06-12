package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StudentResult extends Page {

    EventHandler<ActionEvent> homeFunc;
    VBox centerBoxes;
    HBox studentInputBox;
    FlowPane timeInputs;
    FlowPane matchedTutors;
    HBox buttonHolder;

    private String subject;
    private ArrayList<TimeSlot> timeSlots;
    private ArrayList<ArrayList<String>> matchedList;

    public static TutorProfilePopUp tutorProfilePopUp;

    public StudentResult(Stage stage, String subj, ArrayList<TimeSlot> slots){
        subject = subj;
        timeSlots = slots;

        createHeader("-fx-background-color: darkolivegreen", "Student Results", Color.FLORALWHITE);
        homeFunc = createSceneChangeEvent(stage, Main.homePage.getScene());
        createCenter();
        createHomeButton();
        createScene();
        stage.setScene(scene);
    }

    private void createCenter(){
        centerBoxes = new VBox();
        createStudentInput();
        createMatchedTutorsGrid();
        centerBoxes.setAlignment(Pos.CENTER);
        pane.setCenter(centerBoxes);
    }

    private void createStudentInput(){
        studentInputBox = new HBox();
        studentInputBox.setSpacing(20);

        if (subject != null){
            VBox subjectBox = new VBox();
            Text subjectLabel = new Text("Subject:");
            Text subjectInput = new Text(subject);
            subjectBox.getChildren().addAll(subjectLabel, subjectInput);
            studentInputBox.getChildren().add(subjectBox);
        }
        if(!timeSlots.isEmpty()){
            for(int i = 0; i < timeSlots.size(); i++){
                studentInputBox.getChildren().add(createTimeInput(i));
            }
        }

        studentInputBox.setAlignment(Pos.CENTER);
        centerBoxes.getChildren().add(studentInputBox);
    }

    private FlowPane createTimeInput(int i){
        FlowPane timePane = new FlowPane();
        timePane.setOrientation(Orientation.VERTICAL);
        Text index = new Text("Time Slot " + (i + 1));
        Text day = new Text("Day: " + timeSlots.get(i).getDay());
        Text range = new Text(numToTimeConvert(timeSlots.get(i).getStartTIme()) +
                " - " + numToTimeConvert(timeSlots.get(i).getEndTime()));
        timePane.getChildren().addAll(index, day, range);
        return timePane;
    }

    private void createMatchedTutorsGrid(){
        matchedTutors = new FlowPane();
        matchedTutors.setOrientation(Orientation.VERTICAL);
        matchedTutors.setVgap(5);
        matchedTutors.setHgap(20);
        matchedTutors.setAlignment(Pos.CENTER);

        if(timeSlots.isEmpty()){
            //SUBJECT QUERY ONLY
            JavaToMySQL subjectOnlyQuery = new JavaToMySQL("SELECT * FROM tutors WHERE lasid IN( " +
                    "SELECT lasid FROM subjects WHERE subject = \"" + subject + "\");");
            subjectOnlyQuery.doQuery();
            matchedList = subjectOnlyQuery.readTutorProfiles();
        }else if(subject == null){
            //TIMESLOT QUERY ONLY
            JavaToMySQL timeSlotOnlyQuery = new JavaToMySQL(
                    "SELECT * FROM tutors WHERE lasid IN ( SELECT lasid FROM timeslots WHERE (" +
                            "(end >= " + timeSlots.get(0).getStartTIme() + " " +
                            "AND end <= " + timeSlots.get(0).getEndTime() + ")" +
                            " OR " +
                            "(start <= " + timeSlots.get(0).getEndTime() + " " +
                            "AND end >= " + timeSlots.get(0).getEndTime() + ")" +
                            ") AND day = \"" + timeSlots.get(0).getDay() + "\");");

            timeSlotOnlyQuery.doQuery();
            matchedList = timeSlotOnlyQuery.readTutorProfiles();
        }else{
            //BOTH QUERIES
            String query = "SELECT tutors.* FROM tutors, subjects, timeslots " +
                    "WHERE (tutors.lasid = subjects.lasid AND tutors.lasid = timeslots.lasid)" +
                    "AND subjects.subject = \"" + subject + "\" AND (";
            for (int i = 0; i < timeSlots.size(); i++){
                query = appendTimeSlotToQuery(query, i);
            }
            JavaToMySQL bothQueries = new JavaToMySQL(query);
            bothQueries.doQuery();
            matchedList = bothQueries.readTutorProfiles();
        }
        System.out.println(matchedList);           //print returned profiles

        for(int i = 0; i < matchedList.size(); i++){
            int finalI = i;
            Hyperlink accessTutorInfo = new Hyperlink(matchedList.get(i).get(1)+ " " + matchedList.get(i).get(2)/*, INSERT IMAGE*/);
            accessTutorInfo.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // create new tutorProfile pop up window
                    // pass in list.get(i)
                    tutorProfilePopUp = new TutorProfilePopUp(matchedList.get(finalI));
                }
            });
            matchedTutors.getChildren().add(accessTutorInfo);
        }

        centerBoxes.getChildren().add(matchedTutors);
    }


    /* Option 1
        Tutor Start                 Tutor End
            |----------------------------|
                        |---------------------------------|
                    Student Start                    Student End

            Overlap will occur when Tutor End is between Student Start and End
     */

    /* Option 2
        Student Start              Student End
            |----------------------------|
                        |---------------------------------|
                    Tutor Start                       Tutor End

            Overlap will occur when Student End is between Tutor Start and End
     */
//    private boolean isOverlap(String tutorStartStr, String tutorEndStr, String studentStartStr, String studentEndStr){
//        int tutorStart = convertTimeToNum(tutorStartStr);
//        int tutorEnd = convertTimeToNum(tutorEndStr);
//        int studentStart = convertTimeToNum(studentStartStr);
//        int studentEnd = convertTimeToNum(studentEndStr);
//
//        if(tutorEnd >= studentStart && tutorEnd <= studentEnd)
//            return true;
//        else if(studentEnd >= tutorStart && studentEnd <= tutorEnd)
//            return true;
//
//        return false;
//    }

    // 12am = 0
    // 3am = 3
    // 12pm = 12
    // 5pm = 17
    // 11pm = 23
    private int convertTimeToNum(String time){
        int hour = 0;
        int minute = 0;

        minute = Integer.parseInt(time.substring(time.length() - 5, time.length() - 3));
        minute *= 5/3;

        hour = Integer.parseInt(time.substring(0, time.indexOf(":")));

        if(time.endsWith("pm")){
            hour += 12;
        }else if(time.startsWith("12")) {
            hour = 0;
        }

        hour *= 100;

        return hour + minute;
    }

    private String appendTimeSlotToQuery(String query, int slotID){
        String newQuery = query +
                "(" +
                "((timeslots.end >= " + timeSlots.get(0).getStartTIme() + " AND " +
                "timeslots.end <= " + timeSlots.get(0).getEndTime() + ")" +
                " OR " +
                "(timeslots.start <= " + timeSlots.get(0).getEndTime() + " AND " +
                "timeslots.end >= " + timeSlots.get(0).getEndTime() + ")" +
                ") AND timeslots.day = \"" + timeSlots.get(0).getDay() + "\"" +
                ")";
        if (slotID < timeSlots.size() - 1)
            newQuery = newQuery + " OR ";
        else
            newQuery = newQuery + ");";
        return newQuery;
    }

    private void createHomeButton(){
        buttonHolder = new HBox();
        Button homeButton = createButton("Home", "-fx-background-color: indianred", homeFunc,
                150, 50, 20);
        buttonHolder.setPadding(new Insets(30));
        buttonHolder.setAlignment(Pos.BOTTOM_LEFT);
        buttonHolder.getChildren().add(homeButton);
        pane.setBottom(buttonHolder);
    }
}
