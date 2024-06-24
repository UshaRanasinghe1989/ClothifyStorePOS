package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.dto.User;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.GetModelMapper;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

@Slf4j
public class ManageEmployeeFormController extends SuperFormController implements Initializable {
    @FXML
    private TextField empNameText;
    @FXML
    private TextField empNicText;
    @FXML
    private DatePicker empDobDatePicker;
    @FXML
    private TextField empContactNoText;
    @FXML
    private TextField empEmailText;
    @FXML
    private TextField empAddressText;
    @FXML
    private TableColumn<Employee, String> idCol;
    @FXML
    private TableColumn<Employee, String> nameCol;
    @FXML
    private TableColumn<Employee, String> dobCol;
    @FXML
    private TableColumn<Employee, String> nicCol;
    @FXML
    private TableColumn<Employee, String> contactNoCol;
    @FXML
    private TableColumn<Employee, String> emailCol;
    @FXML
    private TableColumn<Employee, String> addressCol;
    @FXML
    private Label currentDateLbl;
    @FXML
    private Label timerLbl;
    @FXML
    private TableView<Employee> empDetailTable;
    @FXML
    public TextField empIdText;
    //MENU FIELDS
    @FXML
    public Label userLbl;
    @FXML
    public Button dashboardBtn;
    @FXML
    public ComboBox<String> manageStockCombo;
    @FXML
    public Button usersBtn;
    @FXML
    public ComboBox<String> manageReturnCombo;
    @FXML
    public Button ordersBtn;
    @FXML
    public Button supplierBtn;
    @FXML
    public Button customerBtn;
    @FXML
    public Button employeeBtn;
    @FXML
    public Button logoutBtn;

    private final EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private static final String TRYAGAIN = "Please try again !";
    private User user;
    private String selectedEmpId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = setDisplayName();
        loadMenu(userLbl, dashboardBtn, usersBtn);
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
        loadManageStockCombo(manageStockCombo);
        loadManageReturnCombo(manageReturnCombo);

        loadId();
        loadDetailTable();
    }

    public void saveBtnOnAction() {
        save();
    }

    public void editBtnOnAction() {
        updateById();
    }

    public void deleteBtnOnAction() {
        if (loadConfirmAlert("Confirm delete ?")) {
            int noRowsDeleted = employeeBo.deleteById(empIdText.getText());
            if (noRowsDeleted > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted successfully !").show();
                clearForm();
                loadId();
                loadDetailTable();
            } else {
                new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
            }
        }
    }
    @FXML
    public void searchBtnOnAction() {
        selectedEmpId = empIdText.getText();
        searchDetailById();
    }
    @FXML
    public void empIdTextOnAction() {
        selectedEmpId = empIdText.getText();
        searchDetailById();
    }

    @Override
    void save() {
        boolean isSaved = employeeBo.save(getEmployeeObj());
        if (isSaved) {//TRUE
            new Alert(Alert.AlertType.CONFIRMATION, "Employee added successfully !").show();
            clearForm();
            loadId();
            loadDetailTable();
        } else {
            new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
        }
    }

    @Override
    public void clearForm() {
        empNameText.setText("");
        empDobDatePicker.setValue(null);
        empNicText.setText("");
        empContactNoText.setText("");
        empEmailText.setText("");
        empAddressText.setText("");
    }

    @Override
    void loadId() {
        int number = 0;
        try {
            List<String> list = employeeBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableList(list);
            String lastId = observableList.get(observableList.size() - 1);
            number = Integer.parseInt(lastId.split("EMP")[1]);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            empIdText.setText("EMP0001");
        }
        number++;
        empIdText.setText(String.format("EMP%04d", number));
    }

    @Override
    void loadDetailTable() {
        List<Employee> employeeList = employeeBo.retrieveAll();
        ObservableList<Employee> observableList = FXCollections.observableList(employeeList);

        empDetailTable.setItems(observableList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
        nicCol.setCellValueFactory(new PropertyValueFactory<>("nic"));
        contactNoCol.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    @Override
    public void searchDetailById() {
        List<Employee> employeeList = employeeBo.retrieveById(selectedEmpId);
        Employee employee = employeeList.get(0);
        empNameText.setText(employee.getName());
        empDobDatePicker.setValue(employee.getDob());
        empNicText.setText(employee.getNic());
        empContactNoText.setText(employee.getContactNo());
        empEmailText.setText(employee.getEmail());
        empAddressText.setText(employee.getAddress());
    }

    @Override
    void updateById() {
        if (loadConfirmAlert("Confirm update ?")) {
            int updatedRowCount = employeeBo.update(getEmployeeObj());
            if (updatedRowCount > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated successfully !").show();
                clearForm();
                loadId();
                loadDetailTable();
            } else {
                new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
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

    public void logoutBtnOnAction() {
        //PENDING
    }

    public void dashboardBtnOnAction() {
        try {
            loadDashboardForm(dashboardBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
    private Employee getEmployeeObj(){
        Employee employeeDto=null;
        String id = empIdText.getText();
        String name = empNameText.getText();
        LocalDate dob = empDobDatePicker.getValue();
        String nic = empNicText.getText();
        String contactNo = empContactNoText.getText();
        String email = empEmailText.getText();
        String address = empAddressText.getText();

        if (id.isEmpty() || name.isEmpty() || nic.isEmpty() || contactNo.isEmpty() || email.isEmpty() || address.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all details !").show();
        } else if (!emailFormatMatcher(email).matches()) {
            new Alert(Alert.AlertType.WARNING, "Wrong email format !").show();
        } else if (!phoneNoFormatMatcher(contactNo).matches()) {
            new Alert(Alert.AlertType.WARNING, "Wrong phone number format !").show();
        } else {
            employeeDto = new Employee(
                    id,
                    name,
                    dob,
                    nic,
                    contactNo,
                    email,
                    address,
                    new Date()
            );
        }
        return employeeDto;
    }
}
