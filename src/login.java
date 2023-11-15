import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.plaf.basic.BasicButtonUI;
public class login extends JPanel {

    public login(){

    setLayout(null);

        ImageIcon image =new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\imagemin.png");
        JLabel image2= new JLabel(image);
        image2.setBounds(70,470,200,200);
    JButton signinbtn= new JButton("Sign In");
    signinbtn.setBounds(90,270,155,50);
    custombtn(signinbtn);
    JButton signupbtn=new JButton("Sign Up");
    signupbtn.setBounds(90,350,155,50);
    custombtn(signupbtn);
    add(signinbtn);
    add(signupbtn);
    add(image2);
        Color CC = new Color(30,30,30);
        setBackground(CC);
        signinbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start start = (start) SwingUtilities.getWindowAncestor(login.this);
                start.setContentPane(new signin());
                start.revalidate();

            }
        });
        signupbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start start = (start) SwingUtilities.getWindowAncestor(login.this);
                start.setContentPane(new signup());
                start.revalidate();

            }
        });
    }
    private static void custombtn(JButton button){
        button.setUI(new BasicButtonUI());
        button.setBackground(Color.BLACK);
        button.setForeground(Color.white);
        button.setFont(new Font("Helvetica" ,Font.BOLD, 19));
    }


}
