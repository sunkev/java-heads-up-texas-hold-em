/**
 * Created by brookeside on 4/30/15.
 */
// ActionEventDemo.java

/**
 * Illustrate how Action Events work
 * @author Henry H. Leitner
 * @version  Last modified on March 6, 2015
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class ActionEventDemo
{
    public static void main (String [] args)
    {
        ActionEventFrame a = new ActionEventFrame ("My Frame!", 500, 500);
        a.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        a.setVisible (true);
    }
}

class ActionEventFrame extends JFrame
{
    JButton addButton = new JButton ("Click to ADD");
    JButton subtractButton = new JButton ("Click to SUBTRACT");
    JTextField tf = new JTextField ("blah blah blah ...");

    public ActionEventFrame (String name, int h, int w)
    {
        setTitle (name);
        setSize (h, w);
        Font f = new Font ("Times", Font.BOLD, 36);
        addButton.setFont (f);
        subtractButton.setFont (f);
        addButton.setForeground (Color.RED);
        subtractButton.setForeground (Color.ORANGE.darker());
        ButtonListener bl = new ButtonListener ();
        addButton.addActionListener (bl);
        subtractButton.addActionListener (bl);
        tf.setFont(f);
        tf.setForeground(Color.BLUE);
        tf.setHorizontalAlignment (JTextField.CENTER);

        add (tf, BorderLayout.CENTER);
        add (subtractButton, BorderLayout.SOUTH);
        add (addButton, BorderLayout.NORTH);
        addWindowListener (new WindowChatterBox());
    }


    // an inner class
    class ButtonListener implements ActionListener
    {
        int counter = 0;
        public void actionPerformed (ActionEvent e)
        {
            if (e.getSource() == addButton) counter++;
            else if (e.getSource() == subtractButton) counter-- ;
            tf.setText (" " + counter);
        }
    }

    // another inner class
    class WindowChatterBox implements WindowListener
    {
        public void windowClosing (WindowEvent e)
        {
            System.out.println("Window is in the process of CLOSING!");
        }

        public void windowActivated (WindowEvent e)
        {
            System.out.println("Window got ACTIVATED!");
        }

        public void windowClosed (WindowEvent e)
        {
            System.out.println("Window got CLOSED!");
        }

        public void windowDeactivated (WindowEvent e)
        {
            System.out.println("Window got DEACTIVATED!");
        }

        public void windowDeiconified (WindowEvent e)
        {
            System.out.println("Window got DE-ICONIFIED!");
        }

        public void windowIconified (WindowEvent e)
        {
            System.out.println("Window got ICONIFIED!");
        }

        public void windowOpened (WindowEvent e)
        {
            System.out.println("Window got OPENED!");
        }
    }
}