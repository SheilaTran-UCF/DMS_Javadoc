// Package declaration: defines the package this class belongs to
package com.addingdatabase.assigment_dms_phase4;

/**
 * Professor: Ashley Evans
 * Author: Minh Ngoc Tran
 * Course: 202530-CEN-3024C-31774
 * Date: July15 2025
 *
 * AssigmentDmsPhase4Application.java
 *
 * This is the main entry point for the Data Management System (DMS) Phase 4 Spring Boot application.
 *
 * Key Features:
 * - Prompts the user to input the file path to the SQLite database at runtime.
 * - Dynamically sets the Spring datasource URL property based on the user input.
 * - Validates the user input to ensure a valid database path is provided.
 * - Launches the Spring Boot application with the configured datasource.
 *
 * Usage:
 * When the application starts, it asks for the SQLite file path via the console.
 * The input is used to configure the datasource URL before the application fully boots up.
 *
 */


// Import Spring Boot application classes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Import Scanner for reading input from the console
import java.util.Scanner;

// Mark this class as the main Spring Boot application entry point
@SpringBootApplication
public class AssigmentDmsPhase4Application {

    // Main method - application entry point
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the SQLite database file path
        System.out.print("Enter SQLite file path: ");

        // Read the user input as a string
        String dbPath = scanner.nextLine();

        // Validate the input: check if the path is null or blank
        if (dbPath == null || dbPath.isBlank()) {
            // If input is invalid, display an error message and exit the program
            System.out.println("Invalid path. Exiting.");
            return;
        }

        // Set the Spring datasource URL property dynamically using the entered file path
        System.setProperty("spring.datasource.url", "jdbc:sqlite:" + dbPath);

        // Launch the Spring Boot application, passing command line arguments
        SpringApplication.run(AssigmentDmsPhase4Application.class, args);
    }
}
