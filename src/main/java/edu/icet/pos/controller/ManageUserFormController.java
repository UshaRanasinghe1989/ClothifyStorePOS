package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.dto.User;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.GetModelMapper;
import edu.icet.pos.util.PasswordBasedEncryption;
import edu.icet.pos.util.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ManageUserFormController extends SuperFormController implements Initializable {
    @FXML
    private ComboBox<String> selectEmpIdCombo;
    @FXML
    private TextField nameSystemText;
    @FXML
    private TableView<User> userDetailsTable;
    @FXML
    private TableColumn<User, String> userIdCol;
    @FXML
    private TableColumn<User, String> userTypeCol;
    @FXML
    private TableColumn<User, String> nameSystemCol;
    @FXML
    private TableColumn<User, String> userNameCol;
    @FXML
    private Label userNameLbl;
    @FXML
    private ComboBox<UserType> userTypeCombo;
    @FXML
    private PasswordField passwordPw;
    @FXML
    private TextField userIdTxt;
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
    private static final String TRY_AGAIN = "Please try again !";
    private final UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    private final EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = setDisplayName();
        getCurrentDate(currentDateLbl);//LOAD CURRENT DATE
        getCurrentTime(timerLbl);
        loadEmpIdCombo();//LOAD EXISTING EMPLOYEE ID LIST
        loadId();//LOAD NEXT USER ID
        loadUserTypeCombo();//LOAD USER TYPES
        loadDetailTable();
    }

    public void selectEmpComboOnAction() {
        loadEmail(String.valueOf(selectEmpIdCombo.getValue()));
        String empId = String.valueOf(selectEmpIdCombo.getValue());

        User user = userBo.retrieveByEmpId(empId);

        userIdTxt.setText(user.getId());
        userTypeCombo.setValue(user.getType());
        nameSystemText.setText(user.getSystemName());
    }

    public void saveBtnOnAction() {
        save();
    }

    public void updateBtnOnAction() {
        updateById();
    }

    public void deactivateBtnOnAction() {
        User user = new User(
                userIdTxt.getText(),
                false
        );
        if (loadConfirmAlert("Confirm deactivate ?")) {
            int deactivatedRowCount = userBo.deactivateById(user);
            if (deactivatedRowCount > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "User deactivated successfully !").show();
                clearForm();
                loadId();
                loadDetailTable();
            } else {
                new Alert(Alert.AlertType.ERROR, TRY_AGAIN).show();
            }
        }
    }

    public void searchBtnOnAction() {
        searchDetailById();
    }

    @Override
    public void save() {
        try {
            boolean isSaved = userBo.save(getUserObj());
            if (isSaved) {//TRUE
                new Alert(Alert.AlertType.CONFIRMATION, "User added successfully !").show();
                clearForm();
                loadId();
                loadDetailTable();
            } else {
                new Alert(Alert.AlertType.ERROR, TRY_AGAIN).show();
            }
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void clearForm() {
        nameSystemText.setText("");
        userNameLbl.setText("");
        selectEmpIdCombo.valueProperty().setValue(null);
        userTypeCombo.valueProperty().set(null);
        passwordPw.setText("");
    }

    @Override
    void loadId() {
        int number = 0;
        try {
            List<String> list = userBo.retrieveAllId();
            List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
            String lastId = sortedList.get(sortedList.size() - 1);
            number = Integer.parseInt(lastId.split("USR")[1]);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            userIdTxt.setText("USR0001");
        }
        number++;
        userIdTxt.setText(String.format("USR%04d", number));
    }

    @Override
    void loadDetailTable() {
        ObservableList<User> userList = FXCollections.observableList(userBo.retrieveAll());//LOAD EXISTING USER RECORDS
        userDetailsTable.setItems(userList);

        userIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameSystemCol.setCellValueFactory(new PropertyValueFactory<>("systemName"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @Override
    void searchDetailById() {
        String userId = userIdTxt.getText();
        User user = userBo.retrieveById(userId).get(0);
        userIdTxt.setText(user.getId());
        userTypeCombo.setValue(user.getType());
        userNameLbl.setText(user.getEmail());
        selectEmpIdCombo.setValue(mapper.map(user.getEmployee(), Employee.class).getId());
        nameSystemText.setText(user.getSystemName());
    }

    @Override
    void updateById() {
        if (loadConfirmAlert("Confirm update ?")) {
            int updatedRowCount = userBo.update(getUpdateUserObj());
            if (updatedRowCount > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "User updated successfully !").show();
                clearForm();
                loadId();
                loadDetailTable();
            } else {
                new Alert(Alert.AlertType.ERROR, TRY_AGAIN).show();
            }
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
        try {
            loadCustomerForm(customerBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void manageReturnComboOnAction() {
        String comboOption = manageReturnCombo.getValue();
        try {
            loadManageReturnForms(manageReturnCombo, comboOption);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void logoutBtnOnAction(ActionEvent event) {
        //PENDING
    }

    public void dashboardBtnOnAction() {
        try {
            loadDashboardForm(dashboardBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    //PRIVATE METHODS
    private void loadEmpIdCombo() {
        ObservableList<String> list = FXCollections.observableList(employeeBo.retrieveAllId());
        selectEmpIdCombo.setItems(list);
    }

    private void loadEmail(String empId) {
        Employee employee = new ModelMapper().map(employeeBo.retrieveById(empId), Employee.class);
        userNameLbl.setText(employee.getEmail());
    }

    private void loadUserTypeCombo() {
        ObservableList<UserType> typeOptions = FXCollections.observableArrayList();
        typeOptions.add(UserType.ADMIN);
        typeOptions.add(UserType.USER);
        userTypeCombo.setItems(typeOptions);
    }

    //GENERATE ENCRYPTED PASSWORD
    private String getEncryptedPassword(String password) {
        return PasswordBasedEncryption.generateSecurePassword(password);
    }

    private User getUserObj() {
        User user = null;
        String userId = userIdTxt.getText();
        String empId = selectEmpIdCombo.getValue();
        String displayName = nameSystemText.getText();
        String email = userNameLbl.getText();
        String pw = passwordPw.getText();
        UserType userType = userTypeCombo.getValue();

        if (userId.isEmpty() || empId.isEmpty() || displayName.isEmpty() || email.isEmpty() || pw.isEmpty() || userType == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all details !").show();
        } else {
            user = new User(
                    userId,
                    employeeBo.retrieveById(empId).get(0),
                    displayName,
                    email,
                    getEncryptedPassword(pw),
                    userType,
                    true,
                    new Date()
            );
        }
        return user;
    }
    //USER UPDATE OBJECT
    private User getUpdateUserObj() {
        User user = null;
        String userId = userIdTxt.getText();
        String displayName = nameSystemText.getText();
        String pw = passwordPw.getText();
        UserType userType = userTypeCombo.getValue();

        if (userId.isEmpty() || displayName.isEmpty() || pw.isEmpty() || userType == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all details !").show();
        } else {
            user = new User(
                    userIdTxt.getText(),
                    nameSystemText.getText(),
                    passwordPw.getText(),
                    userTypeCombo.getValue()
            );
        }
        return user;
    }
}
