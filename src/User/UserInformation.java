package User;

/** Holds important user information for database access */

public class UserInformation {

   /** User information */
   public static final String user = "root";
   public static final String pass = "root";

   /** Database Access Information */
   private static final String dbName = "twitter";
   private static final String dbHostMachine = "0.0.0.0"; // ip address of host machine
   private static final String port = "3306"; // port number on host machine

   /** URL needed to connect to remote database */
   public static final String url = "jdbc:mysql://" + dbHostMachine + ":" + port + "/" + dbName;

   /** backup URL */
   public static final String backupURL = "jdbc:mysql://127.0.0.1:3306";
   public static final String createSchema = "CREATE SCHEMA `twitterbotschema`;";
   public static final String table1 = "CREATE TABLE `twitterbotschema`.`tweets` (  `username` VARCHAR(30) NOT NULL,  `message` VARCHAR(200) NOT NULL,  `linkToTweet` VARCHAR(125) NOT NULL,  `mstimeOf` VARCHAR(25) NOT NULL,  `retweets` INT(11) NOT NULL,  `favorites` INT(11) NOT NULL,  PRIMARY KEY (`username`, `linkToTweet`));";
   public static final String table2 = "CREATE TABLE `twitterbotschema`.`tags` (  `hashtag` VARCHAR(50) NOT NULL,  `count` INT(11) NOT NULL DEFAULT '1',  PRIMARY KEY (`hashtag`));";
   public static final String table3 = "CREATE TABLE `twitterbotschema`.`users` (  `username` VARCHAR(30) NOT NULL,  `displayName` VARCHAR(30) NOT NULL,  `linkToAvatar` VARCHAR(125) NOT NULL, `linkToProfile` VARCHAR(125) NOT NULL,  PRIMARY KEY (`username`));";
   public static final String alter = "ALTER TABLE `twitterbotschema`.`tweets` ADD CONSTRAINT `username`  FOREIGN KEY (`username`)  REFERENCES `twitterbotschema`.`users` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION;";
   public static final String conclude = "use twitterbotschema;";
}