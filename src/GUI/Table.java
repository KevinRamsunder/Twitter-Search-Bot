package GUI;

import java.awt.BorderLayout;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import HTML.Parser;
import HTML.Tweet;
import IO.IO;

/** Class responsible for displaying database info to user */

public class Table {

   // GUI elements for table
   private JFrame frame;
   private JTable table;
   private Parser parser;
   private ResultSet rs;
   private ResultSetMetaData rsmd;
   private TableMenu menuBar;

   /** Constructor for SQL query result sets */
   public Table(ResultSet rs, ResultSetMetaData resultSetMetaData) {
      this.rs = rs;
      this.rsmd = resultSetMetaData;

      // start GUI
      init();
   }

   /** Constructor for HTML parsed information */
   public Table(Parser parser) {
      this.parser = parser;

      // error detected, skip this tweet
      if (parser.getCurrentCount() == 0) {
         return;
      }

      // start GUI
      init();
   }

   /** Start GUI elements */
   private void init() {
      // Start frame and set icon
      frame = new JFrame();
      frame.setBounds(100, 100, 750, 400);
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      ImageIcon img = new ImageIcon("twitterFavicon.png");
      frame.setIconImage(img.getImage());

      // Start scroll pane
      JScrollPane scrollPane = new JScrollPane();

      // Start table
      table = new JTable();
      table.setFillsViewportHeight(true);
      table.setCellSelectionEnabled(true);
      table.setColumnSelectionAllowed(true);

      // add table to scrollpane
      scrollPane.setViewportView(table);
      frame.getContentPane().setLayout(new BorderLayout(0, 0));
      frame.getContentPane().add(scrollPane);

      // determine type of result
      if (parser == null) {
         initResultTable();
      } else {
         menuBar = new TableMenu(frame, parser);
         initTweetTable();
      }

      frame.setVisible(true);
   }

   /** Result Table for SQL query */
   private void initResultTable() {
      String[] columnTitle = null;
      Object[][] tableModel = null;

      try {
         // get row count
         int rows = 0;
         while (rs.next()) {
            ++rows;
         }

         // error, quit
         if (rows == 0) {
            return;
         }
         // restart result set
         rs.beforeFirst();

         // load column labels
         columnTitle = new String[rsmd.getColumnCount()];
         int counter = 0;
         int currentColumn = 1;
         int columnCount = rsmd.getColumnCount();

         while (currentColumn <= columnCount) {
            columnTitle[counter++] = rsmd.getColumnLabel(currentColumn);
            currentColumn++;
         }

         currentColumn = 1;
         columnCount = rsmd.getColumnCount(); // index of last column

         // set title
         frame.setTitle(rows + " Results found.");

         tableModel = new Object[rows][columnCount];

         // Get information and fill it in the table
         int counter2 = 0;
         int counter3 = 0;
         while (rs.next()) {
            currentColumn = 1;
            while (currentColumn <= columnCount) {
               if (rsmd.getColumnName(currentColumn).equals("mstimeOf")) {
                  String time = rs.getString(currentColumn);
                  long msTime = Long.valueOf(time);
                  tableModel[counter2][counter3++] = (new Date(msTime));
               } else {
                  tableModel[counter2][counter3++] = (rs
                        .getString(currentColumn));
               }
               currentColumn++;
            }
            counter2++;
            counter3 = 0;
         }
      } catch (SQLException e) {
         IO.displayGUI(e.getLocalizedMessage());
      }

      // set model to table
      table.setModel(new DefaultTableModel(tableModel, columnTitle));
   }

   /** Get HTML results and put in table */
   private void initTweetTable() {
      // set title
      frame.setTitle(parser.getCurrentCount() + " Tweets found.");

      // get results
      HashMap<String, Tweet> content = parser.getCurrentTweets();

      // set titles
      String[] columnTitle = new String[] { "Username", "Message",
            "linkToTweet", "msTimeOf", "# of Retweets", "# of Favorites" };

      // get row and column size
      int rows = parser.getCurrentCount();
      int columns = columnTitle.length;

      // start content
      Object[][] tableModel = new Object[rows][columns];

      // fill content with data
      int i = 0;
      int j = 0;
      for (String s : content.keySet()) {
         Tweet t = content.get(s);

         tableModel[i][j++] = t.getUsername();
         tableModel[i][j++] = t.getMessage();
         tableModel[i][j++] = t.getUrlToTweet();
         tableModel[i][j++] = new Date(t.getMsTimestamp());
         tableModel[i][j++] = t.getRetweets();
         tableModel[i][j++] = t.getFavorites();

         i++;
         j = 0;
      }

      // set model to table
      table.setModel(new DefaultTableModel(tableModel, columnTitle));
   }
}
