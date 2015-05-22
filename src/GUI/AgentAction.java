package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import HTML.Agent;
import HTML.Parser;
import HTML.TweetType;
import IO.IO;
import SQL.InsertSQL;

/** Class responsible for querying Twitter and parsing results */

public class AgentAction implements ActionListener {

   private Agent agent; // agent to connect to website
   private Parser parser; // object which parses html

   private Submit submit; // objects responsible for submitting query

   public AgentAction(Submit submit) {
      this.submit = submit; // submit elements
      this.agent = new Agent(); // web agent
      this.parser = new Parser(); // parsing package
   }

   /** Action performed operations */
   @Override
   public void actionPerformed(ActionEvent arg0) {
      // Start on background thread
      Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {
            // process user input
            processInput();
         }
      });

      thread.start(); // start thread
   }

   /** Get user input and parse */
   private void processInput() {
      // Do not block main GUI thread */
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            // disable buttons
            submit.setInterfaceStatus(!submit.getStatus());
         }
      });

      // Determine the query and type from user input
      String query = submit.getTextField().getText();
      TweetType type = submit.getButtons().getClicked();

      // connect to website and parse
      if (type == TweetType.ALL) {
         multiSearch(query, type);
      } else {
         agent.setSearchTerm(query, type);
         parser.parseXML(agent.getXML());
      }

      // insert all newly found tweets
      try {
         // insert new tweets to MySQL server
         InsertSQL.addUsersAndTweets(parser);
         InsertSQL.addTags(parser);
         IO.displayGUI(parser.getCurrentCount() + " new tweets found in this session.");

         // start GUI for displaying table
         new Table(parser);
      } catch (SQLException e) {
         IO.displayGUI(e.getLocalizedMessage());
      }

      // Do not block main GUI thread
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            // re-enable all buttons
            submit.getTextField().setText("");
            submit.getButton().setText("Submit");
            submit.setInterfaceStatus(!submit.getStatus());
         }
      });
   }

   /** Multi-page search (optional) */
   private void multiSearch(String query, TweetType type) {
      String message = "Tweets from the 'ALL' category are continuously updated.\nWould you like to gather more than one page of search results?";
      int i = JOptionPane.showConfirmDialog(null, message);

      if (i == JOptionPane.YES_OPTION) {
         int val = 0;
         String value = JOptionPane.showInputDialog(null,
               "How many pages of search results would you like?");

         try {
            val = Integer.valueOf(value);
         } catch (Exception e) {
            IO.displayGUI(e.getLocalizedMessage()
                  + "\nReverting to normal operation...");
         }

         int count = 0;
         parser.setMultiTweetMode(true);
         IO.displayGUI(val + " searches will be performed on a 15 second interval.\n(Press ok to begin.)");
         while (count < val) {
            agent.setSearchTerm(query, type);
            parser.parseXML(agent.getXML());
            pause(1000 * 15);
            count++;
         }
         parser.setMultiTweetMode(false);
      } else {
         agent.setSearchTerm(query, type);
         parser.parseXML(agent.getXML());
      }
   }

   /** Pause thread for certain amount of time */
   private void pause(int milliseconds) {
      CountDownLatch cdl = new CountDownLatch(1);

      try {
         cdl.await(milliseconds, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
         IO.displayGUI(e.getLocalizedMessage());
      }
   }
}