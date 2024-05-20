package assignments.assignment4.components.form;

import assignments.assignment2.MainMenu;
import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
import assignments.assignment4.page.MemberMenu;

import java.util.function.Consumer;

public class LoginForm {
    private static User userLoggedIn;
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    Font primaryFont = MainApp.primaryFont;
    Font headerFont = MainApp.headerFont;
    Background bgStyle = MainApp.bgStyle;
    String buttonStyle = MainApp.buttonStyle;
    String fontColor = MainApp.fontColor;
    String textfieldStyle = MainApp.textfieldStyle;

    Button loginButton;


    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
        this.nameInput = new TextField(); // Initialize nameInput
        this.phoneInput = new TextField(); // Initialize phoneInput
        this.loginButton = new Button("Login");

        // Set action for login button
        loginButton.setOnAction(e -> {handleLogin(); nameInput.clear(); phoneInput.clear();});

    }

    private Scene createLoginForm() {
        GridPane grid = new GridPane();
        grid.setBackground(bgStyle);;

        VBox fullBox = new VBox();
        fullBox.setSpacing(20);
        fullBox.setPadding(new Insets(20)); // Padding inside fullBox

        HBox bigHBox = new HBox(); 

        // Center the header
        VBox headerBox = new VBox();
        headerBox.setAlignment(Pos.CENTER);
        Label header = new Label("Welcome to DepeFood!");
        header.setStyle(fontColor); // Set font color to white
        header.setFont(headerFont);
        headerBox.getChildren().add(header);

        VBox inputTitleBox = new VBox();
        inputTitleBox.setSpacing(15);
        inputTitleBox.setPadding(new Insets(8, 10, 0, 0)); // Padding for the right side
        Label userName = new Label("Name");
        userName.setFont(primaryFont);
        userName.setStyle(fontColor);; // Set font color to white
        Label phoneNumber = new Label("Phone Number");
        phoneNumber.setFont(primaryFont);
        phoneNumber.setStyle(fontColor); // Set font color to white
        inputTitleBox.getChildren().addAll(userName, phoneNumber);

        VBox inputFieldBox = new VBox();
        inputFieldBox.setSpacing(10);
        inputFieldBox.setPadding(new Insets(0, 0, 0, 10)); // Padding for the left side
        nameInput.setFont(primaryFont);
        nameInput.setStyle(textfieldStyle);
        HBox phoneHbox = new HBox();
        phoneInput.setFont(primaryFont);
        phoneInput.setStyle(textfieldStyle);
        phoneHbox.setSpacing(13);

        inputFieldBox.getChildren().addAll(nameInput, phoneInput);

        HBox buttonsBox = new HBox();
        buttonsBox.setAlignment(Pos.CENTER);
        loginButton.setStyle(buttonStyle);
        loginButton.setMaxSize(90, 40);
        buttonsBox.getChildren().addAll(loginButton);

        bigHBox.setPadding(new Insets(10, 10, 10, 10));
        bigHBox.setSpacing(10);  
        bigHBox.getChildren().addAll(inputTitleBox, inputFieldBox); 

        fullBox.getChildren().addAll(headerBox, bigHBox, buttonsBox);

        // Center fullBox in the grid
        grid.setAlignment(Pos.CENTER);
        grid.add(fullBox, 0, 0);
        
        GridPane.setMargin(fullBox, new Insets(20)); // Margin around fullBox

        return new Scene(grid, 550, 350);
    }


    private void handleLogin() {
        String name = nameInput.getText();
        String noTelp = phoneInput.getText();
        // MemberMenu openMenu;
        userLoggedIn = DepeFood.getUser(name, noTelp);

        DepeFood.setPenggunaLoggedIn(userLoggedIn);
        if(userLoggedIn == null){
            // Display error message pop-up window
            displayErrorMessage("Login Failed", "Invalid username or password. Please try again.");
        } else {
            if ((userLoggedIn.role).equals("Admin")){
                AdminMenu openMenu = new AdminMenu(stage, mainApp, userLoggedIn);
                mainApp.setScene(openMenu.createBaseMenu());
            } else {
                CustomerMenu openMenu = new CustomerMenu(stage, mainApp, userLoggedIn);
                mainApp.setScene(openMenu.createBaseMenu());
            }
        }
    }


    private void displayErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    
        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }

    public static User getUserLoggedIn() {
        return userLoggedIn;
    }

    public Scene getScene(){
        return this.createLoginForm();
    }

}
