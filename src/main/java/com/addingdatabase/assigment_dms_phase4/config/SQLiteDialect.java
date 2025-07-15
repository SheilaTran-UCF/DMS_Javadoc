// Package declaration: defines the package where this class belongs
package com.addingdatabase.assigment_dms_phase4.config;

/**
 * Professor: Ashley Evans
 * Author: Minh Ngoc Tran
 * Course: 202530-CEN-3024C-31774
 * Date: July15 2025
 * SQLiteDialect.java
 *
 * This class defines a custom Hibernate SQL dialect for the SQLite database engine.
 * Since SQLite is not officially supported by Hibernate, this custom dialect configures
 * the necessary behaviors and capabilities so that Hibernate can properly generate
 * SQL statements compatible with SQLite’s syntax and limitations.
 *
 * Key Features:
 * - Provides identity column (auto-increment) support using IdentityColumnSupportImpl.
 * - Disables unsupported features in SQLite such as ALTER TABLE and dropping constraints.
 * - Configures support for "IF EXISTS" clauses before and after table names in DROP statements.
 * - Specifies that cascade delete is not supported by default in SQLite.
 *
 * Usage:
 * This custom dialect class should be registered in the application’s Hibernate configuration
 * so that Hibernate uses it when interacting with the SQLite database.
 *
 */


// Import Hibernate Dialect base class
import org.hibernate.dialect.Dialect;
// Import support class for identity (auto-increment) columns
import org.hibernate.dialect.identity.IdentityColumnSupport;
// Default implementation of identity column support
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

/**
 * Custom SQLiteDialect class to configure Hibernate for SQLite database compatibility.
 * SQLite requires custom handling because it's not officially supported by Hibernate.
 */
public class SQLiteDialect extends Dialect {

    // Override method to provide identity column support (auto-increment behavior in SQLite)
    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        // Return default implementation for identity columns
        return new IdentityColumnSupportImpl();
    }

    // No need for supportsIdentityColumns() method — removed intentionally
    // because IdentityColumnSupportImpl will handle identity column behavior.

    // Override method to indicate that SQLite does not support ALTER TABLE
    @Override
    public boolean hasAlterTable() {
        // SQLite has very limited ALTER TABLE support (no drop column, etc.)
        return false;
    }

    // Override method to indicate that dropping constraints is not supported in SQLite
    @Override
    public boolean dropConstraints() {
        // SQLite doesn't support explicitly dropping constraints — handled differently
        return false;
    }

    // Override to confirm that SQLite supports "IF EXISTS" before table name in DROP statements
    @Override
    public boolean supportsIfExistsBeforeTableName() {
        // Example: DROP TABLE IF EXISTS table_name;
        return true;
    }

    // Override to confirm that SQLite supports "IF EXISTS" after table name in DROP statements
    @Override
    public boolean supportsIfExistsAfterTableName() {
        // Example: DROP TABLE table_name IF EXISTS;
        return true;
    }

    // Override method to specify that SQLite does not support cascade delete constraints
    @Override
    public boolean supportsCascadeDelete() {
        // SQLite requires special PRAGMA settings for ON DELETE CASCADE, not handled by default
        return false;
    }
}
