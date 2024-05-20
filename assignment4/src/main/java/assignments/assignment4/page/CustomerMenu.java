package assignments.assignment4.page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import assignments.assignment1.OrderGenerator;
import assignments.assignment3.DepeFood;
import assignments.assignment3.MainMenu;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();

    private static Label label = new Label();
    private MainApp mainApp;
    private List<Restaurant> restoList = new ArrayList<>();
    private User user;
    
    Font primaryFont = MainApp.primaryFont;
    Font headerFont = MainApp.headerFont;
    Background bgStyle = MainApp.bgStyle;
    String buttonStyle = MainApp.buttonStyle;
    String fontColor = MainApp.fontColor;
    String textfieldStyle = MainApp.textfieldStyle;

    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user, this.scene); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    @Override
    public Scene createBaseMenu() {
        VBox menuLayout = new VBox(15);
        menuLayout.setBackground(bgStyle);
        menuLayout.setAlignment(Pos.CENTER);

        Label header = new Label("Main Menu");
        header.setStyle(fontColor);
        header.setFont(headerFont);
        header.setPadding(new Insets(0,0,20,0));

        Button buatPesananButton =  new Button("Make an Order");
        buatPesananButton.setStyle(buttonStyle);
        buatPesananButton.setMaxWidth(200);
        buatPesananButton.setOnAction(e -> stage.setScene(createTambahPesananForm()));

        Button cetakBillButton = new Button("Print Bill");
        cetakBillButton.setStyle(buttonStyle);
        cetakBillButton.setMaxWidth(200);
        cetakBillButton.setOnAction(e -> stage.setScene(createBillPrinter()));

        Button bayarBillButton = new Button("Pay Bill");
        bayarBillButton.setStyle(buttonStyle);
        bayarBillButton.setMaxWidth(200);;
        bayarBillButton.setOnAction(e -> stage.setScene(createBayarBillForm()));

        Button cekSaldoButton = new Button("Check Balance");
        cekSaldoButton.setStyle(buttonStyle);
        cekSaldoButton.setMaxWidth(200);
        cekSaldoButton.setOnAction(e -> stage.setScene(createCekSaldoScene()));

        Button logOutButton = new Button("Log Out");
        logOutButton.setStyle(buttonStyle);
        logOutButton.setMaxWidth(200);
        logOutButton.setOnAction(e -> mainApp.logout());

        menuLayout.getChildren().addAll(header, buatPesananButton, cetakBillButton, bayarBillButton, cekSaldoButton, logOutButton);

        return new Scene(menuLayout, 400, 400);
    }

    private Scene createTambahPesananForm() {
        VBox layout = new VBox(15);
        layout.setBackground(bgStyle);
        layout.setAlignment(Pos.CENTER);

        Label header = new Label("Make an Order");
        header.setFont(headerFont);
        header.setStyle(fontColor);
        header.setPadding(new Insets(0,0,16,0));

        HBox bigBox = new HBox(15);
        bigBox.setAlignment(Pos.CENTER);

        VBox inpuTitleBox = new VBox(15);
        inpuTitleBox.setPadding(new Insets(0, 5, 0, 0));
        Label restoInputLabel = new Label("Select a Restaurant");
        restoInputLabel.setFont(primaryFont);
        restoInputLabel.setStyle(fontColor);
        Label dateInputLabel = new Label("Date");
        dateInputLabel.setFont(primaryFont);
        dateInputLabel.setStyle(fontColor);
        inpuTitleBox.getChildren().addAll(restoInputLabel, dateInputLabel);

        VBox inputFieldBox = new VBox(10);
        // Create the ComboBox and populate it with restaurant names
        restaurantComboBox = new ComboBox<>();
        restaurantComboBox.setStyle(buttonStyle);
        restaurantComboBox.setItems(FXCollections.observableArrayList(getRestaurantNames()));
        // restaurantComboBox.setOnAction(e -> displayMenuItems());

        menuItemsListView = new ListView<>();
        menuItemsListView.setStyle(textfieldStyle);
        menuItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        menuItemsListView.setMaxHeight(200);
        menuItemsListView.setMaxWidth(350);

        TextField dateField = new TextField();
        dateField.setPromptText("DD/MM/YYYY");
        dateField.setStyle(textfieldStyle);

        inputFieldBox.getChildren().addAll(restaurantComboBox, dateField);

        bigBox.getChildren().addAll(inpuTitleBox, inputFieldBox);

        Button menuButton = new Button("Menu");
        menuButton.setStyle(buttonStyle);
        menuButton.setOnAction(e -> handleShowMenu(dateField.getText()));
        
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(5,0,0,0));

        Button orderButton = new Button("Make Order");
        orderButton.setStyle(buttonStyle);
        orderButton.setOnAction(e -> {handleBuatPesanan(restaurantComboBox.getValue(), dateField.getText(), getSelectedMenuItems()); 
            restaurantComboBox.setValue(null);
            dateField.clear();});

        Button backButton = new Button("Back");
        backButton.setStyle(buttonStyle);
        backButton.setOnAction(e -> stage.setScene(scene));

        buttonsBox.getChildren().addAll(orderButton, backButton);
    
        layout.getChildren().addAll(header, bigBox, menuButton, menuItemsListView, buttonsBox);

        return new Scene(layout, 400, 500);
    }

    private List<String> getSelectedMenuItems() {
        return menuItemsListView.getSelectionModel().getSelectedItems();
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
                menuItems.add(item.getNamaMakanan());
            }
            menuItemsListView.setItems(menuItems);
        } else {
            MainApp.displayErrorMessage("Restaurant not found", "The selected restaurant does not exist.");
        }
    }


    private Scene createBillPrinter(){
        return billPrinter.getScene();
    }

    private Scene createBayarBillForm() {
        VBox menuLayout = new VBox(10);
        menuLayout.setBackground(bgStyle);
        menuLayout.setAlignment(Pos.CENTER);

        Label header = new Label("Pay Bill");
        header.setFont(headerFont);
        header.setStyle(fontColor);
        header.setPadding(new Insets(0,0,15,0));

        HBox bigBox = new HBox(10);
        bigBox.setAlignment(Pos.CENTER);

        VBox inputLabel = new VBox(10);
        inputLabel.setAlignment(Pos.CENTER_LEFT);
        Label inputOrder = new Label("Order ID");
        inputOrder.setFont(primaryFont);
        inputOrder.setStyle(fontColor);
        Label inputPayment = new Label("Payment Method");
        inputPayment.setFont(primaryFont);
        inputPayment.setStyle(fontColor);
        inputLabel.getChildren().addAll(inputOrder, inputPayment);

        VBox inputField = new VBox(10);
        inputField.setAlignment(Pos.CENTER);
        TextField orderId = new TextField();
        orderId.setStyle(textfieldStyle);
        orderId.setPromptText("Enter Order ID");
        orderId.setMaxWidth(200);
        // Create a ChoiceBox
        ChoiceBox<String> methods = new ChoiceBox<>();
        methods.setStyle(buttonStyle);
        methods.setMaxWidth(200);
        
        // Populate the ChoiceBox with options
        methods.setItems(FXCollections.observableArrayList(
            "Credit Card", "Debit"
        ));
                
        inputField.getChildren().addAll(orderId, methods);

        bigBox.getChildren().addAll(inputLabel, inputField);
        
        HBox buttonsBox = new HBox(15);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(8,0,0,0));
        Button payButton = new Button("Make Payment");
        payButton.setStyle(buttonStyle);
        payButton.setOnAction(e -> {handleBayarBill(orderId.getText(), methods.getValue());
        orderId.clear();});
        Button backButton = new Button("Back");
        backButton.setStyle(buttonStyle);
        backButton.setOnAction(e -> stage.setScene(scene));
        buttonsBox.getChildren().addAll(payButton, backButton);

        menuLayout.getChildren().addAll(header, bigBox, buttonsBox);
        return new Scene(menuLayout, 400,300);
    }


    private Scene createCekSaldoScene() {
        VBox menuLayout = new VBox(15);
        menuLayout.setBackground(bgStyle);
        menuLayout.setAlignment(Pos.CENTER);

        Label username = new Label(user.getNama());
        username.setStyle(fontColor);
        username.setFont(headerFont);

        double saldoValue = user.getSaldo();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedSaldo = decimalFormat.format(saldoValue);

        Label saldo = new Label("Rp" + formattedSaldo);
        saldo.setStyle(fontColor);
        saldo.setFont(primaryFont);

        Button backButton = new Button("Back");
        backButton.setStyle(buttonStyle);
        backButton.setOnAction(e -> stage.setScene(scene));

        menuLayout.getChildren().addAll(username, saldo, backButton);
        return new Scene(menuLayout, 400,350);
    }

    private void handleShowMenu(String date) {
        boolean isValid = OrderGenerator.validateDate(date);
        if (isValid) {
            displayMenuItems();
        } else {
            MainApp.displayErrorMessage("Invalid Input", "Date input is invalid, please enter the right date!");
        }
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        if (namaRestoran == null || namaRestoran.isEmpty()) {
            MainApp.displayErrorMessage("Invalid Restaurant", "Please select a restaurant.");
            return;
        }
        
        if (tanggalPemesanan == null || tanggalPemesanan.isEmpty()) {
            MainApp.displayErrorMessage("Invalid Date", "Please enter a date.");
            return;
        }
        
        if (menuItems == null || menuItems.isEmpty()) {
            MainApp.displayErrorMessage("No Menu Items", "Please select at least one menu item.");
            return;
        }
        
        String orderId = DepeFood.handleBuatPesanan(namaRestoran, tanggalPemesanan, menuItems.size(), menuItems);
        if (orderId != null) {
            MainApp.showSuccessMessage("Order Success", "Your order with ID " + orderId + " has been placed successfully.");
        }
    }

    private void handleBayarBill(String orderID, String paymentMethod) {
        boolean isValid = true;
        boolean validId = OrderGenerator.validateOrderID(orderID);
        DepeFoodPaymentSystem paymentSystem = user.getPaymentSystem(); 
        System.out.println(user.getPaymentSystem().getClass().getName());
        System.out.println(!paymentMethod.equals(user.getPaymentSystem().getClass().getSimpleName()));
        if(paymentMethod.equals("Credit Card")) {
            paymentMethod = "CreditCardPayment";
        } else {
            paymentMethod = "DebitPayment";
        }

        if(isValid) {
            Order order = DepeFood.getOrderOrNull(orderID);
            if(order.getOrderFinished()) {
                MainApp.showSuccessMessage("Failed Payment", "This order has already been paid!");
            } else {
                DepeFood.handleBayarBill(orderID, paymentMethod);
    
                double totalHarga = order.getTotalHarga();
                double transactionalFee = totalHarga * 0.02;
        
                // Configure DecimalFormat to use a grouping separator
                DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setGroupingSeparator('.');
                symbols.setDecimalSeparator(',');
                decimalFormat.setDecimalFormatSymbols(symbols);
    
                if(paymentSystem instanceof DebitPayment) {
                    String message = String.format("Bill paid successfully for Rp%s", decimalFormat.format(totalHarga));
                    MainApp.showSuccessMessage("Success Payment", message);
                } else {
                    String message = String.format(
                    "Bill paid successfully for Rp%s with Rp%s transactional fee",
                    decimalFormat.format(totalHarga),
                    decimalFormat.format(transactionalFee)
                    );
                    MainApp.showSuccessMessage("Success Payment",message);
                }
            }
        } else if (!validId) {
            MainApp.displayErrorMessage("Failed Payment", "Order ID is not found!");
        }
    }

}