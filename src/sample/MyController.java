package sample;

import constants.AdminLoginPassword;
import db.Dane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MyController {

    public Button adminLoginButton;
    public TextField adminEmail;
    public PasswordField adminPassword;
    public Button studentLoginButton;
    public PasswordField studentPassword;
    public TextField studentEmail;
    public static int idAccount;

    public void loginAdmin(ActionEvent actionEvent) {
        System.out.println("AdninLogin");
        String email = adminEmail.getText();
        String password = adminPassword.getText();
       if( (new Dane()).porownaj(email,password,1)!=-1){
           System.out.println("Succes");
           idAccount=(new Dane()).porownaj(email,password,1);
           Parent root = null;//;
           try {
               root = FXMLLoader.load(getClass().getResource("/fxml/AdminHomeScreen.fxml"));
               Stage stage=(Stage)studentPassword.getScene().getWindow();
               Scene scene = new Scene(root);
               stage.setScene(scene);
               stage.setMaximized(true);
           } catch (IOException e) {
               e.printStackTrace();
           }



       }
       else System.out.println("No succes");

//        if (email.trim().equalsIgnoreCase(AdminLoginPassword.email)&&password.trim().equals(AdminLoginPassword.password)){
//            System.out.println("Succes");
//        }
//        else System.out.println("No succes");
    }



    public void loginStudent(ActionEvent actionEvent) {
        System.out.println("StudentLogin");

        String email = studentEmail.getText();
        String password = studentPassword.getText();
        if( (new Dane()).porownaj(email,password,2)!= -1){
            System.out.println("Succes");
            idAccount=(new Dane()).porownaj(email,password,2);
            Parent root = null;//;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/StudentHomeScreen.fxml"));
                Stage stage=(Stage)studentPassword.getScene().getWindow();
                Scene scene = new Scene(root, 950, 620);
                stage.setScene(scene);
                // stage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        else System.out.println("No succes");



    }
}
