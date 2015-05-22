package IO;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/** Responsible for logging commands that are executed by user */

public abstract class LogSQL {

   /** log given message */
   public static void log(String message) {
      IO.log(timestamp() + "\r\n");
      IO.log("   " + message + "\r\n");
      IO.log("\r\n");
   }

   /** log sql command from given PreparedStatement */
   public static void log(PreparedStatement stmt) {
      String statement = stmtToDescription(stmt);
      log(statement);
   }

   /** log sql command from given PreparedStatement */
   public static void log(PreparedStatement stmt, String parameter) {
      String statement = stmtToDescription(stmt);
      log(statement + " " + parameter);
   }

   /** log sql command from given PreparedStatement */
   public static void log(PreparedStatement stmt, int parameter) {
      String statement = stmtToDescription(stmt);
      log(statement + " " + Integer.toString(parameter));
   }

   /**
    * Generate a timestamp which includes the date and time.
    * 
    * @return timestamp which indicates date and time.
    */
   private static String timestamp() {
      Calendar c = Calendar.getInstance();
      // Construct info with MM--dd-yyyy hh:mm:ss (AM/PM)
      SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh.mm.ss a");
      return df.format(c.getTime());
   }

   /**
    * PreparedStatements used in this program start with a descriptive header
    * comment. This method returns the header information in string format.
    * 
    * If no explanatory comment is found, the SQL command is extracted from the
    * PreparedStatement and displayed instead. This is useful for long commands
    * and is included by design, especially in the INSERT commands.
    * 
    * @param stmt
    *           PreparedStatement object
    * @return query in string format
    */
   private static String stmtToDescription(PreparedStatement stmt) {
      // Parse PreparedStatement, go to first colon and extract SQL command
      String statement = stmt.toString();
      statement = statement.substring(statement.indexOf(':') + 2);

      // If SQL is missing explanatory comment, return the actual command
      if (!statement.startsWith("/**")) {
         return statement;
      }

      // index of second asterisk, marking beginning of comment
      int indexOfComment1 = statement.indexOf('/') + 3;

      // index of last asterisk, marking ending of comment
      int indexOfComment2 = statement.indexOf('/', indexOfComment1) - 1;

      // Extract explanatory comment
      return statement.substring(indexOfComment1, indexOfComment2);
   }
}