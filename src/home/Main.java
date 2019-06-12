package home;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static HomePage homePage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Peer Tutoring Portal");
        homePage = new HomePage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
