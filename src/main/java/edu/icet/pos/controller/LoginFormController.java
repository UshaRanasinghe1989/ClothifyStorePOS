package edu.icet.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController  {
    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {
        //LOAD DASHBORD
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/admin-dashboard-form.fxml"))));
        stage.show();

    }

}
