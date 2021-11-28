import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;



public class main extends Application {

    /**
     * product id
     */
    private static int productID =0;

    /**
     *  create new product id
     * @return
     */
    public static int createProdID(){
        return ++productID;
    }

    /**
     * part id
     */
    private static int partId = 0;
    /**
     * creates new part id
     * @return
     */
    public static int createPartID(){
        return ++partId;
    }
    /**
     * open main screen with the following parts and products already in the two table views
     * @param args
     */
    public static void main(String[] args) {
        int partID = createPartID();
        Part part1 = new InHouse(partID, "motor", 1115.0, 10, 5, 15, 1);
        Inventory.addPart(part1);

        partID = createPartID();
        Part part2 = new InHouse(partID, "tire", 101.51, 100, 30, 150, 2);
        Inventory.addPart(part2);

        partID = createPartID();
        Part part3 = new InHouse(partID, "handle", 15.0, 13, 5, 25, 3);
        Inventory.addPart(part3);

        int productID = createProdID();
        Product product1 = new Product(productID, "car", 100015.0, 7, 5, 10);
        Inventory.addProduct(product1);

        productID = createProdID();
        Product product2 = new Product(productID, "truck", 20050.0, 5, 3, 7);
        Inventory.addProduct(product2);

        productID = createProdID();
        Product product3 = new Product(productID, "suv", 15000.0, 12, 10, 20);
        Inventory.addProduct(product3);
        launch(args);
    }

    /**
     * open main screen of inventory system
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
