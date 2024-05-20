package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private List<Restaurant> restoList = new ArrayList<>();
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();

    Font primaryFont = MainApp.primaryFont;
    Font headerFont = MainApp.headerFont;
    Background bgStyle = MainApp.bgStyle;
    String buttonStyle = MainApp.buttonStyle;
    String fontColor = MainApp.fontColor;
    String textfieldStyle = MainApp.textfieldStyle;

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();

    }

    @Override
    public Scene createBaseMenu() {
        VBox menuLayout = new VBox(15);
        menuLayout.setBackground(bgStyle);

        Label header = new Label("Main Menu");
        header.setFont(headerFont);
        header.setStyle(fontColor);
        header.setPadding(new Insets(0,0,15,0));

        Button tambahResButton =  new Button("Add Restaurant");
        tambahResButton.setStyle(buttonStyle);
        tambahResButton.setMaxWidth(200);
        tambahResButton.setOnAction(e -> stage.setScene(createAddRestaurantForm()));

        Button tambahMenuResButton = new Button("Add Menu");
        tambahMenuResButton.setStyle(buttonStyle);
        tambahMenuResButton.setMaxWidth(200);
        tambahMenuResButton.setOnAction(e -> stage.setScene(createAddMenuForm()));

        Button lihatDaftarResButton = new Button("Restaurant List");
        lihatDaftarResButton.setStyle(buttonStyle);
        lihatDaftarResButton.setMaxWidth(200);
        lihatDaftarResButton.setOnAction(e -> stage.setScene(createViewRestaurantsForm()));

        Button logOutButton = new Button("Log Out");
        logOutButton.setStyle(buttonStyle);
        logOutButton.setMaxWidth(200);
        logOutButton.setOnAction(e -> mainApp.logout());

        menuLayout.getChildren().addAll(header, tambahResButton, tambahMenuResButton, lihatDaftarResButton, logOutButton);
        menuLayout.setAlignment(Pos.CENTER);

        return new Scene(menuLayout, 400, 400);
    }

    private Scene createAddRestaurantForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(bgStyle);

        Label header = new Label("Add Restaurant");
        header.setFont(headerFont);
        header.setStyle(fontColor);;

        HBox inputBox = new HBox(15);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(15,0,15,0));

        Label inputTitle = new Label("Restaurant");
        inputTitle.setStyle(fontColor);
        inputTitle.setFont(primaryFont);

        TextField restoName = new TextField();
        restoName.setFont(primaryFont);
        restoName.setStyle(textfieldStyle);;
        restoName.setMaxWidth(200);

        inputBox.getChildren().addAll(inputTitle, restoName);

        // Horizontal buttons
        HBox buttonsBox = new HBox(20); // Set spacing between buttons
        buttonsBox.setAlignment(Pos.CENTER); // Center align the buttons
        buttonsBox.setPadding(new Insets(0, 20, 0, 20)); // Padding left and right

        Button submitButton = new Button("Submit");
        submitButton.setStyle(buttonStyle);
        submitButton.setOnAction(e -> {handleTambahRestoran(restoName.getText());
        restoName.clear();});

        Button backButton = new Button("Back");
        backButton.setStyle(buttonStyle);
        backButton.setOnAction(e -> stage.setScene(scene));

        buttonsBox.getChildren().addAll(submitButton, backButton);

        layout.getChildren().addAll(header, inputBox, buttonsBox);

        return new Scene(layout, 500, 350);
    }

    private Scene createAddMenuForm() {
        VBox layout = new VBox(10);
        layout.setBackground(bgStyle);
        layout.setAlignment(Pos.CENTER);

        HBox bigHBox = new HBox(); 
        bigHBox.setAlignment(Pos.CENTER);

        // Center the header
        VBox headerBox = new VBox();
        headerBox.setAlignment(Pos.CENTER);
        Label header = new Label("Add Menu");
        header.setStyle(fontColor);
        header.setFont(headerFont);
        headerBox.getChildren().add(header);

        VBox inputTitleBox = new VBox();
        inputTitleBox.setSpacing(20);
        inputTitleBox.setPadding(new Insets(5, 20, 0, 0)); // Padding for the right side
        Label resto = new Label("Restaurant");
        resto.setFont(primaryFont);
        resto.setStyle(fontColor);
        Label menuItem = new Label("Menu Item");
        menuItem.setFont(primaryFont);
        menuItem.setStyle(fontColor);
        Label price = new Label("Price");
        price.setFont(primaryFont);
        price.setStyle(fontColor);
        // Piece All Together
        inputTitleBox.getChildren().addAll(resto, menuItem, price);

        VBox inputFieldBox = new VBox();
        inputFieldBox.setSpacing(10);
        inputFieldBox.setPadding(new Insets(0, 0, 0, 20)); // Padding for the left side
        TextField restoInput = new TextField();
        restoInput.setFont(primaryFont);
        restoInput.setStyle(textfieldStyle);
        TextField menuInput = new TextField();
        menuInput.setFont(primaryFont);
        menuInput.setStyle(textfieldStyle);
        TextField priceInput = new TextField();
        priceInput.setFont(primaryFont);
        priceInput.setStyle(textfieldStyle);
        inputFieldBox.getChildren().addAll(restoInput, menuInput, priceInput);

        bigHBox.setPadding(new Insets(10, 10, 10, 20));
        bigHBox.setSpacing(10);  
        bigHBox.getChildren().addAll(inputTitleBox, inputFieldBox); 

        HBox buttonsBox = new HBox(20);
        buttonsBox.setAlignment(Pos.CENTER); // Center align the buttons
        buttonsBox.setPadding(new Insets(5, 20, 0, 20)); // Padding left and right
        Button addMenuButton = new Button("Add Menu");
        addMenuButton.setStyle(buttonStyle);

        addMenuButton.setOnAction(e -> {
            String restoName = restoInput.getText();
            String menuName = menuInput.getText();
            String priceText = priceInput.getText();
        
            Restaurant validResto = DepeFood.findRestaurant(restoName);
            if (validResto == null) {
                // Display an error message if the restaurant is not found
                MainApp.displayErrorMessage("Invalid Restaurant", "The restaurant name provided is invalid.");
                return;
            }
        
            try {
                double doublePrice = Double.parseDouble(priceText);
                handleTambahMenuRestoran(validResto, menuName, doublePrice);
                
            } catch (NumberFormatException ex) {
                // Display an error message if the price is not a valid number
                MainApp.displayErrorMessage("Invalid Price", "Please enter a valid number for the price.");
            }
            
            // Clear the text fields
            restoInput.clear();
            menuInput.clear();
            priceInput.clear();
        });

        Button backButton = new Button("Back");
        backButton.setStyle(buttonStyle);
        backButton.setOnAction(e -> stage.setScene(scene));
        buttonsBox.getChildren().addAll(addMenuButton, backButton);

        layout.getChildren().addAll(headerBox, bigHBox, buttonsBox);


        return new Scene(layout, 550, 350);
    }
    
    
    private Scene createViewRestaurantsForm() {
        VBox layout = new VBox(15);
        layout.setBackground(bgStyle);
        layout.setAlignment(Pos.CENTER);

        Label header = new Label("View Restaurant");
        header.setFont(headerFont);
        header.setStyle(fontColor);

        Label titleLabel = new Label("Select a Restaurant");
        titleLabel.setFont(primaryFont);
        titleLabel.setStyle(fontColor);
        // Create the ComboBox and populate it with restaurant names
        restaurantComboBox = new ComboBox<>();
        restaurantComboBox.setStyle(buttonStyle);
        restaurantComboBox.setItems(FXCollections.observableArrayList(getRestaurantNames()));
        restaurantComboBox.setOnAction(e -> displayMenuItems());

        menuItemsListView = new ListView<>();
        menuItemsListView.setStyle(textfieldStyle);
        menuItemsListView.setMaxSize(350, 200);;
    
        Button backButton = new Button("Back");
        backButton.setStyle(buttonStyle);
        backButton.setOnAction(e -> stage.setScene(scene));
    
        layout.getChildren().addAll(header, titleLabel, restaurantComboBox, menuItemsListView, backButton);

        return new Scene(layout, 400, 455);
    }

    private ObservableList<String> getRestaurantNames() {
        ObservableList<String> names = FXCollections.observableArrayList();
        for (Restaurant restaurant : DepeFood.getRestoList()) {
            names.add(restaurant.getNama());
        }
        return names;
    }
    private void displayMenuItems() {
        String selectedRestaurantName = restaurantComboBox.getValue();
        Restaurant validRestaurant = DepeFood.getRestaurantByName(selectedRestaurantName);

        if (validRestaurant != null) {
            ObservableList<String> menuItems = FXCollections.observableArrayList();
            for (Menu item : validRestaurant.getMenu()) {
                menuItems.add(item.getNamaMakanan() + " - " + item.getHarga());
            }
            menuItemsListView.setItems(menuItems);
        } else {
            MainApp.displayErrorMessage("Restaurant not found", "The selected restaurant does not exist.");
        }
    }

    
    private void handleTambahRestoran(String nama) {
        String validName = DepeFood.getValidRestaurantName(nama);
        if (validName != null) {
            DepeFood.handleTambahRestoran(nama);
            MainApp.showSuccessMessage("Register Successful", "Restaurant successfully registered!");
            refreshRestaurantComboBox();
        } else {
            MainApp.displayErrorMessage("Register Failed", "Invalid restaurant name, restaurant failed to register!");
        }
    }

    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
        if (restaurant != null) {
            DepeFood.handleTambahMenuRestoran(restaurant, itemName, price);
            MainApp.showSuccessMessage("Add Menu Successful", "Menu item added successfully!");
        } else {
            MainApp.displayErrorMessage("Add Menu Failed", "Restaurant not found!");
        }
    }

    private void refreshRestaurantComboBox() {
        restaurantComboBox.setItems(FXCollections.observableArrayList(getRestaurantNames()));
    }
}
