package SQL;

/** Holds SQL commands associated with retrieval from MySQL DB */

public abstract class RetrieveQueryGenerator {

   private static final String favID = "/**RETRIEVE Get tweets sorted by favorites*/";
   public static final String getRowsSortedByFavorites = favID + " select * from tweets order by favorites DESC;";
   
   private static final String retweetsID = "/**RETRIEVE Get tweets sorted by retweets*/";
   public static final String getRowsSortedByRetweets = retweetsID + " select * from tweets order by retweets DESC;";

   private static final String recentID = "/**RETRIEVE get tweets sorted by most recent*/";
   public static final String getMostRecent = recentID + " select * from tweets order by mstimeOf desc;";
   
   private static final String wordsID = "/**RETRIEVE get tweets with term:*/";
   public static final String getTweetsWithWords = wordsID + "select * from tweets where message like ?;";
   
   private static final String tweetUserID = "/**RETRIEVE get tweets from user:*/";
   public static final String tweetsFromUsers = tweetUserID + "select * from tweets where username = ?;";
   
   private static final String usersID = "/**Get every user in the database*/";
   public static final String allUsers = usersID + "select * from users";
   
   private static final String tagsID = "/**Get all hashtags*/";
   public static final String allTags = tagsID + "select * from tags order by count DESC";
}