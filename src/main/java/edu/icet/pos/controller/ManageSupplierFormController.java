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
import javafx.fxml.FXML;
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
public class ManageSupplierFormController extends SuperFormController implements Initializable {
    @FXML
    public TextField supplierIdTxt;
    @FXML
    public TextField supplierNameTxt;
    @FXML
    public TextField contactPersonTxt;
    @FXML
    public TextField contactNoTxt;
    @FXML
    public TextField emailTxt;
    @FXML
    public TextField addressTxt;
    @FXML
    public TableView<Supplier> supplierDetailTable;
    @FXML
    public TableColumn<Supplier, String> idCol;
    @FXML
    public TableColumn<Supplier, String> nameCol;
    @FXML
    public TableColumn<Supplier, String> contactPersonCol;
    @FXML
    public TableColumn<Supplier, String> contactNoCol;
    @FXML
    public TableColumn<Supplier, String> emailCol;
    @FXML
    public TableColumn<Supplier, String> addressCol;
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;
    private final SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
        loadId();
        loadDetailTable();
    }

    public void searchBtnOnAction() {
        searchDetailById();
    }

    public void saveBtnOnAction() {
        save();
    }

    public void updateBtnOnAction() {
        updateById();
    }

    @Override
    void save() {
        Supplier supplier = new Supplier(
                supplierIdTxt.getText(),
                supplierNameTxt.getText(),
                contactPersonTxt.getText(),
                contactNoTxt.getText(),
                emailTxt.getText(),
                addressTxt.getText()
        );
        try {
            boolean isSaved = supplierBo.save(supplier);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved successfully !").show();
                loadId();
                loadDetailTable();
                clearForm();
            }else {
                new Alert(Alert.AlertType.ERROR, "Please try again !").show();
            }
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    public void clearForm() {
        supplierNameTxt.setText("");
        contactPersonTxt.setText("");
        contactNoTxt.setText("");
        emailTxt.setText("");
        addressTxt.setText("");
    }

    @Override
    void loadId() {
        int number=0;
        try {
            List<String> supplierList = supplierBo.retrieveAllId();
            String lastSupId = supplierList.get(supplierList.size() - 1);
            number = Integer.parseInt(lastSupId.split("SUP")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            supplierIdTxt.setText("SUP0001");
        }
        number++;
        supplierIdTxt.setText(String.format("SUP%04d", number));
    }

    @Override
    void loadDetailTable() {
        try {
            List<Supplier> supplierList = supplierBo.retrieveAll();
            ObservableList<Supplier> observableList = FXCollections.observableList(supplierList);
            supplierDetailTable.setItems(observableList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            contactPersonCol.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
            contactNoCol.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    void searchDetailById() {
        String id = supplierIdTxt.getText();
        try {
            List<Supplier> supplierList = supplierBo.retrieveById(id);
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

    @Override
    void updateById() {
        Supplier supplier = new Supplier(
                supplierIdTxt.getText(),
                supplierNameTxt.getText(),
                contactPersonTxt.getText(),
                contactNoTxt.getText(),
                emailTxt.getText(),
                addressTxt.getText()
        );
        if (loadConfirmAlert("Confirm update ?")){
            try {
                int updatedRowCount = supplierBo.update(supplier);

                if (updatedRowCount>0){
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated successfully !").show();
                    loadId();
                    loadDetailTable();
                    clearForm();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Please try again !").show();
                }
            }catch (NullPointerException e){
                log.info(e.getMessage());
            }
        }
    }
}
