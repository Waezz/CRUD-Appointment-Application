package model;

/**
 * Represents a customer in the Appointment Management application.
 * This class stores customer details, including ID, name, address, postal-code, phone number, and location information.
 *
 * @author William Deutsch
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    private String country;
    private String divisionName;

    /**
     * Constructor for creating a Customer object.
     *
     * @param id The ID of the customer.
     * @param name The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal-code of the customer's address.
     * @param phone The phone number of the customer.
     * @param divisionId The ID of the division where the customer is located.
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;

    }

    /**
     * Retrieves the customer's ID.
     *
     * @return The ID of the customer.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of a customer.
     *
     * @param id The new ID of the customer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name The new name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the address of the customer.
     *
     * @return The address of the customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address The new address of the customer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the postal-code of the customer.
     *
     * @return The postal-code of the customer.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal-code of the customer.
     *
     * @param postalCode The new postal-code of the customer.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Retrieves the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone The new phone number of the customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retrieves the divisionID of the customer.
     *
     * @return The divisionID of the customer.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the divisionID of the customer.
     *
     * @param divisionId The new divisionID of the customer.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Retrieves the country of the customer.
     *
     * @return The country of the customer.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the customer.
     *
     * @param country The new country of the customer.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retrieves the division name of the customer.
     *
     * @return The division name of the customer.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets the division name of the customer.
     *
     * @param divisionName The new division name of the customer.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }



}