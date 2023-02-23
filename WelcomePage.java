import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame{
    private JButton logOutButton;
    private JTextArea welcomeText;
    private JPanel welcomePagePanel;

    public WelcomePage(){
        welcomePagePanel.setBackground(Color.white);
        setContentPane(welcomePagePanel);
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        logOutButton.setFocusable(false);


        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==logOutButton){
                    dispose();
                    LoginPage loginPage = new LoginPage();
                }
            }
        });
    }

    public static void main(String[] args){
        WelcomePage welcomePage = new WelcomePage();
    }
}
