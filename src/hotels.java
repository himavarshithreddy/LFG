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
    import java.util.ArrayList;
    import java.util.List;

    public class hotels extends JPanel {
        private JPanel restaurantPanel;
        private String currentSortOrder = "Default";

        private JScrollPane scrollPane;
        private JButton filter;
        private int restaurantsLoadedCount = 0;

        public hotels(JPanel lastVisitedPanel){
            setLayout(null);
            ImageIcon sortimg = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\sort.png");
            ImageIcon filterimg = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\filter.png");
            ImageIcon aboutimg = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\about.png");
            JButton filter=new JButton(filterimg);
            JButton sort=new JButton(sortimg);

            JButton about=new JButton(aboutimg);
            about.setBounds(240,7,50,40);
            about.setContentAreaFilled(false);
            about.setUI(new BasicButtonUI());
            about.setBorder(new EmptyBorder(0,0,0,0));
            sort.setContentAreaFilled(false);
            sort.setUI(new BasicButtonUI());
            sort.setBorder(new EmptyBorder(0,0,0,0));
            filter.setContentAreaFilled(false);
            filter.setUI(new BasicButtonUI());
            filter.setBorder(new EmptyBorder(0,0,0,0));
            ImageIcon user = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\user.png");
            JButton acc = new JButton(user);
            acc.setBounds(275,5,70,40);
            acc.setFont(new Font("Helvetica",Font.BOLD,30));
            acc.setBorder(new EmptyBorder(0,0,0,0));
            acc.setForeground(Color.white);
            acc.setContentAreaFilled(false);
            acc.setUI(new BasicButtonUI());
            about.setBorder(new EmptyBorder(0,0,0,0));
            JLabel title = new JLabel("Hotels");
            title.setForeground(Color.white);
            title.setBackground(Color.black);
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
            filter.setBounds(290,105,40,32);
                sort.setToolTipText("Sort");
                filter.setToolTipText("Filter");
                acc.setToolTipText("My Account");
                about.setToolTipText("About Us");
                acc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                about.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            about.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (lastVisitedPanel != null) {
                        start start = (start) SwingUtilities.getWindowAncestor(hotels.this);
                        start.setContentPane(new aboutus(hotels.this));
                        start.revalidate();
                    }
                }
            });
            acc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    start start = (start) SwingUtilities.getWindowAncestor(hotels.this);
                    start.setContentPane(new myacc(hotels.this));
                    start.revalidate();

                }
            });
            search.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    search.setText(""); // Clear the text field when it gains focus
                }
            });
            search.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String searchText = search.getText().toLowerCase();
                    searchfun(searchText);
                }
            });


            sort.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    applysort();
                }
            });
            filter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showFilterOptions();
                }
            });
            Color CC = new Color(30,30,30);
            setBackground(CC);
            add(acc);
            add(title);
            add(about);
            add(sort);
            add(filter);
            add(search);

            restaurantPanel = new JPanel();
            restaurantPanel.setLayout(null);
            scrollPane = new JScrollPane(restaurantPanel);
            scrollPane.setBounds(10, 160, 325, 520);
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

                    System.out.println("Total restaurants loaded: " + restaurantsLoadedCount);
                    add(acc);
                    add(title);
                    add(about);
                    add(sort);
                    add(filter);
                    add(search);
                }
            };


            Thread dataLoadingThread = new Thread(() -> {
                loadRestaurantsData();

                guiUpdateWorker.execute();
            });

            dataLoadingThread.start();


        }

        private void loadRestaurantsData() {
            int containerY = 10;
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
                String dbUsername = "root";
                String dbPassword = "siddu";
                Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

                String sql = "SELECT restaurants_name, restaurants_rating , restaurants_id FROM restaurants_data";
                PreparedStatement statement = conn.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();


                int containerHeight = 90;

                while (resultSet.next()) {
                    synchronized (this) {
                        // Use synchronized block to ensure thread safety
                        restaurantsLoadedCount++;

                        // Simulate some processing
                        Thread.sleep(100); // Add a delay to simulate processing time
                    }
                    String restaurantName = resultSet.getString("restaurants_name");
                    int restaurantRating = resultSet.getInt("restaurants_rating");
                    int restaurantid= resultSet.getInt("restaurants_id");

                    JPanel restaurantContainer = createRestaurantContainer(restaurantName, restaurantRating, restaurantid,containerY);
                    restaurantPanel.add(restaurantContainer);
                    Color CC = new Color(30,30,30);
                    restaurantPanel.setBackground(CC);
                    restaurantContainer.setBorder(new EmptyBorder(0,0,0,0));
                    scrollPane.getVerticalScrollBar().setValue(0);
                    containerY += containerHeight;
                }

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            restaurantPanel.setPreferredSize(new Dimension(300, containerY + 10));


        }
        private JPanel createRestaurantContainer(String name, int rating, int id, int y) {
            JPanel container = new JPanel();
            container.setLayout(null);
            container.setBounds(10, y, 290, 80);

            JTextArea nameLabel = new JTextArea(name);
            nameLabel.setEditable(false);


            JLabel ratingLabel = new JLabel("R: " + rating+ "/5");

            nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 17));
            nameLabel.setForeground(Color.black);
            ratingLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
            ratingLabel.setForeground(Color.black);
            ImageIcon htlimage = new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\hotel50.png");
            JLabel imageL=new JLabel(htlimage);
            container.setBackground(new Color(173, 216, 230)); // Light Blue

            nameLabel.setOpaque(false);
            imageL.setBounds(10,5,80,70);
            nameLabel.setBounds(100, 10, 115, 50);
            ratingLabel.setBounds(225, 10, 100, 20);
            nameLabel.setLineWrap(true);
            container.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            container.add(nameLabel);
            container.add(ratingLabel);
            container.add(imageL);
            container.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    start start = (start) SwingUtilities.getWindowAncestor(hotels.this);
                    menu menu = new menu(hotels.this);
                    menu.setRestaurantId(id);
                    start.setContentPane(menu);
                    start.revalidate();
                }
            });
            nameLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    start start = (start) SwingUtilities.getWindowAncestor(hotels.this);
                    menu menu = new menu(hotels.this);
                    menu.setRestaurantId(id);
                    start.setContentPane(menu);
                    start.revalidate();
                }
            });

            return container;


        }

        private void showFilterOptions() {
            // Create a panel to hold filter options
            JPanel filterOptionsPanel = new JPanel();
            filterOptionsPanel.setLayout(null);
            filterOptionsPanel.setPreferredSize(new Dimension(200, 200));

            // Create checkboxes for filter options
            JCheckBox southIndianCheckBox = new JCheckBox("South Indian");
            JCheckBox northIndianCheckBox = new JCheckBox("North Indian");
            JCheckBox spicyCheckBox = new JCheckBox("Spicy");
            JCheckBox valueForMoneyCheckBox = new JCheckBox("Value for Money");

            southIndianCheckBox.setBounds(10, 10, 120, 20);
            northIndianCheckBox.setBounds(10, 40, 120, 20);
            spicyCheckBox.setBounds(10, 70, 70, 20);
            valueForMoneyCheckBox.setBounds(10, 100, 120, 20);

            filterOptionsPanel.add(southIndianCheckBox);
            filterOptionsPanel.add(northIndianCheckBox);
            filterOptionsPanel.add(spicyCheckBox);
            filterOptionsPanel.add(valueForMoneyCheckBox);

            // Create an "Apply" button to apply filters
            JButton applyFilterButton = new JButton("Apply");
            applyFilterButton.setBounds(10, 130, 80, 30);
            filterOptionsPanel.add(applyFilterButton);

            // Add an action listener to the "Apply" button to apply filters
            applyFilterButton.addActionListener(applyFilterEvent -> {
                // Apply the selected filters based on checkbox states
                applyFilters(
                        southIndianCheckBox.isSelected(),
                        northIndianCheckBox.isSelected(),
                        spicyCheckBox.isSelected(),
                        valueForMoneyCheckBox.isSelected()
                );

                // Close the filter options popup
                closeFilterOptionsPopup();
            });

            // Show the filter options in a dialog
            int option = JOptionPane.showConfirmDialog(this, filterOptionsPanel, "Filter Options", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            // If OK button is clicked, apply the filters (code for applyFilters method)
            if (option == JOptionPane.OK_OPTION) {
                applyFilters(
                        southIndianCheckBox.isSelected(),
                        northIndianCheckBox.isSelected(),
                        spicyCheckBox.isSelected(),
                        valueForMoneyCheckBox.isSelected()
                );
            }
        }

        private void closeFilterOptionsPopup() {
            // Close the filter options dialog if it's open
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof JDialog) {
                    JDialog dialog = (JDialog) window;
                    if (dialog.getTitle().equals("Filter Options")) {
                        dialog.dispose();
                    }
                }
            }
        }

        private void applyFilters(boolean filterSouthIndian, boolean filterNorthIndian, boolean filterSpicy, boolean filterValueForMoney) {
            restaurantPanel.removeAll(); // Clear the existing restaurant containers

            // Reload the restaurants data with filters
            int containerY = 10;
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
                String dbUsername = "root";
                String dbPassword = "siddu";
                Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

                String sql = "SELECT restaurants_name, restaurants_rating, restaurants_id, south_indian, north_indian, spicy, valueformoney FROM restaurants_data";
                PreparedStatement statement = conn.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();

                int containerHeight = 90;

                while (resultSet.next()) {
                    String restaurantName = resultSet.getString("restaurants_name");
                    int restaurantRating = resultSet.getInt("restaurants_rating");
                    int restaurantId = resultSet.getInt("restaurants_id");
                    boolean isSouthIndian = resultSet.getBoolean("south_indian");
                    boolean isNorthIndian = resultSet.getBoolean("north_indian");
                    boolean isSpicy = resultSet.getBoolean("spicy");
                    boolean isValueForMoney = resultSet.getBoolean("valueformoney");

                    // Apply filters
                    if ((filterSouthIndian && isSouthIndian) ||
                            (filterNorthIndian && isNorthIndian) ||
                            (filterSpicy && isSpicy) ||
                            (filterValueForMoney && isValueForMoney)) {

                        JPanel restaurantContainer = createRestaurantContainer(restaurantName, restaurantRating, restaurantId, containerY);
                        restaurantPanel.add(restaurantContainer);
                        Color CC = new Color(30, 30, 30);
                        restaurantPanel.setBackground(CC);
                        restaurantContainer.setBorder(new EmptyBorder(0, 0, 0, 0));

                        containerY += containerHeight;
                    }
                }

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            restaurantPanel.setPreferredSize(new Dimension(300, containerY + 10));

            // Scroll to the beginning
            scrollPane.getVerticalScrollBar().setValue(0);
        }

        private void applysort() {
            JPanel sortOptionsPanel = new JPanel();
            sortOptionsPanel.setLayout(null);
            sortOptionsPanel.setPreferredSize(new Dimension(200, 200));

            JRadioButton defaultOption = new JRadioButton("Default");
            JRadioButton highToLowOption = new JRadioButton("High Rating to Low Rating");
            JRadioButton lowToHighOption = new JRadioButton("Low Rating to High Rating");

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(defaultOption);
            buttonGroup.add(highToLowOption);
            buttonGroup.add(lowToHighOption);

            defaultOption.setBounds(10, 10, 250, 20);
            highToLowOption.setBounds(10, 40, 250, 20);
            lowToHighOption.setBounds(10, 70, 250, 20);

            sortOptionsPanel.add(defaultOption);
            sortOptionsPanel.add(highToLowOption);
            sortOptionsPanel.add(lowToHighOption);

            JButton applySortButton = new JButton("Apply");
            applySortButton.setBounds(10, 100, 80, 30);
            sortOptionsPanel.add(applySortButton);

            applySortButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (highToLowOption.isSelected()) {
                        currentSortOrder = "HighToLow";
                    } else if (lowToHighOption.isSelected()) {
                        currentSortOrder = "LowToHigh";
                    } else {
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

        private void applySorting() {
            restaurantPanel.removeAll();
            int containerY = 10;

            // Retrieve the restaurant data from the database, sorted by ratings
            List<RestaurantData> sortedData = getSortedRestaurantData();

            for (RestaurantData data : sortedData) {
                JPanel restaurantContainer = createRestaurantContainer(data.getName(), data.getRating(), data.getId(), containerY);
                restaurantPanel.add(restaurantContainer);
                Color CC = new Color(30, 30, 30);
                restaurantPanel.setBackground(CC);
                restaurantContainer.setBorder(new EmptyBorder(0, 0, 0, 0));
                containerY += 90; // Adjust the height as needed
            }

            // Set the preferred size of the panel
            restaurantPanel.setPreferredSize(new Dimension(300, containerY + 10));

            // Scroll to the beginning
            scrollPane.getVerticalScrollBar().setValue(0);

            // Repaint the panel to reflect the changes
            revalidate();
            repaint();
        }

        private List<RestaurantData> getSortedRestaurantData() {
            List<RestaurantData> sortedData = new ArrayList<>();

            try {
                String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
                String dbUsername = "root";
                String dbPassword = "siddu";
                Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

                String orderBy = "ASC"; // Default sorting order (Low to High)
                if (currentSortOrder.equals("HighToLow")) {
                    orderBy = "DESC"; // High to Low
                }

                String sql = "SELECT restaurants_name, restaurants_rating, restaurants_id FROM restaurants_data " +
                        "ORDER BY restaurants_rating " + orderBy;

                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String restaurantName = resultSet.getString("restaurants_name");
                    int restaurantRating = resultSet.getInt("restaurants_rating");
                    int restaurantId = resultSet.getInt("restaurants_id");
                    sortedData.add(new RestaurantData(restaurantName, restaurantRating, restaurantId));
                }

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return sortedData;
        }

        // Other methods remain the same

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

        // Define a class to hold restaurant data
        private class RestaurantData {
            private String name;
            private int rating;
            private int id;

            public RestaurantData(String name, int rating, int id) {
                this.name = name;
                this.rating = rating;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public int getRating() {
                return rating;
            }

            public int getId() {
                return id;
            }
        }
        private void searchfun(String searchText){
             // Get the text from the search field

            // Clear the existing restaurant containers
            restaurantPanel.removeAll();

            int containerY = 10;

            try {
                String url = "jdbc:mysql://127.0.0.1:3306/food_guider";
                String dbUsername = "root";
                String dbPassword = "siddu";
                Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

                String sql = "SELECT restaurants_name, restaurants_rating, restaurants_id FROM restaurants_data";
                PreparedStatement statement = conn.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();

                int containerHeight = 90;

                while (resultSet.next()) {
                    String restaurantName = resultSet.getString("restaurants_name").toLowerCase();
                    String restaurantName2 = resultSet.getString("restaurants_name");// Convert to lowercase for case-insensitive search
                    int restaurantRating = resultSet.getInt("restaurants_rating");
                    int restaurantid = resultSet.getInt("restaurants_id");

                    // Check if the restaurant name contains the search text
                    if (restaurantName.contains(searchText)) {
                        JPanel restaurantContainer = createRestaurantContainer(restaurantName2, restaurantRating, restaurantid, containerY);
                        restaurantPanel.add(restaurantContainer);
                        Color CC = new Color(30, 30, 30);
                        restaurantPanel.setBackground(CC);
                        restaurantContainer.setBorder(new EmptyBorder(0, 0, 0, 0));

                        containerY += containerHeight;
                    }
                }

                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            restaurantPanel.setPreferredSize(new Dimension(300, containerY + 10));

            // Scroll to the beginning
            scrollPane.getVerticalScrollBar().setValue(0);

            // Repaint the panel to reflect the changes
            revalidate();
            repaint();

        }

    }


