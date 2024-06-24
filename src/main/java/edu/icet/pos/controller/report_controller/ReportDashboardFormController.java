package edu.icet.pos.controller.report_controller;

import edu.icet.pos.db_connection.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportDashboardFormController implements Initializable {
    @FXML
    private Label currentDateLbl;

    @FXML
    private Label timerLbl;

    @FXML
    private Label userLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @FXML
    void customersBtnOnAction(ActionEvent event) {

    }

    @FXML
    void dashboardBtnOnAction(ActionEvent event) {

    }

    @FXML
    void employeesBtnOnAction(ActionEvent event) {

    }


    @FXML
    void logoutBtnOnAction(ActionEvent event) {

    }

    @FXML
    void manageStockComboOnAction(ActionEvent event) {

    }

    @FXML
    void ordersBtnOnAction(ActionEvent event) {

    }

    @FXML
    void returnBtnOnAction(ActionEvent event) {

    }

    @FXML
    void suppliersBtnOnAction(ActionEvent event) {

    }

    @FXML
    void usersBtnOnAction(ActionEvent event) {

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
