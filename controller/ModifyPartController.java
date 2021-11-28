package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Modify parts screen controller:
 * Allows user to modify selected part and select in house or outsourced
 * Alerts if invalid data is entered or if text fields are left blank
 * @author Ana Ramirez
 */
public class ModifyPartController implements Initializable {
    /**
     * button cancels changes and returns to home screen
     */
    @FXML
    private javafx.scene.control.Button cancel;

    /**
     * button saves changes made to part
     */
    @FXML
    private Button save;

    /**
     * label changes to InHouse or Outsourced by associated radio buttons selection
     */
    @FXML
    private Label partSource;

    /**
     * toggle group between inHouse and outsourced radio buttons
     */
    @FXML
    private ToggleGroup addPartToggle;

    /**
     * button selects part is inHouse
     */
    @FXML
    private RadioButton partInHouse;

    /**
     * button selects part is outsourced
     */
    @FXML
    private RadioButton partOutsourced;

    /**
     * text field for either inHouse and outsourced part
     */
    @FXML
    private TextField partSourceTF;

    /**
     * part ID text field
     */
    @FXML
    private TextField partID;

    /**
     * part name text field
     */
    @FXML
    private TextField partName;

    /**
     * part maximum inventory text field
     */
    @FXML
    private TextField partMax;

    /**
     * part minimum inventory text field
     */
    @FXML
    private TextField partMin;

    /**
     * part cost or price text field
     */
    @FXML
    private TextField partPriceCost;

    /**
     * part inventory text field
     */
    @FXML
    private TextField partInv;

    /**
     * part selected by user to modify in modify part screen
     */
    private Part selectedPart;

    /**
     * sets part source label to Machine ID
     * @param actionEvent
     */
    @FXML
    void inHouseSelected(javafx.event.ActionEvent actionEvent) {

        partSource.setText("Machine ID");
    }

    /**
     * sets part source label to company name
     * @param actionEvent
     */
    @FXML
    void outSourcedSelected(javafx.event.ActionEvent actionEvent) {

        partSource.setText("Company Name");
    }

    /**
     * cancel changes and returns user to main screen with confirmation
     * @param actionEvent cancel clicked
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
     * saves modified part and checks that:
     * min < max, stock is between min and max, valid values entered, no blanks
     * save with either In-house or Outsourced selected by user
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    void addClick(javafx.event.ActionEvent actionEvent) throws Exception {
        try{
            int id = selectedPart.getId();
            String name = partName.getText();
            Double price = Double.parseDouble(partPriceCost.getText());
            int stock = Integer.parseInt(partInv.getText());
            int min = Integer.parseInt(partMin.getText());
            int max = Integer.parseInt(partMax.getText());
            if(min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Value Error");
                alert.setContentText("Minimum inventory value cannot be greater than the maximum inventory value.");
                alert.showAndWait();
            }
            else if (stock > max || stock <min){
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
            }
            else {
                if (partInHouse.isSelected()){
                    int MachineID = Integer.parseInt(partSourceTF.getText());
                    InHouse addPart = new InHouse(id, name, price, stock, min, max, MachineID);
                    Inventory.addPart(addPart);
                    Inventory.deletePart(selectedPart);
                }
                if (partOutsourced.isSelected()){
                    String companyName = partSourceTF.getText();
                    OutSourced addPart = new OutSourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(addPart);
                    Inventory.deletePart(selectedPart);
                }
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        }catch(NumberFormatException event) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values for each entry.");
            alert.showAndWait();
        }
    }

    /**
     * Open window with selected part values in their appropriate text fields
     * In-house or outsourced radio buttons determine part source label
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = MainScreenController.getPartToModify();
        partID.setText(String.valueOf(selectedPart.getId()));
        partName.setText(selectedPart.getName());
        partInv.setText(String.valueOf(selectedPart.getStock()));
        partPriceCost.setText(String.valueOf(selectedPart.getPrice()));
        partMax.setText(String.valueOf(selectedPart.getMax()));
        partMin.setText(String.valueOf(selectedPart.getMin()));
        if (selectedPart instanceof InHouse){
            partSourceTF.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
            partSource.setText("Machine ID");
            partInHouse.setSelected(true);
        }
        if (selectedPart instanceof OutSourced){
            partSourceTF.setText(((OutSourced) selectedPart).getCompanyName());
            partSource.setText("Company Name");
            partOutsourced.setSelected(true);
        }

    }
}
