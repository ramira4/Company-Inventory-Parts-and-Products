package model;

/**
 * Part class
 * @author Analy Ramirez-Berber
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
     * sets the machine ID
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * gets the machine ID
     * @return
     */
    public int getMachineId() {
        return machineId;
    }

}