package HTML;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Class for parsing information */

public class Parser {

   private String xml; // main html
   private int currentCount = 0; // count of new tweets
   private HashMap<String, Tweet> tweets; // container for tweets
   private HashMap<String, Integer> tags; // container for tags
   private HashMap<String, Tweet> currentTweets; // container for currentTweets
   private boolean multiMode = false; // mode of parser, default is singleMode

   public Parser() {
      // init
      tweets = new HashMap<String, Tweet>();
      tags = new HashMap<String, Integer>();
      currentTweets = new HashMap<String, Tweet>();
   }

   /** Get xml and parse it */
   public void parseXML(String xml) {
      this.xml = xml;
      extractTweets();
   }

   /** Set parser mode */
   public void setMultiTweetMode(boolean state) {
      this.multiMode = state;
      if (state) {
         currentCount = 0;
         currentTweets = new HashMap<String, Tweet>();
      }
   }

   /** Getters */
   public HashMap<String, Tweet> getTweets() {
      return tweets;
   }

   public HashMap<String, Tweet> getCurrentTweets() {
      return currentTweets;
   }

   public HashMap<String, Integer> getTags() {
      return tags;
   }

   public int getCurrentCount() {
      return this.currentCount;
   }

   /** Parse tweets from xml */
   private void extractTweets() {
      // reset values
      if (!multiMode) {
         currentCount = 0;
         currentTweets = new HashMap<String, Tweet>();
      }

      // pattern needed to extract info
      Pattern tweet = Pattern.compile(Patterns.tweets);
      Matcher matcher = tweet.matcher(xml);

      // while matchers are found, process them
      while (matcher.find()) {
         // get content
         String tweetContent = matcher.group();
         Tweet twitterContent = new Tweet(tweetContent);

         // error found, quit
         if (twitterContent.getUsername() == null
               || twitterContent.getUsername().equals("")) {
            continue;
         }
         // get key
         String key = twitterContent.getUrlToTweet();

         // perform analysis on new tweets only
         if (!tweets.containsKey(key)) {
            // add to container
            currentCount++;
            addUniqueTweets(key, twitterContent);
            addTags(twitterContent);
         }
      }
   }

   /** add tweets to container */
   private void addUniqueTweets(String key, Tweet twitterContent) {
      this.tweets.put(key, twitterContent);
      this.currentTweets.put(key, twitterContent);
   }

   /** add tags to container, if tag exists, increment count */
   private void addTags(Tweet twitterContent) {
      LinkedList<String> hashtags = twitterContent.getMessageInfo()
            .getHashtags();

      for (int i = 0; i < hashtags.size(); i++) {
         String keyValue = hashtags.get(i);

         // new tag, count = 1
         if (!tags.keySet().contains(keyValue)) {
            tags.put(keyValue, new Integer(1));
         } else {
            // existing tag, increment count
            tags.put(keyValue, tags.get(keyValue) + 1);
         }
      }
   }
}