package SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import SQL.RetrieveQueryGenerator;
import IO.LogSQL;
import IO.ResultSetProcessor;

/** Class responsible for retrieving information from MySQL database */

public class RetrieveSQL extends SQL {

   /** Get all tweets, sort by favorites */
   public static void getTweetsSortedByFavorite() throws SQLException {
      // Get the query and execute
      String query = RetrieveQueryGenerator.getRowsSortedByFavorites;
      PreparedStatement retrieve = connection.prepareStatement(query);

      LogSQL.log(retrieve); // log
      execute(retrieve); // execute
   }

   /** Get all tweets, sort by retweets */
   public static void getTweetsSortedByRetweets() throws SQLException {
      // Get the query
      String query = RetrieveQueryGenerator.getRowsSortedByRetweets;
      PreparedStatement retrieve = connection.prepareStatement(query);

      LogSQL.log(retrieve); // log
      execute(retrieve); // execute
   }

   /** Get all tweets, sort by most recent */
   public static void getMostRecent() throws SQLException {
      // Get the query and execute
      String query = RetrieveQueryGenerator.getMostRecent;
      PreparedStatement retrieve = connection.prepareStatement(query);

      LogSQL.log(retrieve); // log
      execute(retrieve); // execute
   }

   /** Get all tweets with given keyword */
   public static void getTweetsWithWords(String term) throws SQLException {
      // Get the query
      String query = RetrieveQueryGenerator.getTweetsWithWords;
      PreparedStatement retrieve = connection.prepareStatement(query);

      // Add term to query
      retrieve.setString(1, '%' + term + '%');

      LogSQL.log(retrieve, term); // log
      execute(retrieve); // execute
   }

   /** Get all tweets from given user */
   public static void getTweetsFromUser(String user) throws SQLException {
      // Get the query and execute
      String query = RetrieveQueryGenerator.tweetsFromUsers;
      PreparedStatement retrieve = connection.prepareStatement(query);

      // Add term to query
      retrieve.setString(1, user);

      LogSQL.log(retrieve, user); // log
      execute(retrieve);// execute
   }

   /** Get all users */
   public static void getAllUsers() throws SQLException {
      // Get the query and execute
      String query = RetrieveQueryGenerator.allUsers;
      PreparedStatement retrieve = connection.prepareStatement(query);

      LogSQL.log(retrieve); // log
      execute(retrieve);// execute
   }

   /** Get all tags */
   public static void getAllTags() throws SQLException {
      // Get the query and execute
      String query = RetrieveQueryGenerator.allTags;
      PreparedStatement retrieve = connection.prepareStatement(query);

      LogSQL.log(retrieve); // log
      execute(retrieve);// execute
   }

   /** Execute, and process resultSet */
   private static void execute(PreparedStatement retrieve) throws SQLException {
      ResultSet result = retrieve.executeQuery();
      ResultSetProcessor.print(result);
   }
}