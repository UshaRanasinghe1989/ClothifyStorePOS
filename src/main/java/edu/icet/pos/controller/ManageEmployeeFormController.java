package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.dto.User;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.GetModelMapper;
import edu.icet.pos.util.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Slf4j
public class ManageEmployeeFormController extends SuperFormController implements Initializable {
    @FXML
    public Button productCategoryBtn;
    @FXML
    public Button updateBtn;
    @FXML
    public Button deleteBtn;
    @FXML
    public ComboBox<String> titleCombo;
    @FXML
    public RadioButton maleGenderRadioBtn;
    @FXML
    public RadioButton femaleGenderRadioBtn;
    @FXML
    public ComboBox<String> maritalStatusCombo;
    @FXML
    public TextField initialsTxt;
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
    private TextArea empAddressText;
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
    private static final String TRYAGAIN = "Please try again !";
    private User currentUser;
    private String selectedEmpId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //SET USERS DISPLAY NAME
        currentUser = setDisplayName();
        //LOAD DATE & TIME
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
        //LOAD COMBO BOXES
        loadManageStockCombo(manageStockCombo);
        loadManageReturnCombo(manageReturnCombo);
        loadTitleCombo();
        loadMaritalStatusCombo();
        //LOAD NEXT EMPLOYEE ID
        loadId();
        //TABLES
        loadDetailTable();
        //LOAD MENU FOR USER
        if (currentUser.getType()== UserType.USER){
            loadUserMenu();
        }
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
        searchBtnOnAction();
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
        initialsTxt.setText("");
        titleCombo.valueProperty().setValue(null);
        maritalStatusCombo.valueProperty().setValue(null);
        maleGenderRadioBtn.setSelected(false);
        femaleGenderRadioBtn.setSelected(false);
    }

    @Override
    void loadId() {
        int number = 0;
        try {
            List<String> list = employeeBo.retrieveAllId();
            List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
            String lastId = sortedList.get(sortedList.size() - 1);
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
        titleCombo.setValue(employee.getTitle());
        String gender = employee.getGender();
        if (Objects.equals(gender, "male")){
            maleGenderRadioBtn.setSelected(true);
        }else {
            femaleGenderRadioBtn.setSelected(true);
        }
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

    @Override
    void loadUserMenu() {
        dashboardBtn.setVisible(false);
        employeeBtn.setVisible(false);
        usersBtn.setVisible(false);
        updateBtn.setVisible(false);
        deleteBtn.setVisible(false);
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
    public void productCategoryBtnOnAction() {
        try {
            loadCategoryForm(productCategoryBtn);
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
    private Employee getEmployeeObj(){
        Employee employeeDto=null;
        String id = empIdText.getText();
        String name = empNameText.getText();
        LocalDate dob = empDobDatePicker.getValue();
        String nic = empNicText.getText();
        String contactNo = empContactNoText.getText();
        String email = empEmailText.getText();
        String address = empAddressText.getText();
        String title = titleCombo.getValue();
        String maritalStatus = maritalStatusCombo.getValue();
        String initials = initialsTxt.getText();
        //GET SELECTED GENDER
        String selectedGender;
        if (maleGenderRadioBtn.isSelected()){
            selectedGender = maleGenderRadioBtn.getText();
        }else {
            selectedGender = femaleGenderRadioBtn.getText();
        }

        if (id.isEmpty() || initials.isEmpty() || name.isEmpty() || nic.isEmpty() || contactNo.isEmpty() || email.isEmpty() || address.isEmpty() || title==null || selectedGender ==null || maritalStatus==null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all details !").show();
        }else {
            employeeDto = new Employee(
                    id,
                    title,
                    initials,
                    name,
                    dob,
                    nic,
                    selectedGender,
                    maritalStatus,
                    contactNo,
                    email,
                    address,
                    new Date()
            );
        }
        /*else {
            if (!emailFormatMatcher(email).matches()) {
                new Alert(Alert.AlertType.WARNING, "Wrong email format !").show();
            }else {
                if (!phoneNoFormatMatcher(contactNo).matches()) {
                    new Alert(Alert.AlertType.WARNING, "Wrong phone number format !").show();
                }else {
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
            }
        }*/
        return employeeDto;
    }
    private void loadTitleCombo(){
        try {
            ObservableList<String> titleOptions = FXCollections.observableArrayList();
            titleOptions.add("Mr");
            titleOptions.add("Mrs");
            titleOptions.add("Miss");

            titleCombo.setItems(titleOptions);
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }
    private void loadMaritalStatusCombo() {
        ObservableList<String> statusOptions = FXCollections.observableArrayList();
        statusOptions.add("Single");
        statusOptions.add("Married");
        maritalStatusCombo.setItems(statusOptions);
    }
}
