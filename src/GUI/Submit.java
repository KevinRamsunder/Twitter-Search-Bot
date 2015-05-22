package GUI;

import java.awt.Font;

import javax.swing.*;

/** Submit elements */

public class Submit {

   /** All GUI elements responsible for submitting information */
   private JFrame frame;
   private Menu menu;
   private TypeButtons buttons;
   private JTextField textField;
   private JButton button;   
   private boolean enabled = true;

   public Submit(JFrame frame, Menu menu, TypeButtons buttons) {
      // linking elements
      this.frame = frame;
      this.menu = menu;
      this.buttons = buttons;
      init();
   }

   /** Start GUI elements */
   private void init() {
      textField = new JTextField();
      textField.setFont(new Font("Dialog", Font.PLAIN, 10));
      textField.setBounds(69, 70, 152, 20);
      frame.getContentPane().add(textField);
      textField.setColumns(10);

      button = new JButton("Submit");
      button.setBounds(105, 125, 76, 21);
      frame.getContentPane().add(button);
      frame.getRootPane().setDefaultButton(button);
      addActionListenerToButton();
   }
   
   /** Set status for all GUI elements */
   public void setInterfaceStatus(boolean enabled) {
      textField.setEnabled(enabled);
      button.setEnabled(enabled);
      menu.setInterfaceStatus(enabled);
      buttons.setInterfaceStatus(enabled);
      this.enabled = enabled;
   }
   
   /** Set button listener */
   private void addActionListenerToButton() {
      button.addActionListener(new AgentAction(this));
   }

   /** Getters */
   public JButton getButton() {
      return button;
   }
   
   public boolean getStatus() {
      return enabled;
   }
   
   public TypeButtons getButtons() {
      return buttons;
   }

   public JTextField getTextField() {
      return textField;
   }   
}