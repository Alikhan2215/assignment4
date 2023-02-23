

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;


public class LoginPage extends JFrame{
    private JPanel panel1;
    private JTextField tfEnterEmail;
    private JPasswordField tfEnterPass;
    private JButton logInButton;
    private JButton forgotPasswordButton;
    private JLabel messageLabel;
    private JTextField tfRecoveryCode;
    private JLabel emailLabel;
    private JLabel passLabel;
    private JButton checkButton;

    public LoginPage() {
        Database db = new Database();
        Encryptor encryptor = new Encryptor();
        Userdata data = new Userdata();
        LogPassCheck correctData = new LogPassCheck();
        SlackMessageSender msgSender = new SlackMessageSender();


        Connection conn = db.connectToDB("database", "postgres", "soil467seamwall");
        String realLog = db.readEmail(conn, "users");
        String realPass = db.readPassword(conn, "users");

        setContentPane(panel1);
        setTitle("Welcome");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        tfRecoveryCode.setVisible(false);
        checkButton.setVisible(false);

        checkButton.setFocusable(false);
        logInButton.setFocusable(false);
        forgotPasswordButton.setFocusable(false);



        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==logInButton){
                    String email = tfEnterEmail.getText();
                    String password = String.valueOf(tfEnterPass.getPassword());

                    try {

                        if (correctData.checkData(email, password)) {
                            messageLabel.setText("The minimum length of the login and password is 6 characters");


                        }
                        else if (!correctData.checkString(password)) {
                            messageLabel.setText("Password should contain at least 1 capital, 1 lower case letter and a number");

                        }

                        else if (email.equals(realLog) && encryptor.encryptString(password).equals(realPass)) {
                            messageLabel.setText("LOGIN SUCCESSFUL");
                            msgSender.SuccessSender();
                            dispose();
                            WelcomePage welcomePage = new WelcomePage();
                        } else {
                            messageLabel.setText("LOGIN WRONG");
                            msgSender.FailSender();
                        }

                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });


        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==forgotPasswordButton){
                    data.codeWriter();
                    try {
                        EmailSender.main(null);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (MessagingException ex) {
                        throw new RuntimeException(ex);
                    }

                    messageLabel.setText("Recovery code was sent to your email.");
                    tfRecoveryCode.setVisible(true);
                    checkButton.setVisible(true);
                    logInButton.setVisible(false);
                    forgotPasswordButton.setVisible(false);
                    tfEnterEmail.setVisible(false);
                    tfEnterPass.setVisible(false);
                    emailLabel.setVisible(false);
                    passLabel.setVisible(false);

                }
            }
        });


        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recoveryToken = tfRecoveryCode.getText();

                if(recoveryToken.equals(EmailSender.getRecoveryCode())){
                    dispose();
                    NewPassword newPassword = new NewPassword();
                }
                else{
                    messageLabel.setText("Incorrect token");
                }
            }
        });
    }

    public static void main(String[] args){
        LoginPage loginPage = new LoginPage();
    }
}
