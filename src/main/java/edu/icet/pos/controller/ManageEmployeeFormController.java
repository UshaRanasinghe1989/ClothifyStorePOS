package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ManageEmployeeFormController extends SuperFormController implements Initializable {
    @FXML
    public TextField empNameText;
    @FXML
    public TextField empNicText;
    @FXML
    public DatePicker empDobDatePicker;
    @FXML
    public TextField empContactNoText;
    @FXML
    public TextField empEmailText;
    @FXML
    public TextField empAddressText;
    @FXML
    public TableColumn<Employee, String> idCol;
    @FXML
    public TableColumn<Employee, String> nameCol;
    @FXML
    public TableColumn<Employee, String> dobCol;
    @FXML
    public TableColumn<Employee, String> nicCol;
    @FXML
    public TableColumn<Employee, String> contactNoCol;
    @FXML
    public TableColumn<Employee, String> emailCol;
    @FXML
    public TableColumn<Employee, String> addressCol;
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;
    @FXML
    public TableView<Employee> empDetailTable;
    @FXML
    public TextField empIdText;

    private final EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);

    private static final String TRYAGAIN = "Please try again !";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
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
    public void searchBtnOnAction() {
        searchDetailById();
    }

    @Override
    void save() {
        Employee employeeDto = new Employee(
                empIdText.getText(),
                empNameText.getText(),
                empDobDatePicker.getValue(),
                empNicText.getText(),
                empContactNoText.getText(),
                empEmailText.getText(),
                empAddressText.getText(),
                new Date()
        );
        boolean isSaved = employeeBo.save(employeeDto);
        if(isSaved){//TRUE
            new Alert(Alert.AlertType.CONFIRMATION, "Employee added successfully !").show();
            clearForm();
            loadId();
            loadDetailTable();
        }else {
            new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
        }
    }

    @Override
    public void clearForm(){
        empNameText.setText("");
        empDobDatePicker.setValue(null);
        empNicText.setText("");
        empContactNoText.setText("");
        empEmailText.setText("");
        empAddressText.setText("");
    }

    @Override
    void loadId() {
        int number=0;
        try {
            List<String> list = employeeBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableList(list);
            String lastId = observableList.get(observableList.size() - 1);
            number = Integer.parseInt(lastId.split("EMP")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            empIdText.setText("EMP0001");
        }
        number++;
        empIdText.setText(String.format("EMP%04d", number));
    }

    @Override
    void loadDetailTable() {
        List<Employee> employeeList = employeeBo.retrieveAll();
        ObservableList<Employee> observableList= FXCollections.observableList(employeeList);

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
    void searchDetailById() {
        Employee employee = new ModelMapper().map(employeeBo.retrieveById(empIdText.getText()), Employee.class);
        empNameText.setText(employee.getName());
        empDobDatePicker.setValue(employee.getDob());
        empNicText.setText(employee.getNic());
        empContactNoText.setText(employee.getContactNo());
        empEmailText.setText(employee.getEmail());
        empAddressText.setText(employee.getAddress());
    }

    @Override
    void updateById() {
        if (loadConfirmAlert("Confirm update ?")){
            Employee employee = new Employee(
                    empIdText.getText(),
                    empNameText.getText(),
                    empDobDatePicker.getValue(),
                    empNicText.getText(),
                    empContactNoText.getText(),
                    empEmailText.getText(),
                    empAddressText.getText(),
                    new Date()
            );
            int updatedRowCount = employeeBo.update(employee);
            if(updatedRowCount>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated successfully !").show();
                clearForm();
                loadId();
                loadDetailTable();
            }else {
                new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
            }
        }
    }
}
