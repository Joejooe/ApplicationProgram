package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class HomePage {

    private Stage primaryStage;
    private Scene homeScene;
    private StackPane rootHomePane;
    private GridPane shortcutGrid;
    private Map<String, VBox> shortcuts = new HashMap<>(); // To store and manage shortcuts

    public HomePage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeHomePage();
    }

    private void initializeHomePage() {
        rootHomePane = new StackPane();

        // Background Image
        ImageView backgroundImageView = new ImageView();
        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/resources/homepage.jpg")); // Changed to homepage.jpg [cite: 14]
            backgroundImageView.setImage(bgImage);
            backgroundImageView.fitWidthProperty().bind(rootHomePane.widthProperty());
            backgroundImageView.fitHeightProperty().bind(rootHomePane.heightProperty());
            backgroundImageView.setPreserveRatio(false);
        } catch (Exception e) {
            System.err.println("Error loading home background image. Make sure 'homepage.jpg' is in src/resources/");
            backgroundImageView.setStyle("-fx-background-color: lightgray;"); // Fallback color
        }

        // Task Bar
        MenuBar taskBar = new MenuBar();
        taskBar.setMinHeight(50);
        taskBar.setStyle("-fx-background-color: black;"); // Black taskbar

        // Window Icon Menu
        Menu windowMenu = new Menu();
        ImageView windowIcon = new ImageView(new Image(getClass().getResourceAsStream("/resources/window-icon.png"))); // Uses window-icon.png [cite: 42]
        windowIcon.setFitHeight(30);
        windowIcon.setFitWidth(30);
        windowMenu.setGraphic(windowIcon);
      
        MenuItem logoutMenuItem = new MenuItem("Log out");
        ImageView logoutIcon = new ImageView(new Image(getClass().getResourceAsStream("/resources/logout3-icon.png")));
        logoutIcon.setFitHeight(20);
        logoutIcon.setFitWidth(20);
        logoutMenuItem.setGraphic(logoutIcon);
        // Menambahkan gaya CSS untuk logoutMenuItem
        logoutMenuItem.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        logoutMenuItem.setOnAction(e -> {
            System.out.println("Logging out...");
            LoginPage loginPage = new LoginPage();
            loginPage.start(primaryStage);
        });

        MenuItem shutdownMenuItem = new MenuItem("Shutdown");
        ImageView shutdownIcon = new ImageView(new Image(getClass().getResourceAsStream("/resources/shutdown4-icon.png")));
        shutdownIcon.setFitHeight(20);
        shutdownIcon.setFitWidth(20);
        shutdownMenuItem.setGraphic(shutdownIcon);
        // Menambahkan gaya CSS untuk shutdownMenuItem
        shutdownMenuItem.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        shutdownMenuItem.setOnAction(e -> {
            System.out.println("Shutting down...");
            primaryStage.close();
        });

        windowMenu.getItems().addAll(logoutMenuItem, shutdownMenuItem);
        
        // Notepad Icon Menu
        Menu notepadMenu = new Menu();
        ImageView notepadIcon = new ImageView(new Image(getClass().getResourceAsStream("/resources/notepad-icon.png"))); // Uses notepad-icon.png [cite: 45]
        notepadIcon.setFitHeight(30);
        notepadIcon.setFitWidth(30);
        notepadMenu.setGraphic(notepadIcon);
        notepadMenu.setOnAction(e -> {
            System.out.println("Opening Notepad application...");
            // TODO: Implement Notepad application instance
            // For now, just a placeholder
        });

        taskBar.getMenus().addAll(windowMenu, notepadMenu);
        taskBar.setUseSystemMenuBar(false);

        // Shortcuts Grid
        shortcutGrid = new GridPane();
        shortcutGrid.setVgap(20); // Vertical gap between shortcuts
        shortcutGrid.setHgap(20); // Horizontal gap between shortcuts
        shortcutGrid.setPadding(new Insets(20));
        shortcutGrid.setAlignment(Pos.TOP_LEFT);

        // Add statically defined shortcuts [cite: 38]
        addShortcut("Trash", "/resources/trash-icon.png"); // Uses trash-icon.png [cite: 34]
        addShortcut("Notepad", "/resources/notepad-icon.png"); // Uses notepad-icon.png [cite: 34]
        addShortcut("ChRUme", "/resources/chrome.png"); // Uses chrome.png [cite: 34]

        // Layout for the home page
        VBox homeLayout = new VBox();
        VBox.setVgrow(shortcutGrid, javafx.scene.layout.Priority.ALWAYS);
        homeLayout.getChildren().addAll(shortcutGrid, taskBar);

        rootHomePane.getChildren().addAll(backgroundImageView, homeLayout);
        StackPane.setAlignment(homeLayout, Pos.TOP_LEFT);

        homeScene = new Scene(rootHomePane, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
    }

    private void addShortcut(String name, String imagePath) {
        ImageView iconView = new ImageView();
        try {
            Image icon = new Image(getClass().getResourceAsStream(imagePath));
            iconView.setImage(icon);
            iconView.setFitWidth(64); // Standard icon size
            iconView.setFitHeight(64);
        } catch (Exception e) {
            System.err.println("Error loading shortcut image: " + imagePath);
            iconView.setFitWidth(64);
            iconView.setFitHeight(64);
            iconView.setStyle("-fx-background-color: darkgray;"); // Fallback
        }

        Label nameLabel = new Label(name); // Shortcut title/name [cite: 34]
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        nameLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 5, 0, 0, 1);");

        VBox shortcutVBox = new VBox(5, iconView, nameLabel);
        shortcutVBox.setAlignment(Pos.CENTER);
        shortcutVBox.setPadding(new Insets(5));
        shortcutVBox.setUserData(name); // Store the name for identification

        // Add click action for shortcuts (placeholder)
        shortcutVBox.setOnMouseClicked(event -> {
            String clickedShortcut = (String) shortcutVBox.getUserData();
            System.out.println("Opened " + clickedShortcut + " application.");
            // TODO: Implement opening specific applications [cite: 40]
            switch (clickedShortcut) {
                case "Notepad":
                    // Open Notepad App
                    break;
                case "ChRUme":
                    // Open ChRUme App
                    break;
                case "Trash":
                    // Open Trash (maybe just a message)
                    break;
            }
        });

        // Add to the grid, managing columns and rows [cite: 46]
        int maxShortcutsPerColumn = 5;
        int currentShortcuts = shortcuts.size();
        int column = currentShortcuts / maxShortcutsPerColumn;
        int row = currentShortcuts % maxShortcutsPerColumn;

        shortcutGrid.add(shortcutVBox, column, row);
        shortcuts.put(name, shortcutVBox);
    }

    public Scene getScene() {
        return homeScene;
    }
}