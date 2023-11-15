import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class myacc extends JPanel {
    private String username;
    private String emailft;
    private JTextField emailt; // Declare emailt as an instance variable
    private JTextField namet;
    public myacc(JPanel lastVisitedPanel){
        setLayout(null);
        JLabel title=new JLabel("My Account");
        title.setFont(new Font("Helvetica",Font.BOLD,25));
        title.setBounds(100,20,200,40);
        title.setForeground(Color.YELLOW);
        ImageIcon image =new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\imagemin.png");
        JLabel image2= new JLabel(image);
        image2.setBounds(70,470,200,200);
        JButton back=new JButton("<");
        JLabel email = new JLabel("Email ");
        JLabel psd= new JLabel("Password ");
        emailt = new JTextField();
        namet = new JTextField();
        JLabel name= new JLabel("Name");

        JPasswordField pswd = new JPasswordField();

        JButton savechangesbtn = new JButton("Save Changes");
        JButton logout = new JButton("Log Out");
        back.setBounds(-10,10,50,30);
        back.setFont(new Font("Helvetica",Font.PLAIN,30));
        back.setContentAreaFilled(false);
        back.setForeground(Color.white);
        back.setUI(new BasicButtonUI());
        back.setBorder(new EmptyBorder(0,0,0,0));
        email.setBounds(30,220,80,30);
        name.setBounds(30,140,80,30);
        name.setFont(new Font("Helvetica",Font.PLAIN,16));
        email.setFont(new Font("Helvetica",Font.PLAIN,16));
        emailt.setBounds(30,255,280,35);
        namet.setBounds(30,175,280,35);
        psd.setBounds(30,310,180,30);
        psd.setFont(new Font("Helvetica",Font.PLAIN,16));
        pswd.setBounds(30,345,280,35);
        savechangesbtn.setBounds(30,400,280,35);
        logout.setBounds(30,450,280,35);
        email.setForeground(Color.white);
        name.setForeground(Color.white);
        Color bg = new Color(220,220,220);
        namet.setBorder(new EmptyBorder(5,5,5,10));
        emailt.setBorder(new EmptyBorder(5,5,5,10));
        pswd.setBorder(new EmptyBorder(5,5,5,10));
        emailt.setBackground(bg);
        namet.setBackground(bg);
        psd.setForeground(Color.white);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        emailt.setText(emailft);
        pswd.setBackground(bg);
        namet.setFont(new Font("Helvetica",Font.PLAIN,14));
        emailt.setFont(new Font("Helvetica",Font.PLAIN,14));
        pswd.setFont(new Font("Helvetica",Font.PLAIN,14));
        pswd.setForeground(Color.BLACK);
        emailt.setForeground(Color.BLACK);
        namet.setForeground(Color.BLACK);
        savechangesbtn.setUI(new BasicButtonUI());
        savechangesbtn.setBackground(Color.BLACK);
        savechangesbtn.setForeground(Color.white);
        savechangesbtn.setFont(new Font("Helvetica" ,Font.BOLD, 19));
        logout.setUI(new BasicButtonUI());
        logout.setBackground(Color.BLACK);
        logout.setForeground(Color.white);
        logout.setFont(new Font("Helvetica" ,Font.BOLD, 19));
        Color CC = new Color(30,30,30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start start = (start) SwingUtilities.getWindowAncestor(myacc.this);
                start.setContentPane(lastVisitedPanel);
                start.revalidate();
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(myacc.this, "Logged Out Successfully.", " Successful Logout", JOptionPane.INFORMATION_MESSAGE);
                start start = (start) SwingUtilities.getWindowAncestor(myacc.this);
                start.setContentPane(new login());
                start.revalidate();
            }
        });
        savechangesbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(pswd.getPassword()); // Get the new password
                if (!newPassword.isEmpty()) {
                    // Check if either email or name is available
                    String emailOrName = emailt.getText(); // You can also use namet.getText()
                    if (!emailOrName.isEmpty()) {
                        // Update the password in the database based on email or name
                        updatePassword(emailOrName, newPassword);
                        JOptionPane.showMessageDialog(myacc.this, "Password updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(myacc.this, "Email or Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(myacc.this, "New Password is required.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        String emailft = signup.currentUserEmail;
        String username = retrieveUsernameByEmail(emailft);
        namet.setText(username);
        emailt.setText(emailft);
        emailt.setEditable(false);
        namet.setEditable(false);


        setBackground(CC);
        add(email);
        add(emailt);
        add(psd);
        add(pswd);
        add(name);
        add(namet);
        add(savechangesbtn);
        add(back);
        add(image2);
        add(title);
        add(logout);


    }

    private String retrieveUsernameByEmail(String email) {
        String username = null;
        try {
            // Establish a database connection
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Create a SQL query to retrieve the user's name based on the email
            String sql = "SELECT user_name FROM users_data WHERE user_email = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);

            // Execute the SQL query
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                username = resultSet.getString("user_name");
            }

            // Close the database connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }

        return username;
    }
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // Handle the exception properly in your application
            return null;
        }
    }
    private void updatePassword(String emailOrName, String newPassword) {
        String hashedPassword = hashPassword(newPassword);
        if (hashedPassword == null) {
            return;
        }

        try {
            // Establish a database connection
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Create a SQL query to update the user's hashed password based on email or name
            String sql = "UPDATE users_data SET user_password = ? WHERE user_email = ? OR user_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, hashedPassword);
            statement.setString(2, emailOrName);
            statement.setString(3, emailOrName);

            // Execute the SQL update
           statement.executeUpdate();

            // Close the database connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }


}
