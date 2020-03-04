package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import db.Dane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sample.MyController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MakingQuiz {
    public Label titleQuiz;
    public Label labelQuestion;
    public JFXRadioButton option1radio;
    public JFXRadioButton option2radio;
    public JFXRadioButton option3radio;
    public JFXRadioButton option4radio;
    public JFXButton nextQuest;
    public Label labelNumQuest;
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();
    int iterator=0;
    int correctAns=0;
    int numQuestions;
    static double res;
    private ToggleGroup radioGroup;

    public void radioButtonSetup(ActionEvent actionEvent) {
        radioGroup= new ToggleGroup();
        option1radio.setToggleGroup(radioGroup);
        option2radio.setToggleGroup(radioGroup);
        option3radio.setToggleGroup(radioGroup);
        option4radio.setToggleGroup(radioGroup);
    }

    public void initialize() {
        titleQuiz.setText(StudentHomeScreen.chosenTest);

        try {
            questions=new Dane().getQuestions(new Dane().getQuizID(StudentHomeScreen.chosenTest));
            numQuestions = questions.size();
            setAnswersRadio();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void setAnswersRadio(){

        try {
            answers = new Dane().getAnswers(new Dane().getQuestionID(questions.get(iterator)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        labelQuestion.setText(questions.get(iterator));
        option1radio.setText(answers.get(0));
        option2radio.setText(answers.get(1));
        option3radio.setText(answers.get(2));
        option4radio.setText(answers.get(3));
        labelNumQuest.setText("Pytania : "+(iterator+1)+"/"+numQuestions);
        iterator++;

    }
    public void nextQuestion(ActionEvent actionEvent) throws SQLException {
        if (nextQuest.getText().equals("Następne Pytanie")){
            setColourRadioToDefault();
            setAnswersRadio();

            radioGroup.getSelectedToggle().setSelected(false);
            nextQuest.setText("Wybierz");
return;
        }


        else if (nextQuest.getText().equals("Wybierz")){
            Toggle selectedRadio;
            try{ selectedRadio=radioGroup.getSelectedToggle();}
            catch (NullPointerException e){e.printStackTrace();
                selectedRadio=null;}
                if (selectedRadio==null){
                    Notifications noti =   Notifications.create();
                    noti.text("Odpowiedź nie wybrana");
                    noti.title("Wybierz Odpowiedź");
                    noti.position(Pos.CENTER);
                    noti.hideAfter(Duration.millis(2000));
                    noti.showError();
                }else{

if (indexCorrectAnswer()==new Dane().getCorrectAnswerID(new Dane().getQuestionID(questions.get(iterator-1)))){
    System.out.println("Dobrze");
    correctAns++;
}

                    correctAnsLabel();
                    setColourRadio();

        nextQuest.setText("Następne Pytanie");
            }
        }
        if (nextQuest.getText().equals("Zakońć")){
            scoreResult();
            goResultWindow();

        }

        if (iterator==numQuestions){
            iterator=0;
            nextQuest.setText("Zakońć");

        }
    }
void  correctAnsLabel(){
    labelNumQuest.setText(labelNumQuest.getText()+"\nPoprawne odp. : "+correctAns+"/"+(iterator));
    }

    private void setColourRadioToDefault(){
        option1radio.setTextFill(Paint.valueOf("white"));
        option2radio.setTextFill(Paint.valueOf("white"));
        option3radio.setTextFill(Paint.valueOf("white"));
        option4radio.setTextFill(Paint.valueOf("white"));
    }
    private void setColourRadio() throws SQLException {
        System.out.println(new Dane().getCorrectAnswerID(new Dane().getQuestionID(questions.get(iterator-1)))+" ITS CORRECT ID");
        if (new Dane().getCorrectAnswerID(new Dane().getQuestionID(questions.get(iterator-1)))==0)
            option1radio.setTextFill(Paint.valueOf("green"));
        if (new Dane().getCorrectAnswerID(new Dane().getQuestionID(questions.get(iterator-1)))==1)
            option2radio.setTextFill(Paint.valueOf("green"));
        if (new Dane().getCorrectAnswerID(new Dane().getQuestionID(questions.get(iterator-1)))==2)
            option3radio.setTextFill(Paint.valueOf("green"));
        if (new Dane().getCorrectAnswerID(new Dane().getQuestionID(questions.get(iterator-1)))==3)
            option4radio.setTextFill(Paint.valueOf("green"));

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

    private void scoreResult(){
        res =  (100*correctAns)/numQuestions;
        try {
            System.out.println("taki jest id : _________"+MyController.idAccount);
            new Dane().setResult(MyController.idAccount,new Dane().getQuizID(StudentHomeScreen.chosenTest),res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
private void goResultWindow(){
    Parent root = null;//;

    try {
        root = FXMLLoader.load(getClass().getResource("/fxml/ResultScreen.fxml"));
        Stage stage=(Stage)titleQuiz.getScene().getWindow();
        Scene scene = new Scene(root, 950, 620);
        stage.setScene(scene);
       // stage.setMaximized(true);
    } catch (IOException ee) {
        ee.printStackTrace();
    }
}
    public void finishQuiz(ActionEvent actionEvent) {
        scoreResult();
        goResultWindow();
    }


}
