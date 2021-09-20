package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.util.Optional;


import java.net.URL;
import java.util.ResourceBundle;
/**
 * Add Product Controller:
 * Allows user to create new product .
 * Includes table view of parts and allows user to
 * add associated parts to the product.
 * Verifies and alerts if invalid data is entered or if text fields are left blank.
 * User can remove an associated part with confirmation.
 * RUNTIME ERROR CORRECTION:
 * Alert is displayed when no part is selected to add an associated part.
 * This error occurs when a null value is passed to the addClicked() method below.
 * @author Analy Ramirez-Berber
 */

public class AddProductController implements Initializable{
    /**
     * button cancels new product
     */
    @FXML
    private Button cancel;

    /**
     * button saves new product
     */
    @FXML
    private Button save;

    /**
     * button adds associated part
     */
    @FXML
    private Button add;

    /**
     * button removes associated part from product
     */
    @FXML
    private Button removeAssocPart;

    /**
     * searched parts table
     */
    @FXML
    private TextField searchPart;

    /**
     * product id
     */
    @FXML
    private TextField productID;

    /**
     * product name
     */
    @FXML
    private TextField productName;

    /**
     * product inventory level
     */
    @FXML
    private TextField productInv;

    /**
     * product price or cost
     */
    @FXML
    private TextField productPriceCost;

    /**
     * product maximum inventory
     */
    @FXML
    private TextField productMax;

    /**
     * product minimum inventory
     */
    @FXML
    private TextField productMin;

    /**
     * table view of parts
     */
    @FXML
    private TableView<Part> partsTableView;

    /**
     * parts table id column
     */
    @FXML
    private TableColumn<Part, Integer> Id;

    /**
     * parts table name column
     */
    @FXML
    private TableColumn<Part, String> Name;

    /**
     * parts table inventory column
     */
    @FXML
    private TableColumn<Part, Integer> Inv;

    /**
     * parts table price column
     */
    @FXML
    private TableColumn<Part, Double> Price;

    /**
     * stores associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * associated parts table view
     */
    @FXML
    private TableView<Part> associatedPartTableView;

    /**
     * associated parts table id column
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartID;

    /**
     * associated parts table name column
     */
    @FXML
    private TableColumn<Part, String> associatedPartName;

    /**
     * associated parts table inventory column
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInv;

    /**
     * associated parts table price column
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPrice;
    /**
     * product id
     */
    private static int productId=3;

    /**
     *  create new product id
     * @return product id
     */
    public static int createProdID(){
        return ++productId;
    }

    /**
     * adds user selected part to associated parts table
     * alerts when part is not selected
     * @param actionEvent
     */
    @FXML
    void addClicked(javafx.event.ActionEvent actionEvent) {

        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            associatedPartTableView.setItems(associatedParts);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }
    }

    /**
     * Cancels new product and returns user to the main screen
     * with user confirmation
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    void cancelClick(javafx.event.ActionEvent actionEvent) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(parent));
            stage1.show();
        }
    }

    /**
     * Removes associated part selected by user in the associated parts table
     * with confirmation
     * alerts when no associated part is selected
     * @param actionEvent
     */
    @FXML
    void removeClicked(javafx.event.ActionEvent actionEvent){
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm remove:");
            alert.setContentText("Are you sure you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartTableView.setItems(associatedParts);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error removing part.");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }
    }

    /**
     * Saves new product and checks:
     * min<max, inventory between min and max, valid values entered
     * Alerts when exceptions are found, invalid values or blanks
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    void saveClicked(javafx.event.ActionEvent actionEvent) throws Exception {
        try{
            int id = createProdID();
            String name = productName.getText();
            double price = Double.parseDouble(productPriceCost.getText());
            int inventory = Integer.parseInt(productInv.getText());
            int max = Integer.parseInt(productMax.getText());
            int min = Integer.parseInt(productMin.getText());
            if(min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Value Error");
                alert.setContentText("Minimum inventory value cannot be greater than the maximum inventory value.");
                alert.showAndWait();
            }
            else if (inventory > max || inventory <min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Value Error");
                alert.setContentText("Inventory value must be between the maximum and minimum inventory values.");
                alert.showAndWait();
            }
            else if (name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Value Error");
                alert.setContentText("Please enter a valid name.");
                alert.showAndWait();
            } else {
                Product product = new Product(id, name, price, inventory, min, max);
                for (Part part : associatedParts) {
                    product.addAssociatedPart(part);
                }

                Inventory.addProduct(product);

                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        }catch(Exception event) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values for each entry.");
            alert.showAndWait();
        }
    }

    /**
     * Searches parts table by part id or name and alerts when no part is found
     * all parts appear when search is blank
     * @param event
     */
    @FXML
    void searchPartInitiated(KeyEvent event){
        ObservableList<Part> partsSearch = FXCollections.observableArrayList();
        if (searchPart.getText().isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
        } else {
            ObservableList<Part> listParts = Inventory.getAllParts();
            String string = searchPart.getText();
            for (Part part : listParts) {
                if (String.valueOf(part.getId()).contains(string) ||
                        part.getName().contains(string)) {
                    partsSearch.add(part);
                }
            }
            partsTableView.setItems(partsSearch);
        }
        if (partsSearch.size() == 0 && (!searchPart.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part not found.");
            alert.showAndWait();
        }
    }

    /**
     * open window with the parts table identical to the main screen and initiates the
     * associated parts table
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsTableView.setItems(Inventory.getAllParts());
        Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Inv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        Price.setCellValueFactory(new PropertyValueFactory<>("Price"));


        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }
}
