package edu.icet.pos.controller;

import edu.icet.pos.controller.form_validation.FormValidationController;
import edu.icet.pos.dto.User;
import edu.icet.pos.dto.holder_dto.CurrentUserHolder;
import edu.icet.pos.util.UserType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

public abstract class SuperFormController extends FormValidationController {
    //ABSTRACT METHODS
    abstract void save();
    abstract void clearForm();

    abstract void loadId();

    abstract void loadDetailTable();

    abstract void searchDetailById();
    abstract void updateById();

    //CONCRETE METHODS
    protected User setUser(Label userLbl){
        User user = CurrentUserHolder.getInstance().getUser();
        userLbl.setText(user.getSystemName());
        return user;
    }
    protected void loadMenu(Label userLbl, Button dashboardBtn, Button usersBtn){
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
    protected void loadManageStockCombo(ComboBox<String> stockCombo){
        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.add("Manage Stocks");
        typeOptions.add("Manage Products");
        stockCombo.setItems(typeOptions);
    }
    protected void loadManageReturnCombo(ComboBox<String> returnCombo) {
        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.add("Generate Return Note");
        typeOptions.add("Generate Credit Note");
        returnCombo.setItems(typeOptions);
    }
    protected void loadOrderForm(Button btn) throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) btn.getScene().getWindow();
        currentForm.close();
        //LOAD MANAGE ORDER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-orders-form.fxml"))));
        stage.show();
    }
    //STOCK AND PRODUCT MANAGEMENT
    protected void loadManageStockForms(ComboBox<String> stockCombo, String comboOption) throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) stockCombo.getScene().getWindow();
        currentForm.close();
        if (comboOption.equals("Manage Stocks")){
            //LOAD MANAGE STOCK FORM
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-stock-form.fxml"))));
            stage.show();
        } else if (comboOption.equals("Manage Products")) {
            //LOAD MANAGE PRODUCT FORM
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-product-form.fxml"))));
            stage.show();
        }
    }
    protected void loadEmployeeForm(Button btn) throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) btn.getScene().getWindow();
        currentForm.close();
        //LOAD MANAGE EMPLOYEE FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-employee-form.fxml"))));
        stage.show();
    }
    protected void loadUserForm(Button btn) throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) btn.getScene().getWindow();
        currentForm.close();
        //LOAD USER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-user-form.fxml"))));
        stage.show();
    }
    protected void loadCategoryForm(Button btn) throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) btn.getScene().getWindow();
        currentForm.close();
        //LOAD SUPPLIER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-product-category-form.fxml"))));
        stage.show();
    }
    protected void loadSupplierForm(Button btn) throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) btn.getScene().getWindow();
        currentForm.close();
        //LOAD SUPPLIER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-supplier-form.fxml"))));
        stage.show();
    }
    protected void loadCustomerForm(Button btn){
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) btn.getScene().getWindow();
        currentForm.close();
    }
    protected void loadManageReturnForms(ComboBox<String> returnCombo, String comboOption) throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) returnCombo.getScene().getWindow();
        currentForm.close();
        if (comboOption.equals("Generate Return Note")){
            //LOAD MANAGE RETURN NOTE FORM
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-return-form.fxml"))));
            stage.show();
        } else if (comboOption.equals("Generate Credit Note")) {
            //LOAD MANAGE CREDIT NOTE FORM
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/generate-creditnote-form.fxml"))));
            stage.show();
        }
    }
    protected void loadDashboardForm(Button btn) throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) btn.getScene().getWindow();
        currentForm.close();
        //LOAD ADMIN DASH BOARD FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/admin-dashboard-form.fxml"))));
        stage.show();
    }

    //INPUT VALIDATION
}
