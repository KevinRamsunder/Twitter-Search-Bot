package HTML;

/** REGEX patterns for parsing HTML */

public class Patterns {

   public static final String tweets = "<div class=\"content\">(.*?)</li>(.*?)<li class=\"js";
   
   public static final String title = "<strong class=\"fullname js-action-profile-name show-popup-with-id\" data-aria-label-part>(.*?)</strong>";
   public static final String username = "<span class=\"username js-action-profile-name\" data-aria-label-part><s>@</s><b>(.*?)</b></span>";
   public static final String tweetContent = "<p class=\"TweetTextSize(.*?)</p>";
   public static final String time = "data-time-ms=\"(.*?)\"";
   public static final String avatar = "<img class=\"avatar js-action-profile-avatar\" src=\"(.*?)>";
   public static final String href = "<a href=\"/(.*?)\"";
}
