# Twitter Search Bot

###About
Class project for querying and storing tweets to a database based on user input. A GUI is provided for easy user interaction.


###Features
This is a Java application that remotely queries Twitter search results without any dedicated third party html-scraping library. Based on class requirements, HTML parsing was done with REGEX instead of the more conventional XPATH/CSS. 

Search requests can be parameterized based on options given on the Twitter website. Users can search by most current tweet, top tweets, or tweets from a certain user.

It is able to store all relevant information from the tweet into a local MySQL database, which is required for successful execution of this program. These tweets can then be retrieved from the database based on a number of filters, such as sort by most retweeted, sort by most favorited, or get tweets from a certain user.