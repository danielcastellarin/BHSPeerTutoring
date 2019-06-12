package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TutorProfilePopUp extends Page{

    Stage popUpWindow;
    EventHandler<ActionEvent> goBack;
    GridPane grid;

    // [ lasid , first_name , last_name , phone_number ]        eventually add personal email, extra description and photo reference
    ArrayList<String> profile;

    public TutorProfilePopUp(ArrayList<String> list){
        popUpWindow = createStage();
        profile = list;
        createButtonEvent();
        createPopUpHeader("-fx-background-color: deepskyblue;",
                "Tutor Profile", Color.FLORALWHITE);
        displayProfile();

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

    private void createButtonEvent(){
        goBack = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Action cancelled");
                popUpWindow.close();
            }
        };
    }

    private void displayProfile(){
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Text nameLabel = new Text("Name: ");
        grid.add(nameLabel, 2, 1);

        Text name = new Text(profile.get(1) + " " + profile.get(2));
        grid.add(name, 2, 2);

        Text emailLabel = new Text("Email: ");
        grid.add(emailLabel, 2, 3);

        Text email = new Text(profile.get(0));
        grid.add(email, 2, 4);

        Text phoneLabel = new Text("Phone Number: ");
        grid.add(phoneLabel, 2, 5);

        Text phoneNumber = new Text(profile.get(3));
        grid.add(phoneNumber, 2, 6);

        grid.add(createButton("Back", "-fx-background-color: indianred",
                goBack, 150, 50, 20), 1, 7);

        pane.setCenter(grid);
    }
}
