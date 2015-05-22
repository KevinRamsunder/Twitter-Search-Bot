package SQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import IO.LogSQL;

/** Class responsible for deleting items from MySQL database */

public class DeleteSQL extends SQL {

   /** Delete all tweets */
   public static void deleteAllTweets() throws SQLException {
      // Get the query
      String query = DeleteQueryGenerator.deleteTweets;
      PreparedStatement delete = connection.prepareStatement(query);

      LogSQL.log(delete); // log
      execute(delete); // execute
   }
   
   /** Delete all tags */
   public static void deleteTags() throws SQLException {
      // Get the query
      String query = DeleteQueryGenerator.deleteTags;
      PreparedStatement delete = connection.prepareStatement(query);

      LogSQL.log(delete); // log
      execute(delete); // execute
   }

   /** Execute delete PreparedStatement */
   private static void execute(PreparedStatement delete) throws SQLException {
      delete.executeUpdate();
   }
}