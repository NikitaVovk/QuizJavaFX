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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultStudentList {
    public AnchorPane mainPane;
    public AnchorPane paneList;
    public VBox paneVbox;
    //static String chosenTest;
    public void logOut(ActionEvent actionEvent) {
        Parent root = null;//;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/AdminHomeScreen.fxml"));
            Stage stage=(Stage)paneVbox.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AdminHomeScreenController.tabResult=false;
    }
        public void initialize() {
            // mainPane.getChildren().add(new Label("halo"));
            ArrayList<String> results = null;
            try {
                results = new Dane().getResult(new Dane().getQuizID(ResultList.chosenTest));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Label lb = new Label();
            for(String s : results){
                Separator sp = new Separator(Orientation.HORIZONTAL);
               // lb.setText(s);
                paneVbox.getChildren().add(new Label(s));
                paneVbox.getChildren().add(sp);

            }
        }

}
