package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition; // Import for shakeNode

public class LoginPage extends Application {

    private Stage primaryStage;
    private static final String CORRECT_PASSWORD = "owo";

    private ImageView backgroundImageView;
    private ImageView profileImageView;
    private Label welcomeLabel;
    private Label errLabel;
    private PasswordField pField;
    private Button btnLogin;
    private HBox inputHBox;
    private VBox contentVBox;
    private StackPane rootLoginPane;

    private double initialSceneWidth = 1024;
    private double initialSceneHeight = 768;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Login Page");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        showLoginScreen();

        primaryStage.show();

        Scene scene = primaryStage.getScene();
        if (scene != null) {
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.F11) {
                    primaryStage.setFullScreen(!primaryStage.isFullScreen());
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    if (primaryStage.isFullScreen()) {
                        primaryStage.setFullScreen(false);
                    }
                }
            });
        }

        primaryStage.fullScreenProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Fullscreen mode: " + (newVal ? "On" : "Off"));
        });
    }

    private void showLoginScreen() {
        rootLoginPane = new StackPane();

        backgroundImageView = new ImageView();
        profileImageView = new ImageView();
        welcomeLabel = new Label("Welcome RU24-2I !");
        errLabel = new Label("Wrong password!");
        pField = new PasswordField();
        btnLogin = new Button("Login");
        inputHBox = new HBox(10);
        contentVBox = new VBox(30);

        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/resources/nature.jpg"));
            backgroundImageView.setImage(bgImage);
            backgroundImageView.fitWidthProperty().bind(rootLoginPane.widthProperty());
            backgroundImageView.fitHeightProperty().bind(rootLoginPane.heightProperty());
            backgroundImageView.setPreserveRatio(false);

            Image profileImg = new Image(getClass().getResourceAsStream("/resources/default-profile-pic.png"), 150, 150, true, true);
            profileImageView.setImage(profileImg);
            Circle profileClip = new Circle(profileImageView.getFitWidth() / 2, profileImageView.getFitHeight() / 2, profileImageView.getFitWidth() / 2);
            profileImageView.setClip(profileClip);

        } catch (Exception e) {
            System.err.println("Error loading images. Make sure 'nature.jpg' and 'default-profile-pic.png' are in src/resources/");
            System.err.println(e.getMessage());
            rootLoginPane.setStyle("-fx-background-color: lightblue;");
        }

        Circle profileBgCircle = new Circle(85);
        profileBgCircle.setFill(Color.rgb(128, 128, 128, 0.9));
        profileBgCircle.setStroke(Color.TRANSPARENT);
        StackPane profileStack = new StackPane(profileBgCircle, profileImageView);
        profileStack.setAlignment(Pos.CENTER);

        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
        welcomeLabel.setTextFill(Color.WHITE);
        welcomeLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 10, 0, 0, 3);");

        pField.setPromptText("Password");
        pField.setMinHeight(50);
        pField.setFont(Font.font("System", FontWeight.NORMAL, 20));
        pField.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 25;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: rgba(255, 255, 255, 0.7);" +
                        "-fx-padding: 5 15 5 15;"
        );

        btnLogin.setMinHeight(50);
        btnLogin.setFont(Font.font("System", FontWeight.BOLD, 20));
        String commonLoginBtnStyle =
                "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 25;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 5 25 5 25;";
        btnLogin.setStyle("-fx-background-color: transparent;" + commonLoginBtnStyle);
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);" + commonLoginBtnStyle));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle("-fx-background-color: transparent;" + commonLoginBtnStyle));

        inputHBox.setAlignment(Pos.CENTER);
        inputHBox.getChildren().addAll(pField, btnLogin);
        HBox.setHgrow(pField, Priority.ALWAYS);
        inputHBox.setMaxWidth(600);

        errLabel.setTextFill(Color.RED);
        errLabel.setFont(Font.font("System", FontWeight.BOLD, 22));
        errLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 8, 0, 0, 2);");
        errLabel.setVisible(false);

        contentVBox.setAlignment(Pos.CENTER);
        contentVBox.setPadding(new Insets(40));
        contentVBox.setMaxWidth(800);
        contentVBox.getChildren().addAll(profileStack, welcomeLabel, inputHBox, errLabel);

        rootLoginPane.getChildren().addAll(backgroundImageView, contentVBox);
        StackPane.setAlignment(contentVBox, Pos.CENTER);

        btnLogin.setOnAction(e -> {
            if (!pField.getText().trim().equals(CORRECT_PASSWORD)) {
                errLabel.setVisible(true);
                pField.requestFocus();
                pField.selectAll();
                shakeNode(pField);
            } else {
                errLabel.setVisible(false);
                System.out.println("Login successful!");
                showHomePage();
            }
        });

        Scene loginScene = new Scene(rootLoginPane, initialSceneWidth, initialSceneHeight);
        primaryStage.setScene(loginScene);
    }

    private void showHomePage() {
        HomePage homePage = new HomePage(primaryStage); // Create an instance of HomePage
        primaryStage.setScene(homePage.getScene()); // Set the scene from HomePage
        primaryStage.setTitle("Home Page");
    }

    private void shakeNode(javafx.scene.Node node) {
        TranslateTransition tt = new TranslateTransition(javafx.util.Duration.millis(50), node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(4);
        tt.setAutoReverse(true);
        tt.setOnFinished(event -> node.setTranslateX(0));
        tt.playFromStart();
    }

    public static void main(String[] args) {
        launch(args);
    }
}