package controller;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

/**
 * Add parts screen controller:
 * Allows user to create new part and select in house or outsourced
 * Alerts if invalid data is entered or if text fields are left blank
 * @author Analy Ramirez-Berber
 */
public class AddPartController implements Initializable {
    /**
     * button cancels adding part
     */
    @FXML
    private javafx.scene.control.Button cancel;

    /**
     * button saves new part
     */
    @FXML
    private Button save;

    /**
     *label part source either inHouse or outsourced
     */
    @FXML
    private Label partSource;

    /**
     * toggle group between inHouse or Outsourced radio buttons
     */
    @FXML
    private ToggleGroup addPartToggle;

    /**
     *radio button selects inHouse
     */
    @FXML
    private RadioButton partInHouse;

    /**
     *radio button selects outSourced
     */
    @FXML
    private RadioButton partOutsourced;

    /**
     * part source text field inHouse or outsourced
     */
    @FXML
    private TextField partSourceTF;

    /**
     * part ID
     */
    @FXML
    private TextField partID;

    /**
     *part name
     */
    @FXML
    private TextField partName;

    /**
     * part maximum inventory
     */
    @FXML
    private TextField partMax;

    /**
     * part minimum inventory
     */
    @FXML
    private TextField partMin;

    /**
     * part price or cost
     */
    @FXML
    private TextField partPriceCost;

    /**
     * part inventory
     */
    @FXML
    private TextField partInv;

    /**
     * part id
     */
    private static int partId=3;
    /**
     * creates new part id
     * @return part id
     */
    public static int createPartID(){
        return ++partId;
    }



    /**
     * sets part source label to Machine ID
     * @param actionEvent
     */
    @FXML
    void inHouseSelected(javafx.event.ActionEvent actionEvent) {

        partSource.setText("Machine ID");
    }

    /**
     * sets part source label to Company Name
     * @param actionEvent
     */
    @FXML
    void outSourcedSelected(javafx.event.ActionEvent actionEvent) {

        partSource.setText("Company Name");
    }

    /**
     * saves new part and checks that:
     * min < max, stock is between min and max, valid values entered, no blanks
     * through alerts
     * save with either In-house or Outsourced selected by user
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    void addClick(javafx.event.ActionEvent actionEvent) throws Exception {
        try{
            int id = createPartID();
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
                }
                if (partOutsourced.isSelected()){
                    String companyName = partSourceTF.getText();
                    OutSourced addPart = new OutSourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(addPart);
                }
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
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
     * cancel new part and returns user to main screen with confirmation
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
     * open window with radio button inHouse selected and part source label set to Machine ID
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partInHouse.setSelected(true);
        partSource.setText("Machine ID");
    }
}
