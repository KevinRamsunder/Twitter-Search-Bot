package Downloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import IO.IO;

/** Class responsible for downloading all files to LOCAL directory */

public class Downloader {

   /** Download file */
   public static String write(String username, String url) {
      // take the url and replace all special characters as
      // to comply to standard filename conventions
      String filename = url.replaceAll("\\\\|\\/|\\:|\\*|\\?|\"|\\<|\\>|\\|",
            ".");

      String path = "downloads\\" + username + "\\";

      // create the file
      String xml = "";
      try {
         URL link = new URL(url); // get url
         File f = new File(path + filename); // get path
         f.getParentFile().mkdirs(); // create path if needed

         // download the html file
         Write.UrlToFile(link.openConnection(), path + filename);

         // get the html from the file
         xml = Write.getHTML(link.openConnection(), path + filename);

         // get images in the html and download them
         downloadFiles(xml, path);
      } catch (IOException e) {
         IO.displayGUI(e.getLocalizedMessage());
      }

      return "Download filename: " + filename;
   }

   /** Parse HTML and download all images */
   private static void downloadFiles(String xml, String path) {
      // Find images
      Pattern tweet = Pattern.compile("<img src=(.*?)(.jpg|.png|.gif)");
      Matcher matcher = tweet.matcher(xml);

      // Download all images
      try {
         while (matcher.find()) {
            // get url
            String url = matcher.group().substring(
                  matcher.group().indexOf("\"") + 1);
            
            // Download each found URL
            Write.UrlToFile(
                  new URL(url).openConnection(),
                  path
                        + url.replaceAll("\\\\|\\/|\\:|\\*|\\?|\"|\\<|\\>|\\|",
                              "."));
         }
      } catch (IOException e) {
         IO.displayGUI(e.getLocalizedMessage());
      }
   }
}