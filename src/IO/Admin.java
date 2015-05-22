package IO;

/** Class responsible for admin operation */

public class Admin {

   // log file name
   private static String logFileName;

   // set log file name
   public static void setLogFile(String name) {
      logFileName = name;
   }

   // open log file and notepad instance
   public static void openLogFile() {
      try {
         Runtime.getRuntime().exec("notepad.exe " + logFileName);
      } catch (Exception e) {
         IO.displayGUI(e.getLocalizedMessage());
      }
   }

   // open log file and notepad instance
   public static void openDownloadLocation(String location) {
      try {
         Runtime.getRuntime().exec("explorer.exe " + location);
      } catch (Exception e) {
         IO.displayGUI(e.getLocalizedMessage());
      }
   }
   
   // clear log file 
   public static void clearLogFile() {
      IO.resetFile();
   }
}
