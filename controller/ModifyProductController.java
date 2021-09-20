package controller;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Modify Product Controller:
 * Allows user to modify selected product .
 * Includes table view of parts and allows user to
 * add associated parts to the product.
 * Verifies and alerts if invalid data is entered or if text fields are left blank.
 * User can remove an associated part with confirmation.
 * @author Analy Ramirez-Berber
 */
public class ModifyProductController implements Initializable {
    /**
     * product to modify
     */
    Product selectedProduct;

    /**
     * button cancels modifying product
     */
    @FXML
    private Button cancel;

    /**
     * button saves modified product
     */
    @FXML
    private Button save;

    /**
     * button adds associated part
     */
    @FXML
    private Button add;

    /**
     * button removes associated part
     */
    @FXML
    private Button removeAssocPart;

    /**
     * text field where user can search for part in table
     */
    @FXML
    private TextField searchPart;

    /**
     * id
     */
    @FXML
    private TextField productID;

    /**
     * name
     */
    @FXML
    private TextField productName;

    /**
     * inventory
     */
    @FXML
    private TextField productInv;

    /**
     * price
     */
    @FXML
    private TextField productPriceCost;

    /**
     * maximum inventory
     */
    @FXML
    private TextField productMax;

    /**
     * minimum inventory
     */
    @FXML
    private TextField productMin;

    /**
     * part table view
     */
    @FXML
    private TableView<Part> partsTableView;

    /**
     * part table id column
     */
    @FXML
    private TableColumn<Part, Integer> Id;

    /**
     * part table name column
     */
    @FXML
    private TableColumn<Part, String> Name;

    /**
     * part table inventory column
     */
    @FXML
    private TableColumn<Part, Integer> Inv;

    /**
     * part table price column
     */
    @FXML
    private TableColumn<Part, Double> Price;


    /**
     * list of associated parts
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
     * adds a part to associated part table
     * alerts when no part is selected
     * @param actionEvent add button
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
     * cancels modifying product, closes window with confirmation
     * @param actionEvent cancel button
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
     * remove associated part from associated parts table with confirmation
     * alerts when no product is selected
     * @param actionEvent remove associated part button
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
     * saves modified product in inventory and checks:
     * min<max, inventory between min and max, valid values entered
     * Alerts when exceptions are found, invalid values or blanks
     * returns user to main screen
     * @param actionEvent save button
     * @throws Exception
     */
    @FXML
    void saveClicked(javafx.event.ActionEvent actionEvent) throws Exception {
        try{
            int id = selectedProduct.getId();
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
                alert.setContentText("Please do not leave any fields blank.");
                alert.showAndWait();
            } else {
                Product newProduct = new Product(id, name, price, inventory, min, max);
                for (Part part : associatedParts) {
                    newProduct.addAssociatedPart(part);
                }
                Inventory.addProduct(newProduct);
                Inventory.deleteProduct(selectedProduct);

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
     * searches parts table by part id or name and alerts when no part is found
     * when search is blank all parts are displayed
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
     * open window with populated tables and selected product values in the text fields
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = MainScreenController.getProductToModify();
        productID.setText(String.valueOf(selectedProduct.getId()));
        productName.setText(selectedProduct.getName());
        productInv.setText(String.valueOf(selectedProduct.getStock()));
        productPriceCost.setText(String.valueOf(selectedProduct.getPrice()));
        productMax.setText(String.valueOf(selectedProduct.getMax()));
        productMin.setText(String.valueOf(selectedProduct.getMin()));


        associatedParts = selectedProduct.getAllAssociatedParts();

        partsTableView.setItems(Inventory.getAllParts());
        Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Inv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        Price.setCellValueFactory(new PropertyValueFactory<>("Price"));

        associatedPartTableView.setItems(associatedParts);
        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }
}
