package model;
/**
 * Product class
 * @author Analy Ramirez-Berber
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @return associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * add associated part
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * delete selected associated part
     * @param selectedAssociatedPart
     */
    public void deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * set id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * set price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * set stock
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * set min
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * set max
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }
}