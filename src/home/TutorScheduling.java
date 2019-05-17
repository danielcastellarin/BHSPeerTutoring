package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TutorScheduling extends Page{

    GridPane calendar;
    HBox header;
    HBox buttons;
    VBox calendarBox;
    String tutorName;
    EventHandler<ActionEvent> schedulePopUp;
    EventHandler<ActionEvent> backFunc;
    EventHandler<ActionEvent> advFunc;

    public static TimeSelectPopUp timeSelectPopUp;

    public TutorScheduling(Stage stage){
        pane = new BorderPane();
        tutorName = Main.tutorLogin.getComboBoxString();
        createHeader();
        createButtonEvents(stage);
        createCalendarBox();
        createTutorSchedulingButtons();
        createScene();
    }

    private void createHeader(){
        header = new HBox();
        header.setPadding(new Insets(30));
        title = new Text(tutorName);
        title.setFill(Color.FLORALWHITE);
        title.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 36.0));
        header.setStyle("-fx-background-color: deepskyblue");
        header.getChildren().add(title);
        header.setAlignment(Pos.CENTER);
        pane.setTop(header);
    }

    public void setHeaderText(String name){
        tutorName = name;
        title.setText(name);
    }

    private void createCalendarBox(){
        calendarBox = new VBox();
        calendarBox.setSpacing(10);
        calendarBox.setPadding(new Insets(20));
        calendarBox.setAlignment(Pos.CENTER);
        Text calendarLabel = new Text("Edit Availability:");
        calendarLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        calendarLabel.setFill(Color.DEEPSKYBLUE);
        calendar = new GridPane();
        calendar.setHgap(5);
        calendar.setVgap(5);
//        calendar.setStyle("-fx-background-color: deepskyblue");

        Button days[] = new Button[7];
        for(int i = 0; i < 7; i++){
            switch (i){
                case 0:
                    days[i] = new Button("Sunday");
                    break;
                case 1:
                    days[i] = new Button("Monday");
                    break;
                case 2:
                    days[i] = new Button("Tuesday");
                    break;
                case 3:
                    days[i]= new Button("Wednesday");
                    break;
                case 4:
                    days[i] = new Button("Thursday");
                    break;
                case 5:
                    days[i] = new Button("Friday");
                    break;
                case 6:
                    days[i] = new Button("Saturday");
                    break;
                default:
                    break;
            }
            days[i].setPrefSize(100, 100); //width, height
            days[i].setOnAction(schedulePopUp);
            calendar.add(days[i], i, 1);
        }
        calendarBox.getChildren().addAll(calendarLabel, calendar);
        pane.setCenter(calendarBox);
    }

    private void createButtonEvents(Stage stage){
        schedulePopUp = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.print("Pop up");
                timeSelectPopUp = new TimeSelectPopUp();
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
