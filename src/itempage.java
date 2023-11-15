import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class itempage extends JPanel {
    private int itemId;
    private JLabel itemname;
    private JLabel price;
    private JLabel ovr;
    private JLabel rtaste;
    private JLabel rhealthy;
    private JLabel rvtm;
    private JScrollPane scrollPane;
    public static int userid;

    public itempage(JPanel lastVisitedPanel) {
        setLayout(null);
        JButton back = new JButton("<");

        back.setBounds(-10, 10, 50, 30);
        back.setFont(new Font("Helvetica", Font.PLAIN, 25));
        back.setContentAreaFilled(false);
        back.setForeground(Color.white);
        back.setUI(new BasicButtonUI());
        back.setBorder(new EmptyBorder(0, 0, 0, 0));
        ImageIcon user = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\user.png");
        JButton acc = new JButton(user);
        acc.setBounds(275, 5, 70, 50);
        acc.setFont(new Font("Helvetica", Font.BOLD, 30));
        acc.setBorder(new EmptyBorder(0, 0, 0, 0));
        acc.setForeground(Color.white);
        acc.setContentAreaFilled(false);
        acc.setUI(new BasicButtonUI());
        back.setBorder(new EmptyBorder(0, 0, 0, 0));
        ImageIcon image = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\item.png");
        JLabel image1 = new JLabel(image);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        acc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JLabel ovrh =new JLabel("Overall Rating");
        ovrh.setFont(new Font("Helvetica", Font.ITALIC, 20));
        ovrh.setForeground(Color.YELLOW);
        ovrh.setBounds(190, 115, 200, 100);
        itemname = new JLabel();
        itemname.setText("Item Name");
        price = new JLabel();
        price.setText("200/-");
        ovr = new JLabel();
        ovr.setText("10/10");
        ovr.setForeground(Color.yellow);
        price.setForeground(Color.white);
        itemname.setForeground(Color.white);
        itemname.setFont(new Font("Helvetica", Font.PLAIN, 23));
        price.setFont(new Font("Helvetica", Font.PLAIN, 26));
        ovr.setFont(new Font("Helvetica", Font.ITALIC, 33));
        itemname.setBounds(25, 55, 250, 40);
        price.setBounds(250, 60, 100, 40);
        image1.setBounds(25, 110, 120, 160);
        ovr.setBounds(215, 150, 200, 100);
        Color CC = new Color(30, 30, 30);
        JLabel taste = new JLabel("Taste");
        JLabel healthy = new JLabel("Healthy");
        JLabel vtm = new JLabel("Value for Money");
        rtaste = new JLabel();
        rhealthy = new JLabel();
         rvtm = new JLabel();
        rtaste.setText("9/10");
        rhealthy.setText("8/10");
        rvtm.setText("7/10");
        taste.setForeground(Color.green);
        healthy.setForeground(Color.green);
        vtm.setForeground(Color.green);
        vtm.setOpaque(true);
        rvtm.setOpaque(true);
        healthy.setOpaque(true);
        rhealthy.setOpaque(true);
        taste.setOpaque(true);
        rtaste.setOpaque(true);
        vtm.setBackground(Color.darkGray);
        rvtm.setBackground(Color.darkGray);
        healthy.setBackground(Color.darkGray);
        rhealthy.setBackground(Color.darkGray);
        taste.setBackground(Color.darkGray);
        rtaste.setBackground(Color.darkGray);
        taste.setBorder(new EmptyBorder(0, 5, 0, 5));
        healthy.setBorder(new EmptyBorder(0, 5, 0, 5));
        vtm.setBorder(new EmptyBorder(0, 5, 0, 5));
        rtaste.setBorder(new EmptyBorder(0, 10, 0, 5));
        rhealthy.setBorder(new EmptyBorder(0, 10, 0, 5));
        rvtm.setBorder(new EmptyBorder(0, 10, 0, 5));
        taste.setFont(new Font("Helvetica", Font.PLAIN, 20));
        healthy.setFont(new Font("Helvetica", Font.PLAIN, 20));
        vtm.setFont(new Font("Helvetica", Font.PLAIN, 20));
        rtaste.setForeground(Color.green);
        rhealthy.setForeground(Color.green);
        rvtm.setForeground(Color.green);
        rtaste.setFont(new Font("Helvetica", Font.PLAIN, 18));
        rhealthy.setFont(new Font("Helvetica", Font.PLAIN, 18));
        rvtm.setFont(new Font("Helvetica", Font.PLAIN, 18));
        taste.setBounds(25, 290, 230, 40);
        healthy.setBounds(25, 335, 230, 40);
        vtm.setBounds(25, 380, 230, 40);
        rtaste.setBounds(260, 290, 60, 40);
        rhealthy.setBounds(260, 335, 60, 40);
        rvtm.setBounds(260, 380, 60, 40);
        JLabel rtitle = new JLabel("User Reviews");
        rtitle.setFont(new Font("Helvetica", Font.PLAIN, 20));
        rtitle.setForeground(Color.white);
        rtitle.setBounds(110, 430, 200, 40);
        JTextArea rtext = new JTextArea("Enter Your review");
        JButton radd = new JButton("Post review");
        rtext.setBackground(Color.white);
        rtext.setForeground(Color.darkGray);
        rtext.setBorder(new EmptyBorder(8, 5, 0, 0));
        rtext.setFont(new Font("Helvetica", Font.PLAIN, 16));
        rtext.setBounds(20, 480, 300, 40);
        radd.setUI(new BasicButtonUI());
        radd.setBorder(new EmptyBorder(0,0,0,0));

        JButton rsee = new JButton("See Reviews");
        rsee.setUI(new BasicButtonUI());
        rsee.setBorder(new EmptyBorder(0,0,0,0));
        rsee.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rsee.setBackground(new Color(248, 151, 143));
        radd.setBackground(new Color(248, 151, 143));
        rsee.setForeground(Color.black);
        rsee.setBounds(55,590,230,35);
        rsee.setFont(new Font("Helvetica", Font.PLAIN, 17));
        radd.setForeground(Color.black);
        radd.setFont(new Font("Helvetica", Font.PLAIN, 17));
        radd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        radd.setToolTipText("Post review");
        radd.setBounds(55, 540 , 230, 35);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastVisitedPanel != null) {
                    start parentFrame = (start) SwingUtilities.getWindowAncestor(itempage.this);
                    parentFrame.setContentPane(lastVisitedPanel);
                    parentFrame.revalidate();
                }
            }
        });
        acc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start start = (start) SwingUtilities.getWindowAncestor(itempage.this);
                start.setContentPane(new myacc(itempage.this));
                start.revalidate();

            }
        });
        radd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle review submission
                String reviewText = rtext.getText();
                if (reviewText.isEmpty() || reviewText.equals("Enter Your review")) {
                    // Display an error message using JOptionPane
                    JOptionPane.showMessageDialog(null, "Please enter your review before submitting.");
                } else {


                    int userId = userid;
                    if (!hasUserReviewedItem(userId, itemId)) {

                        insertReviewIntoDatabase(userId, itemId, reviewText);
                        rtext.setText("");


                    } else {

                    JOptionPane.showMessageDialog(null, "You have already reviewed this item.");
                   }


                }
            }
        });
        rtext.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                rtext.setText("");
            }
        });
        rsee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReviews(itemId);
            }
        });
        setBackground(CC);
        add(back);
        add(acc);
        add(itemname);
        add(price);
        add(image1);
        add(ovr);
        add(taste);
        add(healthy);
        add(vtm);
        add(rvtm);
        add(rhealthy);
        add(rtaste);
        add(rtitle);
        add(rtext);
        add(radd);
        add(ovrh);
        add(rsee);


    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
        loaddata();

    }

    private void loaddata() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Use the restaurantId to fetch menu items for the selected restaurant
            String sql = "SELECT item_id, item_name, item_price, item_overrallRating,item_description,item_taseRating,item_healthnessRating,item_WorthRating " +
                    "FROM menuItems_data WHERE item_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, itemId); // Set the restaurantId as a parameter

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                int itemPrice = resultSet.getInt("item_price");
                int itemOverallRating = resultSet.getInt("item_overrallRating");
                int itemtasterating = resultSet.getInt("item_taseRating");
                int itemhealthrating = resultSet.getInt("item_healthnessRating");
                int itemworthrating = resultSet.getInt("item_WorthRating");
                String itemdes = resultSet.getString("item_description");
                itemname.setText(itemName);
                price.setText("₹"+itemPrice);
                ovr.setText(itemOverallRating+"/10");
                rtaste.setText(itemtasterating+"/5");
                rhealthy.setText(itemhealthrating+"/5");
                rvtm.setText(itemworthrating+"/5");

            }


            conn.close();
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void insertReviewIntoDatabase(int userId, int itemId, String reviewText) {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            String sql = "INSERT INTO reviews_data (user_id, item_id, review_text) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            statement.setString(3, reviewText);

            statement.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Your review has been submitted.");
    }
    private boolean hasUserReviewedItem(int userId, int itemId) {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            String sql = "SELECT * FROM reviews_data WHERE user_id = ? AND item_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, itemId);

            ResultSet resultSet = statement.executeQuery();

            boolean hasReviewed = resultSet.next(); // Check if a result was found

            conn.close();

            return hasReviewed;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Handle the error appropriately in your application
        }
    }
    private void showReviews(int itemId) {


        JPanel reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));

        try {
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            String sql = "SELECT reviews_data.review_text, users_data.user_name " +
                    "FROM reviews_data " +
                    "INNER JOIN users_data ON reviews_data.user_id = users_data.user_id " +
                    "WHERE reviews_data.item_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, itemId);

            ResultSet resultSet = statement.executeQuery();
            reviewsPanel.setBorder(new EmptyBorder(0,0,0,0));

            while (resultSet.next()) {
                String username = resultSet.getString("user_name");
                String reviewText = resultSet.getString("review_text");

                JPanel reviewContainer = createReviewContainer(username, reviewText);
                reviewsPanel.add(reviewContainer);
                reviewsPanel.add(Box.createVerticalStrut(5));

            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(reviewsPanel);
        scrollPane.setPreferredSize(new Dimension(270, 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));

        JOptionPane.showMessageDialog(this, scrollPane, "Item Reviews", JOptionPane.PLAIN_MESSAGE);
    }
    private JPanel createReviewContainer(String username, String reviewText) {
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBorder(new EmptyBorder(0,0,0,0));

        container.setPreferredSize(new Dimension(230, 60));

        JLabel nameLabel = new JLabel(username);
        JTextArea reviewLabel = new JTextArea(reviewText);
        reviewLabel.setLineWrap(true);
        reviewLabel.setWrapStyleWord(true);
        reviewLabel.setEditable(false);
        reviewLabel.setOpaque(false);
        nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 13));
        nameLabel.setForeground(Color.darkGray);
        reviewLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        reviewLabel.setForeground(Color.black);
        container.setBackground(Color.lightGray);

        nameLabel.setBounds(5, 2, 200, 20);
        reviewLabel.setBounds(5, 18, 230, 40);

        container.add(nameLabel);
        container.add(reviewLabel);

        return container;
    }


}
