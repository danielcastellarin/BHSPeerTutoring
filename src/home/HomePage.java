package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 */
public class HomePage extends Page {

    private EventHandler<ActionEvent> goStudent;
    private EventHandler<ActionEvent> goTutor;

    public static StudentPage studentPage;
    public static TutorLogin tutorLogin;


    /**
     * This is the constructor for HomePage, which is called only once from Main.
     * HomePage extends Page, which is the base class for all Pages in this program.
     * Generic scene setup is done in Page.
     *
     * *** I highly suggest going to Page to learn the setup for each page ***
     *
     * HomePage is unique in that its navigation buttons are in the center of the Page,
     * so its initialization is quite simple.
     *
     * @param stage the stage that is used for the rest of the program
     *              and is passed along to each new page.
     * @see Page
     */
    public HomePage(Stage stage){
        createHeader("-fx-background-color: firebrick", "Peer Tutoring Portal", Color.FLORALWHITE);
        createButtonEvents(stage);
        createCenter();
        createScene();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is where the events for each button on HomePage is created.
     * goStudent creates the StudentPage when the Student button is pressed.
     * goTutor creates the TutorLogin page when the Tutor button is pressed.
     * These buttons are both created in createCenter.
     *
     * @param stage used to pass on the stage to the next page
     *              This is so that the same stage (window) can be used by each page
     *              instead of having to create new stages for each page.
     */
    private void createButtonEvents(Stage stage){
        goStudent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                studentPage = new StudentPage(stage);
            }
        };
        goTutor = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tutorLogin = new TutorLogin(stage);
            }
        };
    }

    /**
     * createCenter sets up the center of this page
     * VBox is a type of layout, where all of its nodes are arranged in a vertical column.
     * Nodes are features on the page. Examples are text, buttons, hyperlinks, etc.
     * The color, positioning, and spacing of nodes is set at the beginning.
     * Both buttons are created through methods defined in Page.
     * centerBox then adds the buttons to its list of nodes.
     * centerBox then set to the center of the page.
     */
    private void createCenter(){
        VBox centerBox = new VBox();
        centerBox.setStyle("-fx-background-color: silver");
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(60);
        Button studentBtn = createButton("Student", "-fx-background-color: darkolivegreen", goStudent,
                275, 100, 36);
        Button tutorBtn = createButton("Tutor", "-fx-background-color: deepskyblue", goTutor,
                275, 100, 36);
        centerBox.getChildren().addAll(studentBtn, tutorBtn);
        pane.setCenter(centerBox);
    }
}
