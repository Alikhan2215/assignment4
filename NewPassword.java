import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public class NewPassword extends JFrame {
    private JPanel recoveryPanel;
    private JPasswordField tfNewPassword;
    private JButton RecoveryButton;
    private JLabel messageRecoveryLabel;

    public NewPassword(){
        LogPassCheck correctData = new LogPassCheck();
        Database db = new Database();
        Userdata data = new Userdata();
        Encryptor encryptor = new Encryptor();


        Connection conn = db.connectToDB("database", "postgres", "soil467seamwall");
        String email = db.readEmail(conn, "users");


        setContentPane(recoveryPanel);
        setTitle("Recovery Page");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);


        messageRecoveryLabel.setVisible(false);
        RecoveryButton.setFocusable(false);


        RecoveryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==RecoveryButton){
                    String recover = String.valueOf(tfNewPassword.getPassword());
                    if (correctData.checkData(email, recover)) {
                        messageRecoveryLabel.setVisible(true);
                        messageRecoveryLabel.setText("The minimum length of the login and password is 6 characters");


                    }
                    else if (!correctData.checkString(recover)) {
                        messageRecoveryLabel.setVisible(true);
                        messageRecoveryLabel.setText("Password should contain at least 1 capital, 1 lower case letter and a number");

                    }

                    else{
                        try {
                            data.setRealPassword(encryptor.encryptString(recover));
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }
                        db.updateData(conn, "users", data.getRealPassword());
                        dispose();
                        LoginPage loginPage = new LoginPage();
                    }
                }
            }
        });
    }

    public static void main(String[] args){
        NewPassword newPassword = new NewPassword();
    }
}
