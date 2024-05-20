package assignments.assignment4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat.Style;
import java.util.HashMap;
import java.util.Map;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import assignments.assignment4.components.form.LoginForm;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage window;
    private Map<String, Scene> allScenes = new HashMap<>();
    private Scene currentScene;
    private static User user;
    private Scene previousScene; // Track the previous scene

    public static Font primaryFont = Font.font("Century Gothic", 15);
    public static Font boldPrimary = Font.font("Century Gothic", FontWeight.BOLD, 20);
    public static Font bold = Font.font("Century Gothic", FontWeight.BOLD, 15);
    public static Font headerFont = Font.font("Lucida Calligraphy", FontWeight.BOLD, 25);
    public static String fontColor = "-fx-text-fill: #595758;";
    
    public static final Background bgStyle = new Background(new BackgroundFill(Color.web("#FFEEF2"), null, null));
    public static final String buttonStyle = fontColor +
        "-fx-background-color: linear-gradient(#FFE4F3, #FFD7EB); " +
        "-fx-border-color: #FFE4F3; " +
        "-fx-border-radius: 8px; " +
        "-fx-background-radius: 8px; " +
        "-fx-font-size: 13px; " +
        "-fx-font-weight: bold;" +
        "-fx-font-family: 'Century Gothic';";

    public static final String textfieldStyle = fontColor +
    "-fx-background-color: linear-gradient(#F8F7FF, #FFFFFF);" +
    "-fx-border-color: #FFE4F3;" +
    "-fx-border-radius: 8px;" +
    "-fx-background-radius: 8px;" +
    "-fx-font-size: 13px;" +
    "-fx-font-weight: bold;" +
    "-fx-font-family: 'Century Gothic';" +
    "-fx-prompt-text-fill: #B0B0B0;";


    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("DepeFood Ordering System");
        DepeFood.initUser(); // Initialize users

        // Initialize all scenes
        Scene loginScene = new LoginForm(window, this).getScene();

        // Populate all scenes map
        allScenes.put("Login", loginScene);

        // Set the initial scene of the application to the login scene
        setScene(loginScene);
        window.show();
    }

    public void setUser(User newUser) {
        user = newUser;
    }

    // Method to set a scene
    public void setScene(Scene scene) {
        window.setScene(scene);
        currentScene = scene;
    }

    // Method to get a scene by name
    public Scene getScene(String sceneName) {
        return allScenes.get(sceneName);
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void addScene(String sceneName, Scene scene){
        allScenes.put(sceneName, scene);
    }

    public void logout() {
        setUser(null); // Clear the current user
        setScene(getScene("Login")); // Switch to the login scene
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showSuccessMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void displayErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    
        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }
}
