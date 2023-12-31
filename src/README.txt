
# QAM2 Task 1: Java Application Development

This application is an Appointment Management System designed to handle customer and appointment data.
It allows users to create, update, and manage appointments, as well as maintain customer
information, with functionalities liker user authentication and time zone adjustments
for scheduling.

## Author

William Deutsch

wdeutsc@wgu.edu

12/31/2023

Application Version: 1.0

## IDE and Java Module Information

IntelliJ IDEA 2023.2.2 (Community Edition)

Build #IC-232.9921.47, built on September 12, 2023

Runtime version: 17.0.8+7-b1000.22 amd64

VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 10.0

JavaFX: javafx-sdk-17.01

MySql Connector: mysql-connector-java-8.0.25

## How to run the program

This program requires Java 17. The application connects to a MySQL database for user authentication and data storage.
Run the program by executing the main class or via the command line.
If you are using an IDE like intelliJ, you can run the application directly by clicking the run button.
Upon starting, the application displays a login screen. Use a valid username and password that match the credentials stored in your MySQL database.

## Additional Report

For the additional report, I added the functionality to filter customers based on their country.
The 'getCustomerIdsByCountry' method in my application retrieves a list of unique Customer ID's based on their associated appointments in a specific country.
It utilizes a SQL query that joins the customers, first_level_divisions, and countries tables to filter customers by country.
Once the IDs are obtained, the 'getCustomersById' method is called to fetch detailed customer information for each ID.