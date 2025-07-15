// Define the package for this class
package com.addingdatabase.assigment_dms_phase4.model;

/**
 * Professor: Ashley Evans
 * Author: Minh Ngoc Tran
 * Course: 202530-CEN-3024C-31774
 * Date: July15 2025
 *
 * LocalDateAttributeConverter.java
 *
 * This class defines a custom JPA AttributeConverter to handle the conversion
 * between Java's LocalDate type and the SQL DATE type when persisting and retrieving
 * data from the database.
 *
 * Purpose:
 * Since some database systems (like SQLite) and JDBC drivers do not natively support
 * Java's LocalDate type, this converter ensures seamless translation between
 * LocalDate and java.sql.Date for all entity fields using LocalDate.
 *
 * Key Features:
 * - Automatically applies to all LocalDate fields in JPA entities via @Converter(autoApply = true).
 * - Converts LocalDate to java.sql.Date when persisting to the database.
 * - Converts java.sql.Date to LocalDate when reading from the database.
 *
 * Usage:
 * No manual configuration is needed in entities, as the converter is globally applied
 * by setting autoApply = true.
 */


// Import JPA interfaces to define a custom converter
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// Import Java SQL and time APIs
import java.sql.Date;         // java.sql.Date used for JDBC-compatible date handling
import java.time.LocalDate;   // java.time.LocalDate represents a date without time or timezone

// Marks this class as a JPA converter that will be automatically applied to all LocalDate fields
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    // Convert Java's LocalDate to java.sql.Date before storing in the database
    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        // If the attribute is null, return null to avoid NullPointerException
        // Otherwise, convert LocalDate to SQL Date using valueOf()
        return (attribute == null) ? null : Date.valueOf(attribute);
    }

    // Convert java.sql.Date from the database into Java's LocalDate when reading from the DB
    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        // If database value is null, return null
        // Otherwise, convert SQL Date to LocalDate using toLocalDate()
        return (dbData == null) ? null : dbData.toLocalDate();
    }
}
