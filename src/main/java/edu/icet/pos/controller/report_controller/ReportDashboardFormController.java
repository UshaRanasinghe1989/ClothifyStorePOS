package edu.icet.pos.controller.report_controller;

import edu.icet.pos.db_connection.DBConnection;
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
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

@Slf4j
public class ReportDashboardFormController implements Initializable {
    @FXML
    private Label currentDateLbl;
    @FXML
    private Label timerLbl;
    @FXML
    private Label userLbl;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button ordersBtn;
    @FXML
    private ComboBox<String> manageStockCombo;
    @FXML
    private Button supplierBtn;
    @FXML
    private Button customerBtn;
    @FXML
    private Button employeeBtn;
    @FXML
    private Button usersBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private ComboBox<String> manageReturnCombo;
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = setUser();
        loadMenu();
        loadManageStockCombo();
        loadManageReturnCombo();
        getCurrentDate();
        getCurrentTime();
    }
    @FXML
    void generateOrderReportBtnOnAction() {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/jasper_report/ordersList.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getDbConnection().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            log.info(e.getMessage());
        }
    }
    @FXML
    void generateOrderDetailReportBtnOnAction() {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/jasper_report/orderDetails.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getDbConnection().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            log.info(e.getMessage());
        }
    }
    @FXML
    void generateStockReportBtnOnAction() {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/jasper_report/stocks.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getDbConnection().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            log.info(e.getMessage());
        }
    }
    @FXML
    void generateProductReportBtnOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/jasper_report/products.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getDbConnection().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            log.info(e.getMessage());
        }
    }
    //MENU
    public void dashboardBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) dashboardBtn.getScene().getWindow();
        currentForm.close();
        //LOAD ADMIN DASH BOARD FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/admin-dashboard-form.fxml"))));
        stage.show();
    }
    public void ordersBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) ordersBtn.getScene().getWindow();
        currentForm.close();
        //LOAD MANAGE ORDER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-orders-form.fxml"))));
        stage.show();
    }
    public void manageStockComboOnAction() throws IOException {
        String comboOption = manageStockCombo.getValue();
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) manageStockCombo.getScene().getWindow();
        currentForm.close();
        if (comboOption.equals("Manage Stocks")) {
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
    public void manageReturnComboOnAction() throws IOException {
        String comboOption = manageReturnCombo.getValue();
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) manageReturnCombo.getScene().getWindow();
        currentForm.close();
        if (comboOption.equals("Generate Return Note")) {
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
    public void customersBtnOnAction() {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) customerBtn.getScene().getWindow();
        currentForm.close();
    }
    public void suppliersBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) supplierBtn.getScene().getWindow();
        currentForm.close();
        //LOAD SUPPLIER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-supplier-form.fxml"))));
        stage.show();
    }
    public void usersBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) usersBtn.getScene().getWindow();
        currentForm.close();
        //LOAD USER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-user-form.fxml"))));
        stage.show();
    }
    public void employeesBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) employeeBtn.getScene().getWindow();
        currentForm.close();
        //LOAD MANAGE EMPLOYEE FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-employee-form.fxml"))));
        stage.show();
    }
    public void logoutBtnOnAction() {
        //PENDING
    }
    private User setUser() {
        User user = CurrentUserHolder.getInstance().getUser();
        userLbl.setText(user.getSystemName());
        return user;
    }
    private void loadMenu() {
        CurrentUserHolder currentUserHolder = CurrentUserHolder.getInstance();
        User user = currentUserHolder.getUser();
        if (user.getType().equals(UserType.USER)) {
            userLbl.setText(user.getSystemName());
            dashboardBtn.setVisible(false);
            usersBtn.setVisible(false);
        } else {
            userLbl.setText("Admin");
        }
    }
    private void loadManageStockCombo() {
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
    private void getCurrentTime(){
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
    private void getCurrentDate(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDateLbl.setText(dateFormat.format(currentDate));
    }
}
