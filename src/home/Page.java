package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Page {

    protected Scene scene;
    BorderPane pane = new BorderPane();
    HBox header;
    AnchorPane navBtns;
    Text title;

    public void createHeader(String headerColor, String headerText, Color textColor){
        header = new HBox();
        header.setPadding(new Insets(30));
        header.setStyle(headerColor);
        title = new Text(headerText);
        title.setFill(textColor);
        title.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 60.0));
        header.getChildren().add(title);
        header.setAlignment(Pos.CENTER);
        pane.setTop(header);
    }

    public void createPopUpHeader(String headerColor, String headerText, Color textColor){
        header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle(headerColor);
        title = new Text(headerText);
        title.setFill(textColor);
        title.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 36.0));
        header.getChildren().add(title);
        header.setAlignment(Pos.CENTER);
        pane.setTop(header);
    }

    public EventHandler<ActionEvent> createSceneChangeEvent (Stage stage, Scene nextScene){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(nextScene);
            }
        };
        return event;
    }

    public Button createButton(String name, String btnColor, EventHandler<ActionEvent> action,
                               double width, double length, double fontSize){
        Button button = new Button(name);
        button.setPrefSize(width, length);
        button.setFont(Font.font("Constantia", FontWeight.NORMAL, fontSize));
        button.setTextFill(Color.FLORALWHITE);
        button.setStyle(btnColor);
        button.setOnAction(action);
        return button;
    }

    public void createNavButtonBox(String advBtnColor, EventHandler<ActionEvent> backFunc,
                                   EventHandler<ActionEvent> advFunc, String advBtnText){
        navBtns = new AnchorPane();
        Button backBtn = createButton("Back", "-fx-background-color: indianred", backFunc,
                225, 75, 30);
        Button advBtn = createButton(advBtnText, advBtnColor, advFunc, 250, 75, 30);
        AnchorPane.setBottomAnchor(backBtn, 40.0);
        AnchorPane.setLeftAnchor(backBtn, 40.0);
        AnchorPane.setBottomAnchor(advBtn, 40.0);
        AnchorPane.setRightAnchor(advBtn, 40.0);
        navBtns.getChildren().addAll(backBtn, advBtn);
        pane.setBottom(navBtns);
    }

    public void createScene(){
        scene = new Scene(pane, 1000, 800);
    }

    public Scene getScene(){
        return scene;
    }



    /*
    Each interval is 15 minutes
    Slider increments by 25
    minute conversion --> og * 3/5

    divide by hundred to get rid of last 2 digits
    analyze to determine morning/evening

     */
    public String numToTimeConvert(int num){
        String time;
        boolean isEvening = false;

        if (num >= 1200 && num != 2400)
            isEvening = true;

        int hours = num / 100;
        int minutes = (num - (hours * 100)) * 3/5;

        if(hours > 12){
            hours -= 12;
        }else if (hours == 0){
            hours = 12;
        }

        if(minutes == 0){
            if (isEvening){
                time = hours + ":" + minutes + "0pm";
            }else{
                time = hours + ":" + minutes + "0am";
            }
        }else{
            if (isEvening){
                time = hours + ":" + minutes + "pm";
            }else{
                time = hours + ":" + minutes + "am";
            }
        }

        return time;
    }

}
