package SQL;

/** Holds SQL commands associated with deletion from MySQL DB */

public abstract class DeleteQueryGenerator {

   /** Delete entire tweets table */
   private static final String tweetsID = "/**Clear table: tweets*/";
   public static final String deleteTweets = tweetsID + "truncate tweets;";
   
   /** Delete entire tags table */
   private static final String tagsID = "/**Clear table: tags*/";
   public static final String deleteTags = tagsID + "truncate tags";
}