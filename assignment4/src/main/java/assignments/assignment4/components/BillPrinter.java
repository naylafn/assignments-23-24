package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import assignments.assignment1.OrderGenerator;
import assignments.assignment2.MainMenu;
import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.form.LoginForm;
import assignments.assignment4.page.CustomerMenu;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;
    private Scene previousScene;

    Font primaryFont = MainApp.primaryFont;
    Font headerFont = MainApp.headerFont;
    Font bold = MainApp.bold;
    Background bgStyle = MainApp.bgStyle;
    String buttonStyle = MainApp.buttonStyle;
    String fontColor = MainApp.fontColor;
    String textfieldStyle = MainApp.textfieldStyle;

    public BillPrinter(Stage stage, MainApp mainApp, User user, Scene previouScene) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
        this.previousScene = previouScene;
    }

    private Scene createBillPrinterForm(){
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(bgStyle);

        Label header = new Label("Print Bill");
        header.setFont(headerFont);
        header.setStyle(fontColor);
        header.setPadding(new Insets(0,0,15,0));

        HBox inputBox = new HBox(15);
        inputBox.setAlignment(Pos.CENTER);

        Label orderIdLabel = new Label("Order ID");
        orderIdLabel.setStyle(fontColor);
        orderIdLabel.setFont(primaryFont);

        TextField orderId = new TextField();
        orderId.setStyle(textfieldStyle);

        inputBox.getChildren().addAll(orderIdLabel, orderId);

        // Horizontal buttons
        HBox buttonsBox = new HBox(20); // Set spacing between buttons
        buttonsBox.setAlignment(Pos.CENTER); // Center align the buttons
        buttonsBox.setPadding(new Insets(5, 20, 0, 20)); // Padding left and right

        Button submitButton = new Button("Print Bill");
        submitButton.setStyle(buttonStyle);
        submitButton.setOnAction(e -> printBill(orderId.getText())); // Call printBill method

        Button backButton = new Button("Back");
        backButton.setStyle(buttonStyle);
        backButton.setOnAction(e -> stage.setScene(previousScene)); // Navigate back to the previous scene

        buttonsBox.getChildren().addAll(submitButton, backButton);

        layout.getChildren().addAll(header, inputBox, buttonsBox);
        VBox.setMargin(orderId, new Insets(20));

        return new Scene(layout, 400, 300);
    }

    private void printBill(String orderId) {
        boolean isValid = OrderGenerator.validateOrderID(orderId);
        if (isValid) {
            Order order = DepeFood.getOrderOrNull(orderId);
            stage.setScene(createBillDetailsScene(order));
        } else {
            MainApp.displayErrorMessage("Invalid Order ID", "The provided Order ID is invalid.");
        }
    }

    private Scene createBillDetailsScene(Order order) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(bgStyle);

        Label header = new Label("BILL");
        header.setFont(headerFont);
        header.setStyle(fontColor);
        header.setPadding(new Insets(0,0,10,0));

        Label orderIdLabel = new Label("Order ID: " + order.getOrderId());
        orderIdLabel.setStyle(fontColor);
        orderIdLabel.setFont(primaryFont);
        Label orderDateLabel = new Label("Tanggal Pemesanan: " + order.getTanggal());
        orderDateLabel.setStyle(fontColor);
        orderDateLabel.setFont(primaryFont);
        Label restaurantLabel = new Label("Restaurant: " + (order.getRestaurant()).getNama());
        restaurantLabel.setStyle(fontColor);
        restaurantLabel.setFont(primaryFont);
        Label deliveryLocationLabel = new Label("Lokasi Pengiriman: " + (LoginForm.getUserLoggedIn()).getLokasi());
        deliveryLocationLabel.setStyle(fontColor);
        deliveryLocationLabel.setFont(primaryFont);
        Label deliveryStatusLabel = new Label("Status Pengiriman: " + order.getOrderFinished());
        deliveryStatusLabel.setStyle(fontColor);
        deliveryLocationLabel.setFont(primaryFont);

        VBox orderDetailsBox = new VBox(5);
        orderDetailsBox.setAlignment(Pos.CENTER);
        Label orderDetailsHeader = new Label("Pesanan:");
        orderDetailsHeader.setStyle(fontColor);
        orderDetailsHeader.setFont(bold);
        orderDetailsBox.getChildren().add(orderDetailsHeader);

        for (Menu item : order.getItems()) {
            Label itemLabel = new Label("- " + item.getNamaMakanan() + " Rp " + item.getHarga());
            itemLabel.setStyle(fontColor);
            itemLabel.setFont(primaryFont);
            orderDetailsBox.getChildren().add(itemLabel);
        }

        Label deliveryFeeLabel = new Label("Biaya Ongkos Kirim: Rp " + order.getOngkir());
        deliveryFeeLabel.setStyle(fontColor);
        deliveryFeeLabel.setFont(primaryFont);
        Label totalCostLabel = new Label("Total Biaya: Rp " + order.getTotalHarga());
        totalCostLabel.setStyle(fontColor);
        totalCostLabel.setFont(primaryFont);

        Button backButton = new Button("Back");
        backButton.setStyle(buttonStyle);
        backButton.setOnAction(e -> stage.setScene(previousScene));

        layout.getChildren().addAll(header, orderIdLabel, orderDateLabel, restaurantLabel, deliveryLocationLabel,
                deliveryStatusLabel, orderDetailsBox, deliveryFeeLabel, totalCostLabel, backButton);

        return new Scene(layout, 400, 400);
    }

    public Scene getScene() {
        return this.createBillPrinterForm();
    }

    // Class ini opsional
    public class MenuItem {
        private final StringProperty itemName;
        private final StringProperty price;

        public MenuItem(String itemName, String price) {
            this.itemName = new SimpleStringProperty(itemName);
            this.price = new SimpleStringProperty(price);
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public StringProperty priceProperty() {
            return price;
        }

        public String getItemName() {
            return itemName.get();
        }

        public String getPrice() {
            return price.get();
        }
    }
}
