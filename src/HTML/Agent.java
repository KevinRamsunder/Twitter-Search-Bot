package HTML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import IO.IO;
import IO.LogSQL;

/** Class responsible for connecting to the website */
public class Agent {

   private HttpURLConnection connection; // handles connection to website

   public Agent() {

   }

   /** Get connection */
   public HttpURLConnection getConnection() {
      return connection;
   }

   /** Create search URL based on given parameters */
   public void setSearchTerm(String searchTerm, TweetType type) {
      String url = searchTermToURL(searchTerm, type); // create url
      LogSQL.log("Connecting to URL: " + url + "..."); // log it
      this.startConnection(url); // start the connection based on above url
   }

   /** Get the lines of XML/HTML from the webpage */
   public String getXML() {
      String line; // line for collecting each line of XML/HTML
      StringBuilder builder = new StringBuilder(); // Array for collecting lines

      try {
         // Get InputStream from the connection
         BufferedReader reader = new BufferedReader(new InputStreamReader(
               connection.getInputStream()));

         // keep reading lines until there are none left
         while ((line = reader.readLine()) != null) {
            builder.append(line); // append line to array
         }
      } catch (Exception e) {
         IO.displayGUI(e.getLocalizedMessage());
      }

      // flatten array and return in String format
      return builder.toString();
   }

   /** Initialize connection to website */
   private void startConnection(String url) {
      connection = init(url); // start connection
   }

   /** Connect to the website */
   private HttpURLConnection init(String url) {
      URL link = getURL(url); // convert String to URL
      HttpURLConnection connection = null;

      try {
         // Open the connection to the website based on the URL
         connection = (HttpURLConnection) link.openConnection();
      } catch (IOException e) {
         IO.displayGUI(e.getCause() + "; " + e.getLocalizedMessage());
      }

      LogSQL.log("Connection Successful.");
      return connection;
   }

   /** Create the Twitter URL based on the given parameters */
   private String searchTermToURL(String searchTerm, TweetType type) {
      String term = searchTerm; // plain text search term
      term = term.replaceAll(" ", "%20"); // replace all white spaces with %20
      term = term.replaceAll("#", "%23"); // replace all hashtags with %23
      term = term.replaceAll("@", "%40"); // replace all @ symbols with %40

      // Depending on the type of search requested, determine the format of the
      // URL
      if (type == TweetType.TOP) {
         // 'Top' type tweets
         term = "https://twitter.com/search?q=" + term + "&src=typd";
      } else if (type == TweetType.ALL) {
         // 'All' type tweets
         term = "https://twitter.com/search?f=realtime&q=" + term + "&src=typd";
      } else if (type == TweetType.USER) {
         // 'User' type tweets
         term = "https://twitter.com/search?q=from%3A" + term + "&src=typd";
      }

      return term;
   }

   /** String to URL */
   private URL getURL(String url) {
      URL e = null;
      try {
         e = new URL(url);
      } catch (MalformedURLException e1) {
         IO.displayGUI(e1.getLocalizedMessage());
      }

      return e;
   }
}