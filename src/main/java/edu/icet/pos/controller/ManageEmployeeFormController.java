package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageEmployeeFormController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmpId();
    }

    public void saveBtnOnAction(ActionEvent actionEvent) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = format.parse(empDobDatePicker.getValue().toString());

        Employee employeeDto = new Employee(
                empIdText.getText(),
                empNameText.getText(),
                dob,
                empNicText.getText(),
                empContactNoText.getText(),
                empEmailText.getText(),
                empAddressText.getText()
        );
        boolean isSaved = bo.save(employeeDto);
        if(isSaved){//TRUE
            new Alert(Alert.AlertType.CONFIRMATION, "Employee added successfully !").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please try again !").show();
        }
    }

    private String loadEmpId(){
        System.out.println(bo.retrieveLastId().get(bo.retrieveLastId().size()-1));
        return null;
    }

    public void editBtnOnAction(ActionEvent actionEvent) {
    }

    public void deactivateBtnOnAction(ActionEvent actionEvent) {
    }

    public void empIdOnAction(ActionEvent actionEvent) {
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
    }
}
