package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import IO.IO;
import IO.LogSQL;
import User.UserInformation;

/** Class responsible for initiating connection with database */

public abstract class SQL {

   // Connection used for all SQL operations
   protected static Connection connection;

   /** Initiate connection with MySQL DB */
   public static void startMySQL() throws SQLException {
      LogSQL.log("Connecting to database...");

      try {
         connection = getConnection();
      } catch (SQLException e) {
         LogSQL.log("Unable to connect to database... attempting connection to local database...");
         connection = getLocalConnection();
      }
   }

   /** Close connection with MySQL DB */
   public static void closeMySQL() throws SQLException {
      LogSQL.log("Closing connection...");
      connection.close();
   }

   /** return initialized connection based on user info and host url */
   private static Connection getConnection() throws SQLException {
      return DriverManager.getConnection(UserInformation.url,
            UserInformation.user, UserInformation.pass);
   }

   /** Can't connect to local DB, perform back up plan */
   private static Connection getLocalConnection() {
      // Connect to local computer's instance of MySQL
      Connection connection = null;
      try {
         connection = DriverManager.getConnection(UserInformation.backupURL,
               UserInformation.user, UserInformation.pass);
      } catch (SQLException e) {
         IO.displayGUI(e.getMessage());
      }

      LogSQL.log("Connection to main DB HAS ***FAILED***...\n   Attempting to create schema on local machine");
      PreparedStatement stmt = null;

      // Create the table and its content for local use
      try {
         stmt = connection.prepareStatement(UserInformation.createSchema);
         stmt.executeUpdate();
         LogSQL.log("Created new schema: kevinramsundertwitter");
         stmt = connection.prepareStatement(UserInformation.table1);
         stmt.executeUpdate();
         stmt = connection.prepareStatement(UserInformation.table2);
         stmt.executeUpdate();
         stmt = connection.prepareStatement(UserInformation.table3);
         stmt.executeUpdate();
         stmt = connection.prepareStatement(UserInformation.alter);
         stmt.executeUpdate();
         stmt = connection.prepareStatement(UserInformation.conclude);
         stmt.executeUpdate();
      } catch (Exception e) {
         try {
            // Contingency already ran, use backup db and carry on
            LogSQL.log("Main DB failed. Connection to contingency successful. Selected schema: kevinramsundertwitter");
            stmt = connection.prepareStatement(UserInformation.conclude);
            stmt.executeUpdate();
         } catch (SQLException e1) {
            IO.displayGUI(e.getLocalizedMessage());
         }
         return connection;
      }

      return connection;
   }
}