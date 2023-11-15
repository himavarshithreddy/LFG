import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class menu extends JPanel {
    private int restaurantId;
    private JScrollPane scrollPane;
    private String currentSortOrder = "Default";
    private JPanel menuPanel;
    private int menusLoadedCount = 0;
    public menu(JPanel  lastVisitedPanel){
        setLayout(null);
        ImageIcon sortimg = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\sort.png");
        ImageIcon aboutimg = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\about.png");
        ImageIcon infoimg = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\info.png");
        JButton info=new JButton(infoimg);
        JButton sort=new JButton(sortimg);
        JButton back=new JButton("<");
        back.setBounds(-10,10,50,30);
        back.setFont(new Font("Helvetica",Font.PLAIN,25));
        back.setContentAreaFilled(false);
        back.setForeground(Color.white);
        back.setUI(new BasicButtonUI());
        back.setBorder(new EmptyBorder(0,0,0,0));
        JButton about=new JButton(aboutimg);
        about.setBounds(240,7,50,40);
        about.setContentAreaFilled(false);
        about.setUI(new BasicButtonUI());
        about.setBorder(new EmptyBorder(0,0,0,0));
        sort.setContentAreaFilled(false);
        sort.setUI(new BasicButtonUI());
        sort.setBorder(new EmptyBorder(0,0,0,0));
        info.setContentAreaFilled(false);
        info.setUI(new BasicButtonUI());
        info.setBorder(new EmptyBorder(0,0,0,0));
        info.setBounds(290,105,40,32);
        ImageIcon user = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\user.png");
        JButton acc = new JButton(user);
        acc.setBounds(275,5,70,40);
        acc.setFont(new Font("Helvetica",Font.BOLD,30));
        acc.setBorder(new EmptyBorder(0,0,0,0));
        acc.setForeground(Color.white);
        acc.setContentAreaFilled(false);
        acc.setUI(new BasicButtonUI());
        about.setBorder(new EmptyBorder(0,0,0,0));
        JLabel title = new JLabel("Menu");
        title.setForeground(Color.white);
        title.setBackground(Color.black);
        sort.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        title.setBounds(110,50,150,40);
        title.setFont(new Font("Verdana",Font.BOLD,28));
        JTextField search = new JTextField("Search");
        search.setBackground(Color.white);
        search.setForeground(Color.darkGray);
        LineBorder border = new LineBorder(Color.lightGray, 3);
        EmptyBorder paddingBorder = new EmptyBorder(0,5,0,0);
        CompoundBorder compoundBorder = new CompoundBorder(border, paddingBorder);
        search.setBorder(compoundBorder);
        search.setFont(new Font("Helvetica" ,Font.PLAIN, 15));
        search.setBounds(20,105,225,35);
        sort.setBounds(250,105,40,32);
        JLabel headings= new JLabel("Name                  Price     Rating");
        headings.setForeground(Color.white);
        headings.setFont(new Font("Helvetica" ,Font.PLAIN, 19));
        headings.setBounds(25,145,300,40);
        acc.setToolTipText("My Account");
        about.setToolTipText("About Us");
        sort.setToolTipText("Sort");
        info.setToolTipText("Info");
        info.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        acc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        about.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastVisitedPanel != null) {
                    start start = (start) SwingUtilities.getWindowAncestor(menu.this);
                    start.setContentPane(new aboutus(menu.this));
                    start.revalidate();
                }
            }
        });
        search.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                search.setText(""); // Clear the text field when it gains focus
            }
        });
        acc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start start = (start) SwingUtilities.getWindowAncestor(menu.this);
                start.setContentPane(new myacc(menu.this));
                start.revalidate();

            }
        });
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start start = (start) SwingUtilities.getWindowAncestor(menu.this);
                hotelinfo hotelinfo = new hotelinfo(menu.this);
                hotelinfo.setRestaurantId(restaurantId);
                start.setContentPane(hotelinfo);
                start.revalidate();

            }
        });
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = search.getText().toLowerCase();
                searchfun(searchText);
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastVisitedPanel != null) {
                    start parentFrame = (start) SwingUtilities.getWindowAncestor(menu.this);
                    parentFrame.setContentPane(lastVisitedPanel);
                    parentFrame.revalidate();
                }
            }
        });
        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applysort();
            }
        });


        Color CC = new Color(30,30,30);
        setBackground(CC);
        add(search);
        add(sort);
        add(info);
        add(headings);
        add(title);
        add(back);

        menuPanel = new JPanel();
       menuPanel.setLayout(null);
        scrollPane = new JScrollPane(menuPanel);
        scrollPane.setBounds(10, 175, 325, 520);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(scrollPane);

        SwingWorker<Void, Void> guiUpdateWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                // This method is called on the Event Dispatch Thread
                return null;
            }

            @Override
            protected void done() {

                System.out.println("Total menu items loaded");

                add(about);
                add(acc);

            }
        };

        guiUpdateWorker.execute();

        // Create a separate Thread for loading data
        Thread dataLoadingThread = new Thread(() -> {
            loadMenuItemsData();

            guiUpdateWorker.execute();
        });

        dataLoadingThread.start();
    }
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
        // Call the method to load menu items here based on the restaurantId

    }
    private void loadMenuItemsData() {
        int containerY = 10;
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Use the restaurantId to fetch menu items for the selected restaurant
            String sql = "SELECT item_id, item_name, item_price, item_overrallRating " +
                    "FROM menuItems_data WHERE restaurants_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, restaurantId); // Set the restaurantId as a parameter

            ResultSet resultSet = statement.executeQuery();
            Color CC = new Color(30,30,30);
            menuPanel.setBackground(CC);

            int containerHeight = 55;

            while (resultSet.next()) {
                synchronized (this) {
                    // Use synchronized block to ensure thread safety
                    menusLoadedCount++;

                    // Simulate some processing
                    Thread.sleep(100); // Add a delay to simulate processing time
                }
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                int itemPrice = resultSet.getInt("item_price");
                int itemOverallRating = resultSet.getInt("item_overrallRating");

                JPanel menuItemContainer = createMenuItemContainer(itemName, itemPrice, itemOverallRating, itemId, containerY);
                menuPanel.add(menuItemContainer);

                menuItemContainer.setBorder(new EmptyBorder(0,0,0,0));

                containerY += containerHeight;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Set the preferred size to ensure proper scrolling in the JScrollPane
        menuPanel.setPreferredSize(new Dimension(300, containerY + 10));
    }
    private JPanel createMenuItemContainer(String name, int price, int overallRating, int itemId, int y) {
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBounds(10, y, 290, 45);

        JLabel nameLabel = new JLabel(name);
        JLabel priceLabel = new JLabel( "₹"+ price);
        JLabel ratingLabel = new JLabel(overallRating+"/10");

        nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        nameLabel.setForeground(Color.black);
        priceLabel.setFont(new Font("Helvetica", Font.PLAIN, 17));
        priceLabel.setForeground(Color.darkGray);

        ratingLabel.setFont(new Font("Helvetica", Font.PLAIN, 17));
        ratingLabel.setForeground(Color.darkGray);
        container.setBackground(new Color(173, 216, 230));
        ratingLabel.setForeground(Color.black);
        nameLabel.setForeground(Color.black);
        priceLabel.setForeground(Color.black);
        container.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nameLabel.setBounds(10, 10, 200, 25);
        priceLabel.setBounds(155, 10, 200, 25);

        ratingLabel.setBounds(235, 10, 200, 25);

        container.add(nameLabel);
        container.add(priceLabel);

        container.add(ratingLabel);

        // Add an action listener to handle item selection
        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                start start = (start) SwingUtilities.getWindowAncestor(menu.this);
                itempage itempage=new itempage(menu.this);
                itempage.setItemId(itemId);
                start.setContentPane(itempage);
                start.revalidate();

            }
        });

        return container;
    }
    private void searchfun(String searchText){
        menuPanel.removeAll();
        int containerY=10;
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Use the restaurantId to fetch menu items for the selected restaurant
            String sql = "SELECT item_id, item_name, item_price, item_overrallRating " +
                    "FROM menuItems_data WHERE restaurants_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, restaurantId); // Set the restaurantId as a parameter

            ResultSet resultSet = statement.executeQuery();
            Color CC = new Color(30,30,30);
            menuPanel.setBackground(CC);

            int containerHeight = 50;

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name").toLowerCase();
                String itemName2 = resultSet.getString("item_name");
                int itemPrice = resultSet.getInt("item_price");
                int itemOverallRating = resultSet.getInt("item_overrallRating");

                // Check if the restaurant name contains the search text
                if (itemName.contains(searchText)) {
                    JPanel menuItemContainer = createMenuItemContainer(itemName2, itemPrice, itemOverallRating, itemId, containerY);
                    menuPanel.add(menuItemContainer);

                    menuPanel.setBackground(CC);
                   menuItemContainer.setBorder(new EmptyBorder(0, 0, 0, 0));

                    containerY += containerHeight;
                }
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        menuPanel.setPreferredSize(new Dimension(300, containerY + 10));

        // Scroll to the beginning
        scrollPane.getVerticalScrollBar().setValue(0);

        // Repaint the panel to reflect the changes
        revalidate();
        repaint();
    }
    private void applysort() {
        JPanel sortOptionsPanel = new JPanel();
        sortOptionsPanel.setLayout(null);
        sortOptionsPanel.setPreferredSize(new Dimension(200, 200));

        JRadioButton defaultOption = new JRadioButton("Default");
        JRadioButton highToLowOption = new JRadioButton("High Rating to Low Rating");
        JRadioButton lowToHighOption = new JRadioButton("Low Rating to High Rating");
        JRadioButton lowToHighprice = new JRadioButton("Low Price to High Price");
        JRadioButton highToLowprice = new JRadioButton("High Price to Low Price");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(defaultOption);
        buttonGroup.add(highToLowOption);
        buttonGroup.add(lowToHighOption);
        buttonGroup.add(lowToHighprice);
        buttonGroup.add(highToLowprice);

        defaultOption.setBounds(10, 10, 250, 20);
        highToLowOption.setBounds(10, 40, 250, 20);
        lowToHighOption.setBounds(10, 70, 250, 20);
        lowToHighprice.setBounds(10, 100, 250, 20);
        highToLowprice.setBounds(10, 130, 250, 20);

        sortOptionsPanel.add(defaultOption);
        sortOptionsPanel.add(highToLowOption);
        sortOptionsPanel.add(lowToHighOption);
        sortOptionsPanel.add(lowToHighprice);
        sortOptionsPanel.add(highToLowprice);

        JButton applySortButton = new JButton("Apply");
        applySortButton.setBounds(10, 160, 80, 30);
        sortOptionsPanel.add(applySortButton);

        applySortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (highToLowOption.isSelected()) {
                    currentSortOrder = "HighToLowR";
                } else if (lowToHighOption.isSelected()) {
                    currentSortOrder = "LowToHighR";
                }
                else if (lowToHighprice.isSelected()) {
                    currentSortOrder = "LowToHighP";
                }
                else if (highToLowprice.isSelected()) {
                    currentSortOrder = "HighToLowP";
                }else {
                    currentSortOrder = "Default";
                }
               applySorting();
                closeSortOptionsPopup();
            }
        });

        int option = JOptionPane.showConfirmDialog(this, sortOptionsPanel, "Sort Options", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
           applySorting();
        }
    }
    private void closeSortOptionsPopup() {
        // Close the sorting options dialog if it's open
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                if (dialog.getTitle().equals("Sort Options")) {
                    dialog.dispose();
                }
            }
        }
    }
    private void applySorting() {
        menuPanel.removeAll();
        int containerY = 10;

        // Retrieve the menu items data from the database, sorted based on the currentSortOrder
        List<MenuItemData> sortedData = getSortedMenuItemsData();

        for (MenuItemData data : sortedData) {
            JPanel menuItemContainer = createMenuItemContainer(data.getName(), data.getPrice(), data.getOverallRating(), data.getId(), containerY);
            menuPanel.add(menuItemContainer);
            Color CC = new Color(30, 30, 30);
            menuPanel.setBackground(CC);
            menuItemContainer.setBorder(new EmptyBorder(0, 0, 0, 0));
            containerY += 50; // Adjust the height as needed
        }

        // Set the preferred size of the menuPanel
        menuPanel.setPreferredSize(new Dimension(300, containerY + 10));

        // Scroll to the beginning
        scrollPane.getVerticalScrollBar().setValue(0);

        // Repaint the panel to reflect the changes
        revalidate();
        repaint();
    }

    private List<MenuItemData> getSortedMenuItemsData() {
        List<MenuItemData> sortedData = new ArrayList<>();

        try {
            String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
            String dbUsername = "root";
            String dbPassword = "siddu";
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Define the sorting order based on the currentSortOrder
            String orderBy = "";
            if (currentSortOrder.equals("HighToLowR")) {
                orderBy = "item_overrallRating DESC"; // High Rating to Low Rating
            } else if (currentSortOrder.equals("LowToHighR")) {
                orderBy = "item_overrallRating ASC"; // Low Rating to High Rating
            } else if (currentSortOrder.equals("LowToHighP")) {
                orderBy = "item_price ASC"; // Low Price to High Price
            } else if (currentSortOrder.equals("HighToLowP")) {
                orderBy = "item_price DESC"; // High Price to Low Price
            } else {
                // Default sorting order (No specific sorting)
                orderBy = "item_id ASC";
            }

            String sql = "SELECT item_id, item_name, item_price, item_overrallRating " +
                    "FROM menuItems_data WHERE restaurants_id = ? ORDER BY " + orderBy;

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, restaurantId); // Set the restaurantId as a parameter

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                int itemPrice = resultSet.getInt("item_price");
                int itemOverallRating = resultSet.getInt("item_overrallRating");
                sortedData.add(new MenuItemData(itemName, itemPrice, itemOverallRating, itemId));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sortedData;
    }

    private class MenuItemData {
        private String name;
        private int price;
        private int overallRating;
        private int id;

        public MenuItemData(String name, int price, int overallRating, int id) {
            this.name = name;
            this.price = price;
            this.overallRating = overallRating;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getOverallRating() {
            return overallRating;
        }

        public int getId() {
            return id;
        }
    }


}
