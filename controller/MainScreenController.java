package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import java.awt.event.ActionEvent;
/** Main Screen Controller:
 * Parts and Products pane views:
 * Users may add, modify, delete, and search for
 * parts in products in their respective panes.
 * FUTURE ENHANCEMENT in the searchProduct() method below may include
 * searching for products with inventory levels that are
 * at the minimum inventory and will need to be restocked soon.
 * @author Analy Ramirez-Berber
 * */

public class MainScreenController implements Initializable {
    /**
     * button opens screen to create new parts
     */
    @FXML
    private Button partAdd;

    /**
     * button opens modify part screen for selected part
     */
    @FXML
    private Button partModify;

    /**
     * button to delete selected part
     */
    @FXML
    private Button partDelete;

    /**
     * table view of parts
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * parts table id column
     */
    @FXML
    private TableColumn<Part, Integer> partID;

    /**
     * parts table name column
     */
    @FXML
    private TableColumn<Part, String> partName;

    /**
     * parts table price column
     */
    @FXML
    private TableColumn<Part, Double> partPrice;

    /**
     * part table stock column
     */
    @FXML
    private TableColumn<Part, Integer> partStock;

    /**
     * part table minimum inventory column
     */
    @FXML
    private TableColumn<Part, Integer> partMin;

    /**
     * parts table maximum inventory column
     */
    @FXML
    private TableColumn<Part, Integer> partMax;

    /**
     * button opens add product screen
     */
    @FXML
    private Button productAdd;

    /**
     * button opens modify product screen for selected product
     */
    @FXML
    private Button productModify;

    /**
     * button deletes selected product
     */
    @FXML
    private Button productDelete;

    /**
     * table view for products
     */
    @FXML
    private TableView<Product> productTableView;

    /**
     * product table id column
     */
    @FXML
    private TableColumn<Product, Integer> productID;

    /**
     * product table name column
     */
    @FXML
    private TableColumn<Product, String> productName;

    /**
     * product table stock column
     */
    @FXML
    private TableColumn<Product, Integer> productStock;

    /**
     * product table minimum inventory column
     */
    @FXML
    private TableColumn<Product, Integer> productMin;

    /**
     * product table maximum inventory column
     */
    @FXML
    private TableColumn<Product, Integer> productMax;

    /**
     * product table price column
     */
    @FXML
    private TableColumn<Product, Double> productPrice;

    /**
     *  text field where user can search for a part in the parts table
     */
    @FXML
    private TextField partSearch;

    /**
     * text field where user can search for a product in the products table
     */
    @FXML
    private TextField productSearch;

    /**
     * button exits the application
     */
    @FXML
    private Button exit;


    /**
     * selected part to modify
     */
    private static Part partToModify;

    /**
     * selected part by user
     */
    private static Part selectedPart;

    /**
     *
     * @return part to be modified in new screen
     */
    static Part getPartToModify() {
        return partToModify;
    }

    /**
     * product selected by user to be modified
     */
    private static Product productToModify;

    /**
     * product selected by user
     */
    private static Product selectedProduct;

    /**
     *
     * @return product to be modified in new screen
     */
    static Product getProductToModify(){
        return  productToModify;
    }

    /**
     *
     * @param actionEvent add part button opens add part screen
     * @throws Exception
     */
    @FXML
    public void AddPartClick(javafx.event.ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) partAdd.getScene().getWindow();
        stage.close();
        Parent addPart;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddPart.fxml"));
        addPart = loader.load();
        Scene addPartsScene = new Scene(addPart);
        Stage addPartStage = new Stage();
        addPartStage.setTitle("Add Part");
        addPartStage.setScene(addPartsScene);
        addPartStage.show();
    }

    /**
     * alerts when no part is selected to modify
     * @param actionEvent modify part button opens modify part screen
     * @throws Exception
     */
    @FXML
    public void ModifyPartClick(javafx.event.ActionEvent actionEvent) throws Exception {
        partToModify = partTableView.getSelectionModel().getSelectedItem();
        if (partToModify == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) partModify.getScene().getWindow();
            stage.close();
            Parent modifyPart;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
            modifyPart = loader.load();
            Scene modifyPartsScene = new Scene(modifyPart);
            Stage modifyPartStage = new Stage();
            modifyPartStage.setTitle("Modify Part");
            modifyPartStage.setScene(modifyPartsScene);
            modifyPartStage.show();
        }
    }

    /**
     * deletes selected part from inventory with confirmation
     * alerts when no part is selected
     */
    @FXML
    public void deletePartClick() {
        selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting Part");
            alert.setContentText("Do you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                model.Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * Searches parts table by part id or name and alerts when no part is found,
     * all parts appear when search is blank
     * @param event
     */
    @FXML
    void searchPart(KeyEvent event){
        ObservableList<Part> partsSearch = FXCollections.observableArrayList();
        if (partSearch.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        } else {
            ObservableList<Part> listParts = Inventory.getAllParts();
            String string = partSearch.getText();
            for (Part part : listParts) {
                if (String.valueOf(part.getId()).contains(string) ||
                        part.getName().contains(string)) {
                    partsSearch.add(part);
                }
            }
            partTableView.setItems(partsSearch);
        }
        if (partsSearch.size() == 0 && (!partSearch.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part not found.");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent add product button opens add product screen
     * @throws Exception
     */
    @FXML
    public void AddProductClick(javafx.event.ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) productAdd.getScene().getWindow();
        stage.close();
        Parent parent ;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddProduct.fxml"));
        parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage1 = new Stage();
        stage1.setTitle("Add Product");
        stage1.setScene(scene);
        stage1.show();
    }

    /**
     * alerts when no product is selected
     * @param actionEvent modify button opens modify product screen for selected product
     * @throws Exception
     */
    @FXML
    public void ModifyProductClick(javafx.event.ActionEvent actionEvent) throws Exception {
        productToModify = productTableView.getSelectionModel().getSelectedItem();
        if (productToModify == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a product to modify");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) productModify.getScene().getWindow();
            stage.close();
            Parent parent ;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
            parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage1 = new Stage();
            stage1.setTitle("Modify Product");
            stage1.setScene(scene);
            stage1.show();
        }
    }

    /**
     * product with associated parts may not be deleted,
     * attempting to delete product with associated parts results in alert,
     * deletes selected product with confirmation
     */
    @FXML
    public void deleteProductClick() {
        selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setHeaderText("Delete Error");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }else if (selectedProduct != null) {
            ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
            if(associatedParts.size() >= 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Error");
                alert.setHeaderText("Delete Error");
                alert.setContentText("Product has associated parts and may not be deleted.");
                alert.showAndWait();
            }else if (associatedParts.size() == 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleting Product");
                alert.setContentText("Do you want to delete the selected product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    model.Inventory.deleteProduct(selectedProduct);}}
        }
    }

    /**
     * Searches products table by part id or name and alerts when no product is found
     * all parts appear when search is blank
     * @param event
     */
    @FXML
    public void searchProduct(KeyEvent event){
        ObservableList<Product> productsSearch = FXCollections.observableArrayList();
        if (productSearch.getText().isEmpty()) {
            productTableView.setItems(Inventory.getAllProducts());
        } else {
            ObservableList<Product> listProducts = Inventory.getAllProducts();
            String string = productSearch.getText();
            for (Product product : listProducts) {
                if (String.valueOf(product.getId()).contains(string) ||
                        product.getName().contains(string)) {
                    productsSearch.add(product);
                }
            }
            productTableView.setItems(productsSearch);
        }
        if (productsSearch.size() == 0 && (!productSearch.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Product not found.");
            alert.showAndWait();
        }
    }

    /**
     * exit button exits entire application
     * @param actionEvent
     */
    @FXML
    void exitClicked(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * open main screen with populated tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partMin.setCellValueFactory(new PropertyValueFactory<Part, Integer>("min"));
        partMax.setCellValueFactory(new PropertyValueFactory<Part, Integer>("max"));

        productTableView.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productStock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productMin.setCellValueFactory(new PropertyValueFactory<Product, Integer>("min"));
        productMax.setCellValueFactory(new PropertyValueFactory<Product, Integer>("max"));
    }
}