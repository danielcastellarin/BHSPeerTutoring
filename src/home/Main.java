package home;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


public class Main extends Application {

    public static Scene homePage;
    public static TutorLogin tutorLogin;
    public static TutorScheduling tutorScheduling;
    public static StudentPage studentPage;
    private BorderPane startPagePane;
    private BorderPane tutorSelectionPagePane;
    private BorderPane studentInquiryPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        createHomeScreen(primaryStage);
        tutorLogin = new TutorLogin(primaryStage);
        studentPage = new StudentPage(primaryStage);
        tutorScheduling = new TutorScheduling(primaryStage);
        homePage = new Scene(startPagePane, 600, 500);
        primaryStage.setTitle("Peer Tutoring Home Page");
        primaryStage.setScene(homePage);
        primaryStage.show();
    }

    public void createHomeScreen(Stage stage){
        startPagePane = new BorderPane();
        HBox headerBox = createHomePageHeader();
        VBox middleButtonBox = createStartPageButtons(stage);
        startPagePane.setTop(headerBox);
        startPagePane.setCenter(middleButtonBox);
    }

    public VBox createStartPageButtons(Stage stage){
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: silver");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        Shadow shadow = new Shadow();
        Button studentBtn = new Button("Student");
        studentBtn.setPrefSize(200, 75);
        studentBtn.setFont(Font.font("Constantia", FontWeight.NORMAL, 24));
        studentBtn.setTextFill(Color.FLORALWHITE);
        studentBtn.setStyle("-fx-background-color: darkolivegreen");
        studentBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Send to Student Input Page");
                switchPages(stage, studentPage.getScene());
            }
        });
        Button tutorBtn = new Button("Tutor");
        tutorBtn.setPrefSize(200, 75);
        tutorBtn.setFont(Font.font("Constantia", FontWeight.NORMAL, 24));
        tutorBtn.setTextFill(Color.FLORALWHITE);
        tutorBtn.setStyle("-fx-background-color: deepskyblue");
        tutorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Send to Tutor Picker Page");
                switchPages(stage, tutorLogin.getScene());
            }
        });
        vBox.getChildren().addAll(studentBtn, tutorBtn);
        return vBox;
    }

    public HBox createHomePageHeader(){
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(30));
        hBox.setStyle("-fx-background-color: firebrick;");
        Text title = new Text("Peer Tutoring Portal");
        title.setFill(Color.FLORALWHITE);
        title.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 36.0));
        hBox.getChildren().add(title);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public static void switchPages(Stage stage, Scene scene){
        stage.setScene(scene);
        stage.show();
    }

    public static Scene getHomePage(){
        return homePage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
