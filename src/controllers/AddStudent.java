package controllers;

import com.jfoenix.controls.JFXTextField;
import db.Dane;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class AddStudent {
    public JFXTextField emailField;
    public JFXTextField passwordField;

    public void addSudent(ActionEvent actionEvent) {

       if(emailField.getText().trim().isEmpty()||
               passwordField.getText().trim().isEmpty()){
           Notifications noti =   Notifications.create();
           noti.text("Nieprawidłowo Podane Dane");
           noti.title("Podaj dane");
           noti.position(Pos.CENTER);
           noti.hideAfter(Duration.millis(2000));
           noti.showError();

       }else {
           new Dane().addStudent(emailField.getText(),passwordField.getText());
           Notifications noti =   Notifications.create();
           noti.text("Operacja OK");
           noti.title("Użytkownik Dodany");
           noti.position(Pos.CENTER);
           noti.hideAfter(Duration.millis(2000));
           noti.show();
           emailField.setText("");
           passwordField.setText("");
       }
    }
}
