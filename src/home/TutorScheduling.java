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

    public TutorScheduling(Stage stage){
        pane = new BorderPane();
        tutorName = Main.tutorLogin.getComboBoxString();
        createHeader();
        createCalendarBox();
        schedulePopUp = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        };
        advFunc = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.switchPages(stage, Main.tutorDone.getScene());
                System.out.println("Send to Tutor Done Page.");
                System.out.println("Update " + tutorName + "'s info in database" + tutorName);
            }
        };
        backFunc = createSceneChangeEvent(stage, Main.tutorLogin.getScene());
        createTutorSchedulingButtons();
        scene = new Scene(pane, 600, 500);
    }

    private void createHeader(){
        header = new HBox();
        header.setPadding(new Insets(30));
        header.setStyle("-fx-background-color: deepbluesky");
        Text title = new Text(tutorName);
        title.setFill(Color.FLORALWHITE);
        title.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 36.0));
        header.getChildren().add(title);
        header.setAlignment(Pos.CENTER);
        pane.setTop(header);
    }

    public void setHeaderText(String name){
//        Title title = new Text
    }

    private void createCalendarBox(){
        calendarBox = new VBox();
        calendarBox.setSpacing(10);
        calendarBox.setPadding(new Insets(50));
        calendarBox.setAlignment(Pos.CENTER);
        Text calendarLabel = new Text("Edit Availability:");
        calendarLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        calendarLabel.setFill(Color.DEEPSKYBLUE);
        calendar = new GridPane();
        calendar.setHgap(5);
        calendar.setVgap(5);
        calendar.setStyle("-fx-background-color: deepbluesky");
        Button days[][] = new Button[2][7];
        for(int i = 0; i<2; i++){
            for(int j = 0; j<7; j++){
                switch (j){
                    case 0:
                        days[i][j] = new Button("Sunday");
                        break;
                    case 1:
                        days[i][j] = new Button("Monday");
                        break;
                    case 2:
                        days[i][j] = new Button("Tuesday");
                        break;
                    case 3:
                        days[i][j] = new Button("Wednesday");
                        break;
                    case 4:
                        days[i][j] = new Button("Thursday");
                        break;
                    case 5:
                        days[i][j] = new Button("Friday");
                        break;
                    case 6:
                        days[i][j] = new Button("Saturday");
                        break;
                    default:
                            break;
                }
                days[i][j].setPrefSize(80, 80);
                calendar.add(days[i][j], j+1, i+1);
            }
        }
        calendarBox.getChildren().addAll(calendarLabel, calendar);
        pane.setCenter(calendarBox);
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
