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

public class hotelinfo extends JPanel {
    private int restaurantId;
    private JLabel name;
    private  JTextArea address;
    private JTextArea contact;
    private JTextArea des;

    public hotelinfo(JPanel lastVisitedPanel) {
        setLayout(null);
        JButton back = new JButton("<");
        back.setBounds(-10, 10, 50, 30);
        back.setFont(new Font("Helvetica", Font.PLAIN, 30));
        back.setContentAreaFilled(false);
        back.setForeground(Color.white);
        back.setUI(new BasicButtonUI());
        back.setBorder(new EmptyBorder(0, 0, 0, 0));
        des = new JTextArea();
        des.setForeground(Color.lightGray);
        des.setBackground(Color.black);
        des.setLineWrap(true);
        des.setBorder(new EmptyBorder(5, 5, 5, 5));
        des.setBounds(20, 430, 300, 200);
        des.setFont(new Font("Helvetica", Font.PLAIN, 15));
        des.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        name = new JLabel();
        name.setText("Hotel Name");
        name.setForeground(Color.white);
        name.setFont(new Font("Helvetica", Font.BOLD, 23));
        name.setOpaque(false);
        name.setBounds(20, 290, 300, 40);
        JLabel title = new JLabel("Hotel Info");
        title.setForeground(Color.lightGray);
        title.setBackground(Color.black);
        ImageIcon image = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\hotel.png");
        JLabel image2 = new JLabel(image);
        title.setBounds(110, 30, 150, 40);
        title.setFont(new Font("Helvetica", Font.BOLD, 28));
        image2.setBounds(20, 100, 300, 180);
        address = new JTextArea("Address: UB Ground Floor, Shop number 2");
        address.setLineWrap(true);
        address.setWrapStyleWord(true);
        address.setForeground(Color.orange);
        address.setOpaque(false);
        contact = new JTextArea("Contact : 9638527410");
        contact.setLineWrap(true);
        contact.setOpaque(false);
        contact.setForeground(Color.orange);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contact.setFont(new Font("Helvetica", Font.PLAIN, 16));
        contact.setBounds(20, 385, 300, 50);
        address.setBounds(20, 335, 300, 50);
        address.setFont(new Font("Helvetica", Font.PLAIN, 16));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastVisitedPanel != null) {
                    start parentFrame = (start) SwingUtilities.getWindowAncestor(hotelinfo.this);
                    parentFrame.setContentPane(lastVisitedPanel);
                    parentFrame.revalidate();
                }
            }
        });
        Color CC = new Color(30, 30, 30);
        setBackground(CC);
        add(title);
        add(address);
        add(image2);
        add(contact);
        add(back);
        add(des);
        add(name);

    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
        loaddata();

    }

    private void loaddata() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            String sql = "SELECT restaurants_name, restaurants_adddress,restaurants_description,restaurants_phoneNumber, restaurants_id " + "FROM restaurants_data WHERE restaurants_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,restaurantId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String restaurantName = resultSet.getString("restaurants_name");
                String restaurantadd = resultSet.getString("restaurants_adddress");
                String restaurantdes = resultSet.getString("restaurants_description");
                String restaurantnum = resultSet.getString("restaurants_phoneNumber");
                name.setText(restaurantName);
                contact.setText("Contact : " +restaurantnum);
                address.setText("Address : " +restaurantadd);
                des.setText(restaurantdes);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }


