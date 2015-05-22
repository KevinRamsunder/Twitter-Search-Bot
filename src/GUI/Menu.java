package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import IO.Admin;
import IO.IO;
import SQL.DeleteSQL;
import SQL.RetrieveSQL;

public class Menu {

   private JFrame frame; // frame
   private JMenu select; // first menu
   private JMenu delete; // second menu
   private JMenu admin; // third menu

   public Menu(JFrame frame) {
      this.frame = frame;
      init(); // start
   }

   /** Start GUI elements */
   private void init() {
      JMenuBar menuBar = new JMenuBar();
      menuBar.setBounds(0, 0, 434, 21);
      select = new JMenu("Select"); // first menu
      delete = new JMenu("Delete"); // second menu
      admin = new JMenu("Admin"); // admin menu

      /** Action for viewing log file */
      JMenuItem a1 = new JMenuItem("View log file");
      a1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Admin.openLogFile();
         }
      });

      /** Action to clear log file */
      JMenuItem a2 = new JMenuItem("Clear log file");
      a2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent arg0) {
            int i = JOptionPane.showConfirmDialog(null,
                  "Are you sure you want to clear the log file?");
            if (i == JOptionPane.YES_OPTION) {
               Admin.clearLogFile();
            }
         }
      });

      /** Open download folder */
      JMenuItem a3 = new JMenuItem("Open download folder");
      a3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new File("downloads\\").mkdirs();
            Admin.openDownloadLocation(new File("downloads\\")
                  .getAbsolutePath());
         }
      });

      /** Action for sorting by favorite tweets */
      JMenuItem fave = new JMenuItem("Get Most Favorited Tweets");
      fave.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent arg0) {
            try {
               RetrieveSQL.getTweetsSortedByFavorite();
            } catch (SQLException e) {
               IO.displayGUI(e.getLocalizedMessage());
            }
         }
      });

      /** Action for sorting by retweeted tweets */
      JMenuItem retwt = new JMenuItem("Get Most Retweeted Tweets");
      retwt.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               RetrieveSQL.getTweetsSortedByRetweets();
            } catch (SQLException e1) {
               IO.displayGUI(e1.getLocalizedMessage());
            }
         }
      });

      /** Action for getting most recent tweets */
      JMenuItem recent = new JMenuItem("Get Recent Tweets");
      recent.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               RetrieveSQL.getMostRecent();
            } catch (SQLException e1) {
               IO.displayGUI(e1.getLocalizedMessage());
            }
         }
      });

      /** Action for getting tweets with given keywords */
      JMenuItem query = new JMenuItem("Get Tweets With Words...");
      query.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               String keyword = IO.queryUser("Enter keyword:");
               RetrieveSQL.getTweetsWithWords(keyword);
            } catch (SQLException e1) {
               IO.displayGUI(e1.getLocalizedMessage());
            }
         }
      });

      /** Action for getting tweets from certain users */
      JMenuItem user = new JMenuItem("Get Tweets From User...");
      user.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               String username = IO.queryUser("Enter username:");
               RetrieveSQL.getTweetsFromUser(username);
            } catch (SQLException e1) {
               IO.displayGUI(e1.getLocalizedMessage());
            }
         }
      });

      /** Action for getting all users */
      JMenuItem allUser = new JMenuItem("Get All Users");
      allUser.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               RetrieveSQL.getAllUsers();
            } catch (SQLException e1) {
               IO.displayGUI(e1.getLocalizedMessage());
            }
         }
      });

      /** Action for getting all tags */
      JMenuItem allTags = new JMenuItem("Get All Tags");
      allTags.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               RetrieveSQL.getAllTags();
            } catch (SQLException e1) {
               IO.displayGUI(e1.getLocalizedMessage());
            }
         }
      });

      /** Action for deleting all tweets */
      JMenuItem delTweets = new JMenuItem("Delete All Tweets");
      delTweets.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               int i = JOptionPane.showConfirmDialog(null,
                     "are you sure you want to delete all tweets?");
               if (i == JOptionPane.YES_NO_OPTION) {
                  DeleteSQL.deleteAllTweets();
               }
            } catch (SQLException e1) {
               IO.displayGUI(e1.getLocalizedMessage());
            }
         }
      });

      /** Action for deleting all tags */
      JMenuItem delTags = new JMenuItem("Delete All HashTags");
      delTags.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               int i = JOptionPane.showConfirmDialog(null,
                     "Are you sure you want to delete all tags?");
               if (i == JOptionPane.YES_NO_OPTION) {
                  DeleteSQL.deleteTags();
               }
            } catch (SQLException e1) {
               IO.displayGUI(e1.getLocalizedMessage());
            }
         }
      });

      // Link menu to menu bar, menu to menu items
      admin.add(a1);
      admin.add(a2);
      admin.add(a3);
      select.add(fave);
      select.add(retwt);
      select.add(recent);
      select.add(query);
      select.add(user);
      select.add(allUser);
      select.add(allTags);
      delete.add(delTweets);
      delete.add(delTags);
      menuBar.add(select);
      menuBar.add(delete);
      menuBar.add(admin);

      // add to frame
      frame.getContentPane().add(menuBar);
   }

   public void setInterfaceStatus(boolean enabled) {
      this.select.setEnabled(enabled);
      this.delete.setEnabled(enabled);
      this.admin.setEnabled(enabled);
   }
}