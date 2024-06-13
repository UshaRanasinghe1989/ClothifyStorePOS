package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.util.BoType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageEmployeeFormController implements Initializable, FormController {

    public TextField empNameText;
    public TextField empNicText;
    public DatePicker empDobDatePicker;
    public TextField empContactNoText;
    public TextField empEmailText;
    public TextField empAddressText;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn dobCol;
    public TableColumn nicCol;
    public TableColumn contactNoCol;
    public TableColumn emailCol;
    public TableColumn addressCol;
    public Label currentDateLbl;
    public Label timerLbl;
    public TableView empDetailTable;
    public TextField empIdText;

    private EmployeeBo bo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);

    private ObservableList<Employee> employeeList;
    private final static String TRYAGAIN = "Please try again !";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeList = FXCollections.observableList(bo.retrieveAll());
        loadEmpId();
        loadDateTime();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
        nicCol.setCellValueFactory(new PropertyValueFactory<>("nic"));
        contactNoCol.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadEmployeeTable();
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(currentDate);
    }

    @Override
    public boolean loadConfirmAlert(String msg){
        boolean isConfirm = false;
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, btnYes, btnNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(btnNo)==btnYes) isConfirm = true;

        return isConfirm;
    }
    public void saveBtnOnAction() {
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
        boolean isSaved = bo.save(employeeDto);
        if(isSaved){//TRUE
            new Alert(Alert.AlertType.CONFIRMATION, "Employee added successfully !").show();
            clearForm();
            loadEmpId();
            loadEmployeeTable();
        }else {
            new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
        }
    }
    private void loadEmpId() {
        String lastEmpId=null;
        if (employeeList.isEmpty()) {
            empIdText.setText("EMP0001");
        } else{
            lastEmpId = (String) bo.retrieveAllId().get(bo.retrieveAllId().size() - 1);
            int number = Integer.parseInt(lastEmpId.split("EMP")[1]);
            number++;
            empIdText.setText(String.format("EMP%04d", number));
        }
    }

    public void editBtnOnAction() {
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
            int updatedRowCount = bo.update(employee);
            if(updatedRowCount>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated successfully !").show();
                clearForm();
                loadEmpId();
                loadEmployeeTable();
            }else {
                new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
            }
        }
    }
    public void deleteBtnOnAction() {
        if (loadConfirmAlert("Confirm delete ?")) {
            int noRowsDeleted = bo.deleteById(empIdText.getText());
            if (noRowsDeleted > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted successfully !").show();
                clearForm();
                loadEmpId();
                loadEmployeeTable();
            } else {
                new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
            }
        }
    }
    public void searchBtnOnAction() {
        Employee employee = new ModelMapper().map(bo.retrieveById(empIdText.getText()), Employee.class);
        empNameText.setText(employee.getName());
        empDobDatePicker.setValue(employee.getDob());
        empNicText.setText(employee.getNic());
        empContactNoText.setText(employee.getContactNo());
        empEmailText.setText(employee.getEmail());
        empAddressText.setText(employee.getAddress());
    }

    private void loadEmployeeTable(){
        employeeList = FXCollections.observableList(bo.retrieveAll());
        empDetailTable.setItems(employeeList);
    }

    public void clearForm(){
        empNameText.setText("");
        empDobDatePicker.setValue(LocalDate.parse(getCurrentDate()));
        empNicText.setText("");
        empContactNoText.setText("");
        empEmailText.setText("");
        empAddressText.setText("");
    }
}
