package controllers;
import db.Dane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.management.Notification;
import java.io.IOException;

public class AddQuizFXML {
    public JFXTextField quizTitle;
    public JFXTextArea question;
    public JFXTextField option1;
    public JFXTextField option2;
    public JFXTextField option3;
    public JFXTextField option4;
    public JFXRadioButton option1radio;
    public JFXRadioButton option2radio;
    public JFXRadioButton option3radio;
    public JFXRadioButton option4radio;
    public JFXButton addNextQuestion;
    public JFXButton submitQuiz;
    public JFXButton setQuizTitle;
    private ToggleGroup radioGroup;

    public void radioButtonSetup(ActionEvent actionEvent) {
        radioGroup= new ToggleGroup();
        option1radio.setToggleGroup(radioGroup);
        option2radio.setToggleGroup(radioGroup);
        option3radio.setToggleGroup(radioGroup);
        option4radio.setToggleGroup(radioGroup);

    }

    public void setQuizTitle(ActionEvent actionEvent) {
        System.out.println(quizTitle.getText());

        if (quizTitle.getText().trim().isEmpty()){
            Notifications noti =   Notifications.create();
            noti.text("Podaj Prawidłową Nazwę");
            noti.title("Nazwa Testu");
            noti.position(Pos.CENTER);
            noti.hideAfter(Duration.millis(2000));
            noti.showError();
        }
        else
        {
            new Dane().addQuizTitle(quizTitle.getText());
            quizTitle.setEditable(false);
        }
    }

    public void addNextQuestionFunc(ActionEvent actionEvent) {
        Toggle selectedRadio;
        try{ selectedRadio=radioGroup.getSelectedToggle();}
        catch (NullPointerException e){e.printStackTrace();
        selectedRadio=null;}

        if (question.getText().trim().isEmpty()||
                option1.getText().trim().isEmpty()||
                option2.getText().trim().isEmpty()||
                option3.getText().trim().isEmpty()||
                quizTitle.getText().trim().isEmpty()||
                selectedRadio==null) {

            Notifications noti =   Notifications.create();
            noti.text("Nieprawidłowo Podane Dane Lub Odpowiedź Nie Wybrana");
            noti.title("Dane nie prawidłowe");
            noti.position(Pos.CENTER);
            noti.hideAfter(Duration.millis(2000));
            noti.showError();

        }
        else if (quizTitle.isEditable()){
            Notifications noti =   Notifications.create();
            noti.text("Zatwierdź Nazwę Testu");
            noti.title("Nazwa Testu");
            noti.position(Pos.CENTER);
            noti.hideAfter(Duration.millis(2000));
            noti.showError();
        }else{
            new Dane().addQuestion(question.getText(), quizTitle.getText());
            new Dane().addAnswers(new String[]{option1.getText(),option2.getText(),option3.getText(),option4.getText()},question.getText(),indexCorrectAnswer());
            clean();
            radioGroup.getSelectedToggle().setSelected(false);

        }
    }

    private void clean(){
        question.setText("");
        option1.setText("");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private int indexCorrectAnswer(){
        if (option1radio.isSelected())
            return 0;
        if (option2radio.isSelected())
            return 1;
        if (option3radio.isSelected())
            return 2;
        if (option4radio.isSelected())
            return 3;
        return -1;
    }

    public void submitQuizFunc(ActionEvent actionEvent) {
        clean();
        quizTitle.setEditable(true);
        quizTitle.setText("");
        Notifications noti =   Notifications.create();
        noti.text("OK");
        noti.title("Test został dodany");
        noti.position(Pos.CENTER);
        noti.hideAfter(Duration.millis(2000));
        noti.show();

    }
    public void logOut(ActionEvent actionEvent) {
        Parent root = null;//;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
            Stage stage=(Stage)submitQuiz.getScene().getWindow();
            Scene scene = new Scene(root, 950, 620);
            stage.setScene(scene);
            //stage.setMaximized(true);
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
}
