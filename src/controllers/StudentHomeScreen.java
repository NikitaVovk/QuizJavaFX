package controllers;
import db.Dane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class StudentHomeScreen {
    public AnchorPane mainPane;
    public AnchorPane paneList;
    public VBox paneVbox;
     static String chosenTest;
    public void but(ActionEvent actionEvent) {
       // mainPane.getChildren().add(new Label("halo"));
    }
    public void initialize() {
        // mainPane.getChildren().add(new Label("halo"));
        ArrayList<String> quiz = new Dane().getQuizes();
        Font f = new Font(20);
        Hyperlink hp = null;
        Orientation orientation;

        for(String s : quiz){
             hp = new Hyperlink(s);
             hp.setLineSpacing(20);
            // hp.setTextFill(Paint.valueOf("white"));
             hp.setFont(new Font(30));
             hp.setPadding(new Insets(10,20,10,20));
            Hyperlink finalHp = hp;
            Hyperlink finalHp1 = hp;
            hp.setOnAction(new EventHandler<ActionEvent>() {
                 @Override public void handle(ActionEvent e) {
                     Parent root = null;//;
                     chosenTest=finalHp1.getText();
                     try {
                         root = FXMLLoader.load(getClass().getResource("/fxml/MakingQuiz.fxml"));
                         Stage stage=(Stage)paneVbox.getScene().getWindow();
                         Scene scene = new Scene(root, 950, 620);
                         stage.setScene(scene);
                         //stage.setMaximized(true);
                     } catch (IOException ee) {
                         ee.printStackTrace();
                     }
                 }
             });
            Separator sp = new Separator(Orientation.HORIZONTAL);
             paneVbox.getChildren().add(hp);
            paneVbox.getChildren().add(sp);

        }
    }

    public void logOut(ActionEvent actionEvent) {
        Parent root = null;//;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
            Stage stage=(Stage)paneVbox.getScene().getWindow();
            Scene scene = new Scene(root, 950, 620);
            stage.setScene(scene);
            //stage.setMaximized(true);
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
//    StudentHomeScreen(){
////        mainPane.getChildren().add(new Label("halo"));
//    }
}
