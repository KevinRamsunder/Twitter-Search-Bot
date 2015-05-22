package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import HTML.TweetType;

/** Class for controlling radio buttons */

public class TypeButtons {

   private JFrame frame;
   private JRadioButton all;
   private JRadioButton top;
   private JRadioButton user;

   public TypeButtons(JFrame frame) {
      this.frame = frame;
      init();
   }

   /** Start GUI Elements */
   private void init() {
      // all button
      all = new JRadioButton("ALL");
      all.setSelected(true);
      all.setFont(new Font("Tahoma", Font.PLAIN, 9));
      all.setBounds(69, 94, 43, 23);
      frame.getContentPane().add(all);

      // top button
      top = new JRadioButton("TOP");
      top.setFont(new Font("Tahoma", Font.PLAIN, 9));
      top.setBounds(118, 94, 53, 23);
      frame.getContentPane().add(top);

      // user button
      user = new JRadioButton("USER");
      user.setFont(new Font("Tahoma", Font.PLAIN, 9));
      user.setBounds(168, 94, 53, 23);
      frame.getContentPane().add(user);

      // set listener
      all.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            turnOffAllButtons();
            all.setSelected(true);
         }
      });

      // set listener
      top.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            turnOffAllButtons();
            top.setSelected(true);
         }
      });
      
      // set listener
      user.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            turnOffAllButtons();
            user.setSelected(true);
         }
      });
   }

   /** Disable all buttons */
   private void turnOffAllButtons() {
      all.setSelected(false);
      top.setSelected(false);
      user.setSelected(false);
   }

   /** Set status for each button */
   public void setInterfaceStatus(boolean enabled) {
      this.all.setEnabled(enabled);
      this.top.setEnabled(enabled);
      this.user.setEnabled(enabled);
   }

   /** Convert button clicked to tweet type */
   public TweetType getClicked() {
      if (this.all.isSelected()) {
         return TweetType.ALL;
      } else if (this.top.isSelected()) {
         return TweetType.TOP;
      } else if (this.user.isSelected()) {
         return TweetType.USER;
      }
      return TweetType.ALL;
   }

   /** Getters */
   public JRadioButton getAll() {
      return all;
   }

   public JRadioButton getTop() {
      return top;
   }

   public JRadioButton getUser() {
      return user;
   }
}