package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * Represents an appointment in the Appointment Management Application.
 * This class stores details about an appointment, including its ID, title, description, location, type, and timing.
 *
 * @author William Deutsch
 */
public class Appointments {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;


    /**
     * Constructor for creating an Appointment object.
     *
     * @param appointmentId The ID of the appointment.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param type The type of the appointment.
     * @param start The start time and date of the appointment.
     * @param end The end time and date of the appointment.
     * @param customerId The customer ID associated with the appointment.
     * @param userId The user ID who created the appointment.
     * @param contactId The contact ID associated with the appointment.
     */
    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start,
                        LocalDateTime end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Retrieves the appointment ID of the appointment.
     *
     * @return The appointment ID.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment ID of an appointment.
     *
     * @param appointmentId The new appointment ID.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Retrieves the title of an appointment.
     *
     * @return The appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the appointment title.
     *
     * @param title The new title of the appointment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description of an appointment.
     *
     * @return The appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the appointment description.
     *
     * @param description The new description of the appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the location of an appointment.
     *
     * @return The appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the appointment location.
     *
     * @param location The new location of the appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the type of appointment.
     *
     * @return The type of appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of appointment.
     *
     * @param type The new type of the appointment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the start LocalDateTime of the appointment.
     * Returns the full date and time when the appointment is scheduled to start.
     *
     * @return The start of the LocalDateTime of the appointment.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the start LocalDateTime of the appointment.
     *
     * @param start The new start LocalDateTime of the appointment.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Retrieves the end LocalDateTime of the application.
     * Returns the full date and time when the appointment is scheduled to end.
     *
     * @return The end of the LocalDateTime of the appointment.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the end LocalDateTime of the appointment.
     *
     * @param end The new end LocalDateTime of the appointment.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Retrieves the customer_id associated with the appointment.
     *
     * @return The associated customer_id of the appointment.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer_id of an appointment.
     *
     * @param customerId The new customer_id of the appointment.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Retrieves the user_id associated with the appointment.
     *
     * @return The associated user_id of the appointment.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user_id of an appointment.
     *
     * @param userId The new user_id of the appointment.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the contact_id associated with the appointment.
     *
     * @return The associated contact_id of the appointment.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact_id of an appointment.
     *
     * @param contactId The new contact_id of the appointment.
    */
    public void setContactId(int contactId) {this.contactId = contactId; }

    /**
     * Gets the start date of the appointment.
     * Extracts and returns only the date part of the appointment's start time.
     *
     * @return The start date of the appointment.
     */
    public LocalDate getStartDate() {
        return start != null ? start.toLocalDate() : null;
    }

    /**
     * Gets the start time of the appointment.
     * Extracts and returns only the time part of the appointment's start time.
     *
     * @return The start time of the appointment.
     */
    public LocalTime getStartTime() {
        return start != null ? start.toLocalTime() : null;
    }

    /**
     * Gets the end date of the appointment.
     * Extracts and returns only the date part of the appointment's end time.
     *
     * @return The end date of the appointment.
     */
    public LocalDate getEndDate() {
        return end != null ? end.toLocalDate() : null;
    }

    /**
     * Gets the end time of the appointment.
     * Extracts and returns only the time part of the appointment's end time.
     *
     * @return The end time of the appointment.
     */
    public LocalTime getEndTime() {
        return end != null ? end.toLocalTime() : null;
    }



}
