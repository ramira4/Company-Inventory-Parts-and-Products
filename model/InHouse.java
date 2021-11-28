package model;

/**
 * Part class
 * 
 */
public class InHouse extends Part {

    /**
     * machine id for a part
     */
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
      * SETTER & GETTER
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }


    public int getMachineId() {
        return machineId;
    }

}
