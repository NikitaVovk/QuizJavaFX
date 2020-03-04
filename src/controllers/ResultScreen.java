package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultScreen {
    public Label resProcent;
    public Label isPassed;

    public void initialize() {
        resProcent.setText(String.valueOf(MakingQuiz.res)+"%");
        if (MakingQuiz.res>=50) {isPassed.setText("Zaliczono");isPassed.setTextFill(Paint.valueOf("green"));}
        else {isPassed.setText("Nie Zaliczono");isPassed.setTextFill(Paint.valueOf("red"));}
    }


    public void goHomeScreen(ActionEvent actionEvent) {
        Parent root = null;//;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/StudentHomeScreen.fxml"));
            Stage stage=(Stage)isPassed.getScene().getWindow();
            Scene scene = new Scene(root, 950, 620);

            stage.setScene(scene);
         //   stage.setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
