package GUI;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.sql.SQLException;

import IO.IO;
import SQL.SQL;

/** Main GUI for user interaction */

public class MainGUI {

   private JFrame frame;
   private Submit submit;
   private TypeButtons radio;
   private Menu menu;
   private JTextField textField;

   public MainGUI() {
      initialize(); // set up elements
      setCloseAction(); // set what to do on GUI close
   }

   /** Set visual elements */
   private void initialize() {
      // set title and icon image
      frame = new JFrame("Twitter Agent");
      frame.setBounds(100, 100, 300, 200);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLocationRelativeTo(null);
      frame.getContentPane().setLayout(null);
      ImageIcon img = new ImageIcon("twitterFavicon.png");
      frame.setIconImage(img.getImage());
      
      // Label
      JLabel searchBar = new JLabel("Twitter Search Bar");
      searchBar.setFont(new Font("Tahoma", Font.BOLD, 14));
      searchBar.setBounds(77, 43, 135, 14);
      frame.getContentPane().add(searchBar);

      // Buttons
      radio = new TypeButtons(frame);
      
      // Top Menu
      menu = new Menu(frame);
      
      // Submit elements
      submit = new Submit(frame, menu, radio);
      
      // Show to user
      frame.setVisible(true);
   }

   /** Close all streams once the JFrame is closed */
   private void setCloseAction() {
      // Create new listener
      WindowListener deconstructor = new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            try {
               SQL.closeMySQL(); // close SQL connection
               IO.closeStream(); // close file connection
            } catch (SQLException e1) {
               IO.displayGUI(e1.getLocalizedMessage());
            } finally {
               IO.closeStream();
            }
         }
      };

      // add listener to jframe
      frame.addWindowListener(deconstructor);
   }
}