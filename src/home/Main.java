package home;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


public class Main extends Application {

    private Scene scene1;
    private Scene scene2;
    private BorderPane startPagePane;
    private BorderPane tutorSelectionPagePane;
    private BorderPane studentInquiryPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        createHomeScreen(primaryStage);
        scene2 = createTutorPage(primaryStage);
        scene1 = new Scene(startPagePane, 600, 500);
        primaryStage.setTitle("Peer Tutoring Home Page");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public void createHomeScreen(Stage stage){
        startPagePane = new BorderPane();
        HBox headerBox = createStartPageHeader();
        VBox middleButtonBox = createStartPageButtons(stage);
        startPagePane.setTop(headerBox);
        startPagePane.setCenter(middleButtonBox);
    }

    public Scene createTutorPage(Stage stage){
        tutorSelectionPagePane = new BorderPane();
        ComboBox tutorSelectComboBox = new ComboBox();
        tutorSelectComboBox.getItems().addAll("Kristina Wolinski", "Gati Aher", "Mike Winters");
        tutorSelectionPagePane.setCenter(tutorSelectComboBox);
        HBox headerBox = createTutorSelectionHeader();
        HBox buttonBox = createTutorSelectionButtons(stage, tutorSelectComboBox);
        tutorSelectionPagePane.setTop(headerBox);
        tutorSelectionPagePane.setBottom(buttonBox);
        Scene tutorPage = new Scene(tutorSelectionPagePane, 600, 500);
        return tutorPage;
    }

    public void createStudentPage(Stage stage){
        studentInquiryPane = new BorderPane();
        GridPane gridPane = createStudentGrid();
        studentInquiryPane.setCenter(gridPane);

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
                switchPages(stage, scene2);
            }
        });
        vBox.getChildren().addAll(studentBtn, tutorBtn);
        return vBox;
    }

    public HBox createStartPageHeader(){
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

    public HBox createTutorSelectionHeader(){
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(30));
        hBox.setStyle("-fx-background-color: deepskyblue;");
        Text title = new Text("Tutors");
        title.setFill(Color.FLORALWHITE);
        title.setFont(Font.font("Constantia", FontWeight.SEMI_BOLD, 36.0));
        hBox.getChildren().add(title);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public HBox createTutorSelectionButtons(Stage stage, ComboBox comboBox){
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(30));
        hBox.setSpacing(240);
        Button backBtn = new Button("Back");
        backBtn.setPrefSize(150, 50);
        backBtn.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        backBtn.setTextFill(Color.FLORALWHITE);
        backBtn.setStyle("-fx-background-color: indianred");
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switchPages(stage, scene1);
                System.out.println("Send to Start Page");
            }
        });
        Button continueBtn = new Button("Continue");
        continueBtn.setPrefSize(150, 50);
        continueBtn.setFont(Font.font("Constantia", FontWeight.NORMAL, 20));
        continueBtn.setTextFill(Color.FLORALWHITE);
        continueBtn.setStyle("-fx-background-color: deepskyblue");
        continueBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String tutorName = (String) comboBox.getValue();
                if (tutorName != null)
                    System.out.println("Send to Tutor Scheduling Page. Name: " + tutorName);
                else
                    System.out.println("Please select a name before proceeding.");
            }
        });
        hBox.getChildren().addAll(backBtn, continueBtn);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public GridPane createStudentGrid(){
        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(20);

        return grid;
    }

    public void switchPages(Stage stage, Scene scene){
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
