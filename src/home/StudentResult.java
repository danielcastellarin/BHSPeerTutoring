package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StudentResult extends Page {

    EventHandler<ActionEvent> homeFunc;
    EventHandler<ActionEvent> searchFunc;
    VBox centerBoxes;
    FlowPane timeBox;
    FlowPane matchedTutors;
    HBox buttonHolder;

    private String subject;
    private ArrayList<TimeSlot> timeSlots;
    private ArrayList<ArrayList<String>> matchedList;
    private ArrayList<VBox> days;

    public static TutorProfilePopUp tutorProfilePopUp;

    public StudentResult(Stage stage, String subj, ArrayList<TimeSlot> slots){
        subject = subj;
        timeSlots = slots;

        createHeader("-fx-background-color: darkolivegreen", "Student Results", Color.FLORALWHITE);
        homeFunc = createSceneChangeEvent(stage, Main.homePage.getScene());
        searchFunc = createSceneChangeEvent(stage, HomePage.studentPage.getScene());
        createCenter();
        createNavButtonBox("-fx-background-color: saddlebrown", homeFunc, searchFunc, "Home");
//        createHomeButton();
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

        if (subject != null){
            HBox subjectBox = new HBox();
            subjectBox.setSpacing(40);
            subjectBox.setAlignment(Pos.CENTER);
            subjectBox.setPadding(new Insets(40));
            Text subjectLabel = new Text("Subject:");
            subjectLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 36));
            subjectLabel.setFill(Color.DARKOLIVEGREEN);
            Text subjectInput = new Text(subject);
            subjectInput.setFont(Font.font("System", FontWeight.NORMAL, 20));
            subjectBox.getChildren().addAll(subjectLabel, subjectInput);
            centerBoxes.getChildren().add(subjectBox);
        }

        if(!timeSlots.isEmpty()){
            timeBox = new FlowPane();
            timeBox.setHgap(50);
            timeBox.setVgap(30);
            timeBox.setAlignment(Pos.CENTER);
            timeBox.setPrefWrapLength(950);
            days = new ArrayList<>();

            for(int i = 0; i < timeSlots.size(); i++){
                int existingDayID = -1;
                if(i > 0){
                    for(int j = 0; j < days.size(); j++){
                        if(days.get(j).getId().contains(timeSlots.get(i).getDay())){
                            String substr = days.get(j).getId().substring(2, days.get(j).getId().indexOf('-'));
                            existingDayID = Integer.parseInt(substr);
                            break;
                        }
                    }
                }

                Text range = new Text(numToTimeConvert(timeSlots.get(i).getStartTime()) +
                        " - " + numToTimeConvert(timeSlots.get(i).getEndTime()));
                range.setFont(Font.font("System", FontWeight.NORMAL, 20));

                if(existingDayID > -1) {
                    days.get(existingDayID).getChildren().add(range);
                }else{
                    VBox column = new VBox();
                    column.setAlignment(Pos.TOP_CENTER);
                    column.setSpacing(10);
                    column.setId(timeSlots.get(i).getDay());
                    Text day = new Text(timeSlots.get(i).getDay());
                    day.setFont(Font.font("Constantia", FontWeight.NORMAL, 36));
                    day.setFill(Color.DARKOLIVEGREEN);
                    switch (day.getText()){
                        case "Sunday":
                            column.setId("0 " + i + "- Sunday");
                            break;
                        case "Monday":
                            column.setId("1 " + i + "- Monday");
                            break;
                        case "Tuesday":
                            column.setId("2 " + i + "- Tuesday");
                            break;
                        case "Wednesday":
                            column.setId("3 " + i + "- Wednesday");
                            break;
                        case "Thursday":
                            column.setId("4 " + i + "- Thursday");
                            break;
                        case "Friday":
                            column.setId("5 " + i + "- Friday");
                            break;
                        default:
                            column.setId("6 " + i + "- Saturday");
                            break;
                    }

                    column.getChildren().addAll(day, range);
                    days.add(column);
                    timeBox.getChildren().add(column);
                }
            }

            VBox swap;
            for(int i = 0; i < days.size(); i++){
                for(int j = 1; j < days.size(); j++){
                    swap = days.get(j);
                    if(swap.getId().compareTo(days.get(j - 1).getId()) < 0){
                        days.set(j, days.get(j - 1));
                        days.set(j - 1, swap);
                    }
                }
            }

            timeBox.getChildren().clear();
            for(int i = 0; i < days.size(); i++){
                timeBox.getChildren().add(days.get(i));
            }

            centerBoxes.getChildren().add(timeBox);
        }
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
            String query = "SELECT * FROM tutors WHERE lasid IN ( SELECT lasid FROM timeslots WHERE ";
            for (int i = 0; i < timeSlots.size(); i++)
                query = appendTimeSlotToQuery(query, i);
            JavaToMySQL timeSlotOnlyQuery = new JavaToMySQL(query);
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
                "((timeslots.end >= " + timeSlots.get(0).getStartTime() + " AND " +
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
                225, 75, 30);
        buttonHolder.setPadding(new Insets(30));
        buttonHolder.setAlignment(Pos.BOTTOM_LEFT);
        buttonHolder.getChildren().add(homeButton);
        pane.setBottom(buttonHolder);
    }
}
