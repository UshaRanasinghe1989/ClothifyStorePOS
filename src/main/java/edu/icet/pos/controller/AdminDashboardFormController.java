package edu.icet.pos.controller;

import edu.icet.pos.dto.User;
import edu.icet.pos.dto.holder_dto.CurrentUserHolder;
import edu.icet.pos.util.UserType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminDashboardFormController implements Initializable {
    @FXML
    private ComboBox<String> manageStockCombo;
    @FXML
    private ComboBox<String> manageReturnCombo;
    @FXML
    private Label currentDateLbl;
    @FXML
    private Label timerLbl;
    @FXML
    private Label userLbl;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button usersBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateTime();
        loadMenu();
        loadManageStockCombo();
        loadManageReturnCombo();
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

    public void employeesBtnOnAction() throws IOException {
        //LOAD MANAGE EMPLOYEE MODULE
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-employee-form.fxml"))));
        stage.show();
    }

    public void usersBtnOnAction() {

    }

    public void logoutBtnOnAction(ActionEvent actionEvent) {
    }

    public void manageReturnComboOnAction(ActionEvent event) {
    }
    private void loadDateTime() {
        currentDateLbl.setText(getCurrentDate());

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
    private String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(currentDate);
    }
    private void loadManageStockCombo(){
        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.add("Manage Stocks");
        typeOptions.add("Manage Products");
        manageStockCombo.setItems(typeOptions);
    }
    private void loadManageReturnCombo() {
        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.add("Generate Return Note");
        typeOptions.add("Generate Credit Note");
        manageReturnCombo.setItems(typeOptions);
    }
    private void loadMenu(){
        CurrentUserHolder currentUserHolder = CurrentUserHolder.getInstance();
        User user = currentUserHolder.getUser();
        if (user.getType().equals(UserType.USER)){
            userLbl.setText(user.getSystemName());
            dashboardBtn.setVisible(false);
            usersBtn.setVisible(false);
        }else {
            userLbl.setText("Admin");
        }
    }
}
