import java.sql.SQLException;

import GUI.MainGUI;
import IO.Admin;
import IO.IO;
import IO.LogSQL;
import SQL.SQL;

/** Main Class for the program */
public class TwitterBot {

   public static void main(String[] args) {
      // Process input arguments
      processArguments(args);

      // Start main program
      try {
         
         // start connection
         SQL.startMySQL();

         // start GUI for user
         new MainGUI();

      } catch (SQLException e) {
         // log error
         LogSQL.log(e.getLocalizedMessage());
      }
   }

   /** Process input arguments */
   private static void processArguments(String[] args) {
      if (args.length < 1) {
         IO.displayGUI(args.length + " arguments provided.\nPlease provide output log file through the GUI.");
         IO.chooseFiles();
      } else {
         IO.openStream(args[0]);
         Admin.setLogFile(args[0]);
      }
   }
}
