import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class aboutus extends JPanel {
    public aboutus(JPanel lastVisitedPanel){
        setLayout(null);
        JButton back=new JButton("<");
        back.setBounds(-10,10,50,30);
        back.setFont(new Font("Helvetica",Font.PLAIN,30));
        back.setContentAreaFilled(false);
        back.setForeground(Color.white);
        back.setUI(new BasicButtonUI());
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setBorder(new EmptyBorder(0,0,0,0));
        ImageIcon image =new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\imagemin.png");
        JLabel image2= new JLabel(image);
        image2.setBounds(70,470,200,200);

        JLabel title= new JLabel("About Us");
        title.setForeground(Color.white);
        title.setBackground(Color.black);
        title.setBounds(110,20,150,40);
        title.setFont(new Font("Helvetica",Font.BOLD,28));
        JTextArea des=new JTextArea();
        des.setForeground(Color.lightGray);
        des.setBackground(Color.black);
        des.setLineWrap(true);
        des.setBorder(new EmptyBorder(5,5,5,5));
        des.setBounds(20,100,300,330);
        des.setFont(new Font("Helvetica",Font.PLAIN,16));
        des.setWrapStyleWord(true);
        des.setText("Ever been excited about trying new food, only to be disappointed? We've all been there. That's why we made the Local Food Reviewer App. We want to stop those letdowns and make eating out more fun. We thought, what if people could share their restaurant experiences? That's the idea!We want to create a place where you and others can talk about good and not-so-good food moments.\n\n" +
                "Himavarshith Reddy\n" +
                "Kushal Vekariya\n" +
                "Yatharth Patidhar");
         JLabel contact=new JLabel("Contact : localfoodguidersrm@gmail.com");
        contact.setForeground(Color.orange);
        contact.setFont(new Font("Helvetica",Font.BOLD,15));
        contact.setBounds(20,450,340,40);
        contact.setOpaque(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastVisitedPanel != null) {
                    start parentFrame = (start) SwingUtilities.getWindowAncestor(aboutus.this);
                    parentFrame.setContentPane(lastVisitedPanel);
                    parentFrame.revalidate();
                }
            }
        });
        Color CC = new Color(30,30,30);
        setBackground(CC);
        add(title);
        add(des);
        add(image2);
        add(contact);
        add(back);
    }
}
