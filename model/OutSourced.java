package model;
/**
 * Outsourced class
 * @author Analy Ramirez-Berber
 */

public class OutSourced extends Part {
    /**
     * company name
     */
    private String companyName;

    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * set company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

}
