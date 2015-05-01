/**
 * Created by brookeside on 4/30/15.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class March30b
{
    public static void main (String [] args)
    {
        JFrame frame = new JFrame ("Mystery Layout Management!");

        Font f = new Font ("Helvetica", Font.BOLD, 36);

        JButton b1 = new JButton ("Button 1");
        JButton b2 = new JButton ("Button 2");
        JButton b3 = new JButton ("Button 3");

        b1.setFont (f);
        b2.setFont (f);
        b3.setFont (f);

        b1.addActionListener ( new Clicker() );
        b2.addActionListener ( new Clicker() );

        JLabel label1 = new JLabel ("Southwest");
        JLabel label2 = new JLabel ("Southeast");
        label1.setFont (f);
        label2.setFont (f);

        JPanel top = new JPanel();
        top.setLayout (new FlowLayout());
        top.add (b1);
        top.add (b2);

        JPanel bottom = new JPanel();
        bottom.setLayout (new BorderLayout());
        bottom.add (label1, BorderLayout.WEST);
        bottom.add (label2, BorderLayout.EAST);

        frame.add (top, BorderLayout.NORTH);
        frame.add (bottom, BorderLayout.SOUTH);
        b3.setBackground (Color.PINK.darker().darker());
        frame.add (b3, BorderLayout.CENTER);

        frame.setSize (600, 600);
        frame.setVisible (true);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }
}

class Clicker implements ActionListener
{
    static int counter = 0;
    public void actionPerformed (ActionEvent e)
    {
        counter++;
        System.out.println ("Another click just occurred! " +
                "# of clicks = " + counter);
        JButton jb = (JButton) e.getSource();
        jb.setText ("Ooooooooh,  that felt good!");
    }
}

