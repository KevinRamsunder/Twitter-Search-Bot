package HTML;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Class for parsing message information */

public class Message {

   private String message; // main message
   private LinkedList<String> hashtags; // extracted hashtags
   private LinkedList<String> atReferences; // extracted @ refs

   public Message(String messageText) {
      // parse details
      parse(messageText);

      // init containers
      hashtags = new LinkedList<String>();
      atReferences = new LinkedList<String>();

      // fill containers
      extractTags();
      extractReferences();
   }

   /** Clean up message content */
   private void parse(String messageText) {
      String tweetContent = messageText;
      tweetContent = tweetContent.replaceAll("(<(.*?)>|&nbsp;)", "");
      tweetContent = tweetContent.replaceAll("&#10;|…", " ");
      tweetContent = tweetContent.replaceAll("&amp;", "&");
      tweetContent = tweetContent.replaceAll("&#39;|’", "'");
      tweetContent = tweetContent.replaceAll("&quot;", "\"");
      this.message = tweetContent;
   }

   /** Get @ refs */
   private void extractReferences() {
      Pattern pattern = Pattern.compile("@(.*?)(\\s|$)");
      Matcher matcher = pattern.matcher(message);

      while (matcher.find()) {
         atReferences.add(matcher.group().replaceAll(
               "\\\\|\\/|\\:|\\*|\\?|\"|\\<|\\>|\\!|\\.|\\,|\\&", ""));
      }
   }

   /** Get hashtags */
   private void extractTags() {
      Pattern pattern = Pattern.compile("#(.*?)(\\s|$)");
      Matcher matcher = pattern.matcher(message);

      while (matcher.find()) {
         hashtags
               .add(matcher
                     .group()
                     .replaceAll(
                           "\\\\|\\/|\\:|\\*|\\?|\"|\\<|\\>|\\!|\\.|\\,|\\&| |\\)|\\(",
                           ""));
      }
   }
   
   /** Getters */
   public String getMessage() {
      return message;
   }

   public LinkedList<String> getHashtags() {
      return hashtags;
   }

   public LinkedList<String> getAtReferences() {
      return atReferences;
   }
}