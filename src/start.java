    import javax.swing.*;
    import java.awt.event.*;
    import java.util.Stack;


    public class start extends JFrame {
        private JLabel image;
        private JLabel name;
        private JPanel mainpanel;
        private JLabel Click;


        public start() {
            setContentPane(mainpanel);

            setTitle("Local Food Guider");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(350, 700);
            setLocationRelativeTo(null);
            setVisible(true);

            login loginPanel = new login();
            mainpanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setContentPane(loginPanel);
                    revalidate();
                }
            });

        }


        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new start());
        }
        private void createUIComponents() {
            image = new JLabel(new ImageIcon("S:\\Code\\Projects\\JAVA\\LFG\\image.png"));
        }
    }
