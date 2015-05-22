package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Downloader.Downloader;
import HTML.Parser;
import HTML.Tweet;
import IO.Admin;
import IO.IO;
import IO.LogSQL;

/** Class responsible for displaying download options */

public class TableMenu {

   /** GUI Elements */
   private JFrame frame;
   private JMenuBar menuBar;
   private Parser parser;

   public TableMenu(JFrame frame, Parser parser) {
      this.frame = frame;
      this.parser = parser;
      init();
   }

   /** Start GUI Elements */
   private void init() {
      // menu item for downloading
      JMenuItem dl1 = new JMenuItem("HTML Content");

      // add listener to menu bar
      menuBar = new JMenuBar();
      JMenu download = new JMenu("Download");
      downloadTweets(dl1);
      download.add(dl1);
      menuBar.add(download);

      // add bar to frame
      frame.getContentPane().add(menuBar, BorderLayout.NORTH);
   }

   /** Go through tweets and download the message and the images */
   private void downloadTweets(JMenuItem dl1) {
      dl1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent arg0) {
            // get every current tweet
            final HashMap<String, Tweet> content = parser.getCurrentTweets();

            // sync locks for each download
            final CountDownLatch cdl = new CountDownLatch(content.size());

            // create directory and tell user
            File f = new File("downloads\\");
            IO.displayGUI("Downloading " + parser.getCurrentCount()
                  + " tweets to: " + f.getAbsolutePath()
                  + "\n(This may take some time. Do not interact with the GUI)");

            // Download in background to avoid blocking main thread
            Thread thread = new Thread(new Runnable() {
               @Override
               public void run() {
                  // Download every message and their images
                  for (String s : content.keySet()) {
                     Tweet t = content.get(s); // get tweet

                     // Download
                     LogSQL.log("Downloading: " + t.getUrlToTweet()
                           + " and content to local system.");
                     Downloader.write(t.getUsername(), t.getUrlToTweet());

                     // download finish, count down
                     cdl.countDown();
                  }
               }
            });

            thread.start(); // start thread

            // wait here until all locks are freed
            try {
               cdl.await();
            } catch (InterruptedException e) {
               IO.displayGUI("Timing error occurred. Exiting program.");
               System.exit(0);
            }

            // print messages
            IO.displayGUI("All downloads finished.");
            LogSQL.log("All downloads finished.");
            Admin.openDownloadLocation(new File("downloads\\")
                  .getAbsolutePath());
         }
      });
   }
}