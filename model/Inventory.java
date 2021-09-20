package model;
/**
 * Inventory class
 * @author Analy Ramirez-Berber
 */
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts  = FXCollections.observableArrayList();

    /**
     * adds a part to the inventory
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * adds a product to inventory
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * look up part by id
     * @param partId
     * @return
     */
    public static Part lookupPart(int partId){
        Part partFound = null;
        for (Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }
        return partFound;
    }

    /**
     * look up product by id
     * @param productId
     * @return
     */
    public static Product lookupProduct(int productId){
        Product productFound = null;
        for (Product product : allProducts){
            if (product.getId() == productId){
                productFound = product;
            }
        }
        return productFound;
    }
    /**
     *  look up parts by name
     */
    private static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }
    /**
     *  look up products by name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList <Product> productsFound = FXCollections.observableArrayList();
        for (Product product : allProducts){
            if (product.getName().equals(productName)){
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * update parts
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){
        if (allParts.get(index).getId() == selectedPart.getId()) {
            allParts.set(index, selectedPart);
        }
        return;
    }

    /**
     * update product
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct){
        if (allProducts.get(index).getId() == newProduct.getId()) {
            allProducts.set(index, newProduct);
        }
        return;
    }

    /**
     * deletes a part in the inventory
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * deletes a product from inventory
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * get list of parts in inventory
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *  gets list of products in inventory
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
