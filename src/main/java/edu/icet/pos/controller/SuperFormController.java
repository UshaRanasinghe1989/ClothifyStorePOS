package edu.icet.pos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

public abstract class SuperFormController {
    //ABSTRACT METHODS
    abstract void save();
    abstract void clearForm();

    abstract void loadId();

    abstract void loadDetailTable();

    abstract void searchDetailById();
    abstract void updateById();

    //CONCRETE METHODS
    void getCurrentTime(Label label){
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            label.setText(
                    localTime.getHour() +" : "+localTime.getMinute()+ " : "+ localTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    void getCurrentDate(Label currentDateLbl){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDateLbl.setText(dateFormat.format(currentDate));
    }
    boolean loadConfirmAlert(String msg){
        boolean isConfirm = false;
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, btnYes, btnNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(btnNo)==btnYes) isConfirm = true;

        return isConfirm;
    }
}
