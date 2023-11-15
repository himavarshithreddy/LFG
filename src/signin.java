import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class signin extends JPanel {
    private static final Logger logger = Logger.getLogger(signin.class.getName());


    public signin(){
setLayout(null);
        ImageIcon image =new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\imagemin.png");
        JLabel image2= new JLabel(image);
        image2.setBounds(70,470,200,200);
            JButton back=new JButton("<");
JLabel email = new JLabel("Email ");
JLabel wrongpsd=new JLabel();
JLabel psd= new JLabel("Password ");
JTextField emailt= new JTextField();
JPasswordField pswd = new JPasswordField();
JButton signinbtn = new JButton("Sign In");
            back.setBounds(-10,10,50,30);
            back.setFont(new Font("Helvetica",Font.PLAIN,30));
            back.setContentAreaFilled(false);
            back.setForeground(Color.white);
            back.setUI(new BasicButtonUI());
            back.setBorder(new EmptyBorder(0,0,0,0));
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        wrongpsd.setFont(new Font("Helvetica",Font.PLAIN,18));
        wrongpsd.setForeground(Color.RED);
        wrongpsd.setBounds(100,20,200,30);
        email.setBounds(30,195,80,30);
        email.setFont(new Font("Helvetica",Font.PLAIN,16));
        emailt.setBounds(30,230,280,35);
        psd.setBounds(30,295,180,30);
        psd.setFont(new Font("Helvetica",Font.PLAIN,16));
        pswd.setBounds(30,330,280,35);
        signinbtn.setBounds(30,400,280,35);
        email.setForeground(Color.white);
        Color bg = new Color(220,220,220);
        emailt.setBorder(new EmptyBorder(5,5,5,10));
        pswd.setBorder(new EmptyBorder(5,5,5,10));
        emailt.setBackground(bg);
        psd.setForeground(Color.white);
        pswd.setBackground(bg);

        emailt.setFont(new Font("Helvetica",Font.PLAIN,14));
        pswd.setFont(new Font("Helvetica",Font.PLAIN,14));
        pswd.setForeground(Color.BLACK);
        emailt.setForeground(Color.BLACK);
        signinbtn.setUI(new BasicButtonUI());
        signinbtn.setBackground(Color.BLACK);
        signinbtn.setForeground(Color.white);
        signinbtn.setFont(new Font("Helvetica" ,Font.BOLD, 19));
Color CC = new Color(30,30,30);
back.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            start start = (start) SwingUtilities.getWindowAncestor(signin.this);
            start.setContentPane(new login());
            start.revalidate();
        }
});
        signinbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = emailt.getText();
                String password = new String(pswd.getPassword());
                performSignIn(email, password);
                signup.currentUserEmail = email;
            }
        });
setBackground(CC);
add(email);
add(emailt);
add(psd);
add(pswd);
add(wrongpsd);
add(signinbtn);
add(back);
add(image2);
    };
    public void performSignIn(String email, String password) {
        try {

            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);


            String sql = "SELECT user_id ,user_password FROM users_data WHERE user_email = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);


            ResultSet resultSet = statement.executeQuery();
            FileHandler fileHandler = new FileHandler("signin.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);


            if (resultSet.next()) {
                int userid = resultSet.getInt("user_id");
                itempage.userid = userid;
                String hashedPasswordFromDB = resultSet.getString("user_password");


                String hashedPasswordInput = hashPasswordSHA256(password);


                if (hashedPasswordFromDB.equals(hashedPasswordInput)) {
                    JOptionPane.showMessageDialog(this, "Signin successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    start start = (start) SwingUtilities.getWindowAncestor(signin.this);
                    start.setContentPane(new hotels(signin.this));
                    start.revalidate();
                } else {
                    logger.log(Level.WARNING, "Incorrect password for user: " + email);
                    JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                logger.log(Level.WARNING, "User not found: " + email);

                JOptionPane.showMessageDialog(this, "User Not found", "Error", JOptionPane.ERROR_MESSAGE);
            }


            conn.close();
        } catch (SQLException | NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Exception during sign-in: " + e.getMessage(), e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Sign-in failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String hashPasswordSHA256(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
