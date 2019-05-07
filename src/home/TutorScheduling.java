package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TutorScheduling extends Page{

    private Scene scene;
    BorderPane pane;
    FlowPane calendar;
    HBox header;
    HBox buttons;
    VBox calendarBox;
    String tutorName;
    EventHandler<ActionEvent> schedulePopUp;
    EventHandler<ActionEvent> backFunc;
    EventHandler<ActionEvent> advFunc;

    public TutorScheduling(Stage stage){
        pane = new BorderPane();
//        tutorName = name;
        createHeader();
        createCalendarBox();
        schedulePopUp = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

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

    private void createCalendarBox(){
        calendarBox = new VBox();
        calendarBox.setSpacing(10);
        Text calendarLabel = new Text("Edit Availability:");
        calendarLabel.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        calendarLabel.setFill(Color.DEEPSKYBLUE);
        calendar = new FlowPane();
        calendar.setHgap(5);
        calendar.setVgap(5);
        calendar.setPrefWrapLength(200);
        calendar.setRowValignment(VPos.TOP);
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
                days[i][j].setPrefSize(100, 100);
                calendar.getChildren().add(days[i][j]);
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

    public Scene getScene(){
        return scene;
    }
}
