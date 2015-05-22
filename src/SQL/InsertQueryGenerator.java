package SQL;

/** Holds SQL commands associated with insertion to MySQL DB */

public abstract class InsertQueryGenerator {

   private static final String userCategories = "INSERT IGNORE INTO users(username, displayName, linkToAvatar, linkToProfile)";
   
   public static final String addUser = userCategories + "\n   values (?,?,?,?);";
   
   /** Denote which columns you will be inserting into */
   private static final String categories = "INSERT IGNORE INTO tweets(username, message, linkToTweet, msTimeOf, retweets, favorites)";
   
   /** Insert Tweet, add each attribute as parameters */
   public static final String addTweet = categories + "\n   values (?,?,?,?,?,?);";
   
   public static final String addTag = "insert into tags(hashtag, count) values (?, ?) on duplicate key update hashtag = values(hashtag), count = count+?;";
}
