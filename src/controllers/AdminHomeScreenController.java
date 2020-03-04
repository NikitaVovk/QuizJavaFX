package controllers;

import com.jfoenix.controls.JFXTabPane;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminHomeScreenController {
    public JFXTabPane adminTabPane;
    public Tab addQuiz;
    public Tab addStudent;
    public  Tab resultList;
    static boolean tabResult=true;

    public void initialize(){

    }


    public void addQuizTab(Event event) {
        try {
            Parent  node = FXMLLoader.load(getClass().getResource("/fxml/AddQuizFXML.fxml"));
            addQuiz.setContent(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStudentTab(Event event) {
        try {
            Parent  node = FXMLLoader.load(getClass().getResource("/fxml/AddStudent.fxml"));
            addStudent.setContent(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showResults(Event event) {

        try {
            Parent  node = FXMLLoader.load(getClass().getResource("/fxml/ResultList.fxml"));
            resultList.setContent(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
