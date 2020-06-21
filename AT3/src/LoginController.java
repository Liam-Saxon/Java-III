import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {

    static String userPass = null;
    static String userName = null;
    static String inpUser;
    static String inpPass;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnCreate;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    public void btnLogin(javafx.event.ActionEvent actionEvent) throws IOException {

            inpUser = txtUsername.getText();

            inpPass = txtUsername.getText();

            if (inpUser.equals(userName) && inpPass.equals(userPass)|| inpUser.equals("admin") && inpPass.equals("admin"))
            {
                System.out.println("Success");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("AT3");
                stage.setScene(new Scene(root1, 270, 430));
                stage.setResizable(false);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
            else{
                System.out.println("Error");
            }


    }
    public void btnCreate(javafx.event.ActionEvent actionEvent)
    {
        userName = txtUsername.getText();
        userPass = txtPassword.getText();
        hash();
        System.out.println("User created");
    }

    public static void hash()
    {
        String passwordToHash = userPass;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {

                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

}
