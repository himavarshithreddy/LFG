import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class signup extends JPanel {
    public static String currentUserEmail;
    public signup(){
        setLayout(null);
        ImageIcon image =new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\imagemin.png");
        JLabel image2= new JLabel(image);
        image2.setBounds(70,470,200,200);
        JButton back=new JButton("<");
        JLabel email = new JLabel("Email ");
        JLabel psd= new JLabel("Password ");
        JLabel name= new JLabel("Name");
        JTextField emailt= new JTextField();
        JPasswordField pswd = new JPasswordField();
        JTextField namet= new JTextField();
        JButton signupbtn = new JButton("Sign Up");
        back.setBounds(-10,10,50,30);
        back.setFont(new Font("Helvetica",Font.PLAIN,30));
        back.setContentAreaFilled(false);
        back.setForeground(Color.white);
        back.setUI(new BasicButtonUI());
        back.setBorder(new EmptyBorder(0,0,0,0));
        email.setBounds(30,235,80,30);
        name.setBounds(30,155,80,30);
        name.setFont(new Font("Helvetica",Font.PLAIN,16));
        email.setFont(new Font("Helvetica",Font.PLAIN,16));
        emailt.setBounds(30,270,280,35);
        namet.setBounds(30,190,280,35);
        psd.setBounds(30,325,180,30);
        psd.setFont(new Font("Helvetica",Font.PLAIN,16));
        pswd.setBounds(30,360,280,35);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupbtn.setBounds(30,440,280,35);
        email.setForeground(Color.white);
        name.setForeground(Color.white);
        Color bg = new Color(220,220,220);
        namet.setBorder(new EmptyBorder(5,5,5,10));
        emailt.setBorder(new EmptyBorder(5,5,5,10));
        pswd.setBorder(new EmptyBorder(5,5,5,10));
        emailt.setBackground(bg);
        namet.setBackground(bg);
        psd.setForeground(Color.white);
        pswd.setBackground(bg);
        namet.setFont(new Font("Helvetica",Font.PLAIN,14));
        emailt.setFont(new Font("Helvetica",Font.PLAIN,14));
        pswd.setFont(new Font("Helvetica",Font.PLAIN,14));
        pswd.setForeground(Color.BLACK);
        emailt.setForeground(Color.BLACK);
        namet.setForeground(Color.BLACK);
        signupbtn.setUI(new BasicButtonUI());
        signupbtn.setBackground(Color.BLACK);
        signupbtn.setForeground(Color.white);
        signupbtn.setFont(new Font("Helvetica" ,Font.BOLD, 19));
        Color CC = new Color(30,30,30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start start = (start) SwingUtilities.getWindowAncestor(signup.this);
                start.setContentPane(new login());
                start.revalidate();
            }
        });
        signupbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = namet.getText();
                String email = emailt.getText();
                String password = new String(pswd.getPassword());

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {

                    JOptionPane.showMessageDialog(signup.this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    performSignup(username, email, password);
                    signup.currentUserEmail = email;

                }

            }
        });
        setBackground(CC);
        add(email);
        add(emailt);
        add(psd);
        add(pswd);
        add(name);
        add(namet);
        add(signupbtn);
        add(back);
        add(image2);

    }

    public void performSignup(String username, String email, String password) {
        try {

            String hashedPassword = hashPasswordSHA256(password);


            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);


            String sql = "INSERT INTO users_data (user_name, user_email, user_password) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, hashedPassword); // Store the hashed password

            statement.executeUpdate();
            String sql2 = "SELECT user_id FROM users_data WHERE user_email = ?";
            PreparedStatement statement2 = conn.prepareStatement(sql2);
            statement2.setString(1, email);

            ResultSet resultSet = statement2.executeQuery();
            if (resultSet.next()) {
                int userid = resultSet.getInt("user_id");
                itempage.userid = userid;
            }
            conn.close();

            JOptionPane.showMessageDialog(this, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            start start = (start) SwingUtilities.getWindowAncestor(signup.this);
            start.setContentPane(new hotels(signup.this));
            start.revalidate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Signup failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
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
