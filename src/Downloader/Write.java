package Downloader;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;

/** Class to write websites to local system file */
public class Write {

   public static void UrlToFile(URLConnection connection, String filename) {
      if (isHTML(connection)) {
         // html file, write lines of code to file
         writeHtmlToFile(connection, filename);
      } else {
         // write image file or other type to local file
         writeOtherToFile(connection, filename);
      }
   }

   /** Check if URLConnection is .HTML or .HTM file type */
   private static boolean isHTML(URLConnection connection) {
      return connection.getContentType().contains("htm");
   }

   /** Write html lines to a local file on the system */
   public static String getHTML(URLConnection connection, String filename) {
      // open streams
      BufferedReader reader = null;
      StringBuilder builder = new StringBuilder();

      // open streams to write to file
      try {
         String line = "";
         
         // open file streams
         connection = connection.getURL().openConnection();

         reader = new BufferedReader(new InputStreamReader(
               connection.getInputStream()));

         // write each line to file
         line = reader.readLine();
         while (line != null) {
            builder.append(line = reader.readLine());
         }

         reader.close();

      } catch (IOException e) {
         System.out.println(e.getLocalizedMessage());
      }
      
      return builder.toString();
   }
   
   /** Write html lines to a local file on the system */
   private static void writeHtmlToFile(URLConnection connection, String filename) {
      // open streams
      PrintWriter pw = null;
      BufferedReader reader = null;

      // open streams to write to file
      try {
         String line = "";
         // open file streams
         connection = connection.getURL().openConnection();
         pw = new PrintWriter(new File(filename + ".html"));
         reader = new BufferedReader(new InputStreamReader(
               connection.getInputStream()));

         // write each line to file
         line = reader.readLine();
         while (line != null) {
            pw.write(line + "\r\n");
            line = reader.readLine();
         }

         pw.close(); // close streams
         reader.close();

      } catch (IOException e) {
         System.out.println(e.getLocalizedMessage());
      }
   }

   /** Write images or other file types to local system */
   private static void writeOtherToFile(URLConnection connection,
         String filename) {
      // get file extension
      String extension = getExtension(connection);

      try {
         // open the URL stream to get the input stream
         InputStream inputStream = connection.getURL().openStream();

         // download file to directory
         // consult:
         // http://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html
         Files.copy(inputStream, new File(filename + extension).toPath());
      } catch (IOException e) {
         System.out.println(e.getLocalizedMessage());
      }
   }

   /** get file extension (.jpg, .pdf, etc) */
   private static String getExtension(URLConnection connection) {
      String url = connection.getURL().toString();
      return url.substring(url.lastIndexOf('.'));
   }
}