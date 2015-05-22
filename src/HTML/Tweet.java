package HTML;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Uses regex to parse HTML information */

public class Tweet {

   /** Information from HTML */
   private String title;
   private String username;
   private String message;
   private String hrefMessage;
   private String urlAvatar;
   private String urlToTweet;
   private long msTimestamp;
   private Message messageParser;
   private int retweets = 0;
   private int favorites = 0;

   public Tweet(String tweetContent) {
      /** Extract all necessary information */
      extractUsername(tweetContent);

      // error, exit
      if (username == null || username.equals("")) {
         return;
      }

      extractTitle(tweetContent);
      extractMessage(tweetContent);
      extractTime(tweetContent);
      extractAvatar(tweetContent);
      extractLinkToTweet(tweetContent);
      extractStats(tweetContent);
   }

   /** Getters */
   public Message getMessageInfo() {
      return messageParser;
   }

   public String getUrlToProfile() {
      return "https://twitter.com/" + this.username;
   }

   public String getHrefMessage() {
      return hrefMessage;
   }

   public int getRetweets() {
      return retweets;
   }

   public int getFavorites() {
      return favorites;
   }

   public String getTitle() {
      return title;
   }

   public String getUsername() {
      return username;
   }

   public String getMessage() {
      return message;
   }

   public long getMsTimestamp() {
      return msTimestamp;
   }

   public String getUrlAvatar() {
      return urlAvatar;
   }

   public String getUrlToTweet() {
      return urlToTweet;
   }

   /** Extract title from html */
   private void extractTitle(String tweetContent) {
      Pattern username = Pattern.compile(Patterns.title);
      Matcher matcher = username.matcher(tweetContent);

      while (matcher.find()) {
         String htmlUsername = matcher.group();
         int firstIndex = htmlUsername.indexOf(">");
         int secondIndex = htmlUsername.lastIndexOf("<");
         this.title = (matcher.group().substring(firstIndex + 1, secondIndex));
      }
   }

   /** Extract title from username */
   private void extractUsername(String tweetContent) {
      Pattern username = Pattern.compile(Patterns.username);
      Matcher matcher = username.matcher(tweetContent);

      while (matcher.find()) {
         String htmlUsername = matcher.group();
         int firstIndex = htmlUsername.indexOf("<b>");
         int secondIndex = htmlUsername.indexOf("</b>");
         this.username = (matcher.group()
               .substring(firstIndex + 3, secondIndex));
      }
   }

   /** Extract message from html */
   private void extractMessage(String tweetContent) {
      Pattern tweet = Pattern.compile(Patterns.tweetContent);
      Matcher matcher = tweet.matcher(tweetContent);

      while (matcher.find()) {
         String htmlContent = matcher.group();

         int tagIndex1 = htmlContent.indexOf('>');
         int tagIndex2 = htmlContent.lastIndexOf('<');
         tweetContent = htmlContent.substring(tagIndex1 + 1, tagIndex2);
      }

      this.hrefMessage = tweetContent;
      this.messageParser = new Message(tweetContent);
      this.message = this.messageParser.getMessage();
   }

   /** Extract time from html */
   private void extractTime(String tweetContent) {
      Pattern time = Pattern.compile(Patterns.time);
      Matcher matcher = time.matcher(tweetContent);

      while (matcher.find()) {
         String timeNumber = matcher.group();
         int index = timeNumber.indexOf("\"");
         int index2 = timeNumber.length() - 1;

         this.msTimestamp = Long.valueOf(timeNumber
               .substring(index + 1, index2));
      }
   }

   /** Extract avatar from html */
   private void extractAvatar(String tweetContent) {
      Pattern tweet = Pattern.compile(Patterns.avatar);
      Matcher matcher = tweet.matcher(tweetContent);

      while (matcher.find()) {
         String htmlContent = matcher.group();

         int firstIndex = htmlContent.indexOf("src=\"");
         int secondIndex = htmlContent.indexOf("\"", firstIndex + 6);
         this.urlAvatar = (htmlContent.substring(firstIndex + 5, secondIndex));
      }
   }

   /** Extract link from html */
   private void extractLinkToTweet(String tweetContent) {
      Pattern tweet = Pattern.compile(Patterns.href);
      Matcher matcher = tweet.matcher(tweetContent);

      matcher.find();

      String htmlContent = matcher.group();

      int firstIndex = htmlContent.indexOf("<a href=\"/");
      int secondIndex = htmlContent.lastIndexOf("\"");
      this.urlToTweet = (matcher.group().substring(firstIndex + 9, secondIndex));
      this.urlToTweet = "https://twitter.com" + urlToTweet;
   }

   /** Extract stats from html */
   private void extractStats(String tweetContent) {
      Pattern pattern = Pattern.compile("data-tweet-stat-count=\"(.*?)\">");
      Matcher matcher = pattern.matcher(tweetContent);

      if (matcher.find()) {
         String content = matcher.group();
         int firstIndex = content.indexOf("\"");
         int secondIndex = content.lastIndexOf(">");
         String count = content.substring(firstIndex + 1, secondIndex - 1);
         retweets = Integer.parseInt(count);
      } else {
         retweets = -1;
      }

      if (matcher.find()) {
         String content = matcher.group();
         int firstIndex = content.indexOf("\"");
         int secondIndex = content.lastIndexOf(">");
         String count = content.substring(firstIndex + 1, secondIndex - 1);
         favorites = Integer.parseInt(count);
      } else {
         favorites = -1;
      }
   }
}