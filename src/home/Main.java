package home;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static HomePage homePage;

    /**
     * First code that is run in the program. "Launches" the program
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Inside start, the stage is given a name.
     *
     * A stage is a wiindow. Examples of windows are Chrome, Microsoft Word, etc.
     *
     * This is also where the home page is created.
     *
     * @param primaryStage this is the stage that is used for the rest of the program
     *                     and is passed along to each page that is created.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Peer Tutoring Portal");
        homePage = new HomePage(primaryStage);
    }
}
