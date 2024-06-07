package edu.icet.pos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminDashboardFormController implements Initializable {
    public Label currentDateLbl;
    public Label timerLbl;
    public Label userLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateTime();
    }

    private void loadDateTime() {
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        currentDateLbl.setText(format.format(currentDate));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            timerLbl.setText(
                    localTime.getHour() +" : "+localTime.getMinute()+ " : "+ localTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadManageStockCombo(){

    }

    public void dashboardBtnOnAction(ActionEvent actionEvent) {
    }

    public void ordersBtnOnAction(ActionEvent actionEvent) {
    }

    public void returnBtnOnAction(ActionEvent actionEvent) {
    }

    public void manageStockComboOnAction(ActionEvent actionEvent) {
    }

    public void suppliersBtnOnAction(ActionEvent actionEvent) {
    }

    public void customersBtnOnAction(ActionEvent actionEvent) {
    }

    public void employeesBtnOnAction(ActionEvent actionEvent) throws IOException {
        //LOAD MANAGE EMPLOYEE MODULE
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-employee-form.fxml"))));
        stage.show();
    }

    public void usersBtnOnAction(ActionEvent actionEvent) {
    }

    public void logoutBtnOnAction(ActionEvent actionEvent) {
    }
}
