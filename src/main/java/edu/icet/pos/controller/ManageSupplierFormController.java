package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.dto.Supplier;
import edu.icet.pos.util.BoType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Slf4j
public class ManageSupplierFormController implements Initializable, FormController {
    public TextField supplierIdTxt;
    public TextField supplierNameTxt;
    public TextField contactPersonTxt;
    public TextField contactNoTxt;
    public TextField emailTxt;
    public TextField addressTxt;
    public TableView supplierDetailTable;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn contactPersonCol;
    public TableColumn contactNoCol;
    public TableColumn emailCol;
    public TableColumn addressCol;
    public Label currentDateLbl;
    public Label timerLbl;

    List supplierList;
    ObservableList observableList;

    SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateTime();
        loadSupplierId();
        loadSupplierTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactPersonCol.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        contactNoCol.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadSupplierTable() {
        try {
            supplierList = supplierBo.retrieveAll();
            observableList = FXCollections.observableList(supplierList);
            supplierDetailTable.setItems(observableList);
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    private void loadSupplierId() {
        String lastSupId=null;
        int number=0;
        try {
            supplierList = FXCollections.observableList(supplierBo.retrieveAllId());
            lastSupId = (String) supplierList.get(supplierList.size() - 1);
            number = Integer.parseInt(lastSupId.split("SUP")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            supplierIdTxt.setText("SUP0001");
        }
        number++;
        supplierIdTxt.setText(String.format("SUP%04d", number));
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
        String id = supplierIdTxt.getText();
        try {
            supplierList = supplierBo.retrieveById(id);
            Supplier supplier = new ModelMapper().map(supplierList.get(0), Supplier.class);

            supplierNameTxt.setText(supplier.getName());
            contactPersonTxt.setText(supplier.getContactPerson());
            contactNoTxt.setText(supplier.getContactNo());
            emailTxt.setText(supplier.getEmail());
            addressTxt.setText(supplier.getAddress());
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(
                supplierIdTxt.getText(),
                supplierNameTxt.getText(),
                contactPersonTxt.getText(),
                contactNoTxt.getText(),
                emailTxt.getText(),
                addressTxt.getText()
        );
        boolean isSaved = supplierBo.save(supplier);
        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved successfully !").show();
            loadSupplierId();
            loadSupplierTable();
            clearForm();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please try again !").show();
        }
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(
                supplierIdTxt.getText(),
                supplierNameTxt.getText(),
                contactPersonTxt.getText(),
                contactNoTxt.getText(),
                emailTxt.getText(),
                addressTxt.getText()
        );
        if (loadConfirmAlert("Confirm update ?")){
            int updatedRowCount = supplierBo.update(supplier);

            if (updatedRowCount>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated successfully !").show();
                loadSupplierId();
                loadSupplierTable();
                clearForm();
            }else {
                new Alert(Alert.AlertType.ERROR, "Please try again !").show();
            }
        }
    }

    @Override
    public void loadDateTime() {
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

    @Override
    public String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(currentDate);
    }

    @Override
    public boolean loadConfirmAlert(String msg) {
        boolean isConfirm = false;
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, btnYes, btnNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(btnNo)==btnYes) isConfirm = true;

        return isConfirm;
    }

    @Override
    public void clearForm() {
        supplierNameTxt.setText("");
        contactPersonTxt.setText("");
        contactNoTxt.setText("");
        emailTxt.setText("");
        addressTxt.setText("");
    }
}
