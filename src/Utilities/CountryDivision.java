package Utilities;

/**
 * Represents a geographical division along with its associated country.
 * This class is used to store information about a specific division and the country it belongs to.
 *
 * @author William Deutsch
 */
public class CountryDivision {

    private String country;
    private String division;

    /**
     * Constructor for creating a countryDivision object.
     *
     * @param country The name of the country.
     * @param division The name of the division within the country.
     */
    public CountryDivision(String country, String division) {
        this.country = country;
        this.division = division;
    }

    /**
     * Gets the name of the country.
     *
     * @return The name of the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the name of the division.
     *
     * @return The name of the division.
     */
    public String getDivision() {
        return division;
    }
}
