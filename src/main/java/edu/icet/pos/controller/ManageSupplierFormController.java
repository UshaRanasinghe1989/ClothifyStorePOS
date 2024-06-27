package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.dto.Supplier;
import edu.icet.pos.dto.User;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.UserType;
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

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
public class ManageSupplierFormController extends SuperFormController implements Initializable {
    @FXML
    public Button productCategoryBtn;
    @FXML
    public Button updateBtn;
    @FXML
    private TextField supplierIdTxt;
    @FXML
    private TextField supplierNameTxt;
    @FXML
    private TextField contactPersonTxt;
    @FXML
    private TextField contactNoTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TableView<Supplier> supplierDetailTable;
    @FXML
    private TableColumn<Supplier, String> idCol;
    @FXML
    private TableColumn<Supplier, String> nameCol;
    @FXML
    private TableColumn<Supplier, String> contactPersonCol;
    @FXML
    private TableColumn<Supplier, String> contactNoCol;
    @FXML
    private TableColumn<Supplier, String> emailCol;
    @FXML
    private TableColumn<Supplier, String> addressCol;
    //MENU FIELDS
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;
    @FXML
    public Label userLbl;
    @FXML
    public Button dashboardBtn;
    @FXML
    public Button ordersBtn;
    @FXML
    public ComboBox<String> manageStockCombo;
    @FXML
    public Button supplierBtn;
    @FXML
    public Button customerBtn;
    @FXML
    public Button employeeBtn;
    @FXML
    public Button usersBtn;
    @FXML
    public Button logoutBtn;
    @FXML
    public ComboBox<String> manageReturnCombo;
    private User currentUser;
    private final SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserMenu();
        currentUser = setDisplayName();
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
        loadId();
        loadManageReturnCombo(manageReturnCombo);
        loadManageStockCombo(manageStockCombo);
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
        try {
            boolean isSaved = supplierBo.save(getSupplierObj());
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved successfully !").show();
                loadId();
                loadDetailTable();
                clearForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Please try again !").show();
            }
        } catch (NullPointerException e) {
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
        int number = 0;
        try {
            List<String> list = supplierBo.retrieveAllId();
            List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
            String lastSupId = sortedList.get(sortedList.size() - 1);
            number = Integer.parseInt(lastSupId.split("SUP")[1]);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
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
        } catch (NullPointerException e) {
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
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    void updateById() {
        if (loadConfirmAlert("Confirm update ?")) {
            try {
                int updatedRowCount = supplierBo.update(getSupplierObj());

                if (updatedRowCount > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated successfully !").show();
                    loadId();
                    loadDetailTable();
                    clearForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Please try again !").show();
                }
            } catch (NullPointerException e) {
                log.info(e.getMessage());
            }
        }
    }

    @Override
    void loadUserMenu() {
        if (currentUser.getType()== UserType.USER){
            dashboardBtn.setVisible(false);
            employeeBtn.setVisible(false);
            usersBtn.setVisible(false);
            updateBtn.setVisible(false);
        }
    }

    //MENU - LEFT BORDER
    public User setDisplayName() {
        return setUser(currentDateLbl);
    }

    //MENU FUNCTIONS
    public void ordersBtnOnAction() {
        try {
            loadOrderForm(ordersBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void manageStockComboOnAction() {
        String comboOption = manageStockCombo.getValue();
        try {
            loadManageStockForms(manageStockCombo, comboOption);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void employeesBtnOnAction() {
        try {
            loadEmployeeForm(employeeBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void usersBtnOnAction() {
        try {
            loadUserForm(usersBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void suppliersBtnOnAction() {
        try {
            loadSupplierForm(supplierBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void customersBtnOnAction() {
        loadCustomerForm(customerBtn);
    }

    public void manageReturnComboOnAction() {
        String comboOption = manageReturnCombo.getValue();
        try {
            loadManageReturnForms(manageReturnCombo, comboOption);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void logoutBtnOnAction() {
        systemLogout(logoutBtn);
    }

    public void dashboardBtnOnAction() {
        try {
            loadDashboardForm(dashboardBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
    public void productCategoryBtnOnAction() {
        try {
            loadCategoryForm(productCategoryBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
    private Supplier getSupplierObj() {
        Supplier supplier = null;
        String id = supplierIdTxt.getText();
        String name = supplierNameTxt.getText();
        String contactPerson = contactPersonTxt.getText();
        String contactNo = contactNoTxt.getText();
        String email = emailTxt.getText();
        String address = addressTxt.getText();

        if (id.isEmpty() || name.isEmpty() || contactPerson.isEmpty() || contactNo.isEmpty() || email.isEmpty() || address.isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please fill all details !").show();
        }else {
            supplier = new Supplier(
                    id,
                    name,
                    contactPerson,
                    contactNo,
                    email,
                    address
            );
        }
        return supplier;
    }
}
