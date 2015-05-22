package SQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import HTML.Parser;
import HTML.Tweet;
import IO.LogSQL;

/** Class responsible for inserting courses into MySQL database */

public class InsertSQL extends SQL {

   /** Add tags to the database */
   public static void addTags(Parser parser) throws SQLException {
      HashMap<String, Integer> tweets = parser.getTags();
      for (String s : tweets.keySet()) {
         addTag(s, tweets.get(s));
      }
   }
   
   /** Add tag */
   private static void addTag(String tag, int count) throws SQLException {
      String query = InsertQueryGenerator.addTag;
      PreparedStatement insert = connection.prepareStatement(query);
      
      insert.setString(1, tag);
      insert.setInt(2, count);
      insert.setInt(3, count);

      execute(insert);
   }
   
   /** Add a tweets to the database */
   public static void addUsersAndTweets(Parser parser) throws SQLException {
      HashMap<String, Tweet> tweets = parser.getCurrentTweets();

      for (String s : tweets.keySet()) {
         Tweet t = tweets.get(s);
         
         if(t.getUsername().equals("") || t.getUsername() == null) {
            continue;
         }
         
         addUser(t);
         addTweet(t);
      }
   }

   /** Add user to database */
   private static void addUser(Tweet t) throws SQLException {
      String query = InsertQueryGenerator.addUser;
      PreparedStatement insert = connection.prepareStatement(query);
      
      insert.setString(1, t.getUsername());
      insert.setString(2, t.getTitle());
      insert.setString(3, t.getUrlAvatar());
      insert.setString(4, t.getUrlToProfile());

      execute(insert);
   }
   
   /** Add tweet to database */
   private static void addTweet(Tweet t) throws SQLException {
      String query = InsertQueryGenerator.addTweet;
      PreparedStatement insert = connection.prepareStatement(query);

      insert.setString(1, t.getUsername());
      insert.setString(2, t.getMessage());
      insert.setString(3, t.getUrlToTweet());
      insert.setString(4, t.getMsTimestamp() + "");
      insert.setString(5, t.getRetweets() + "");
      insert.setString(6, t.getFavorites() + "");
      
      execute(insert);
   }

   /** Execute and log PreparedStatement */
   private static void execute(PreparedStatement insert) throws SQLException {
      LogSQL.log(insert);
      insert.executeUpdate();
   }
}