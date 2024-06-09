package edu.icet.pos.controller;

import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.EmployeeDao;
import edu.icet.pos.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageUserFormController implements Initializable {
    public Label currentDateLbl;
    public Label timerLbl;
    public TableView empDetailTable;
    public ComboBox selectEmpIdCombo;
    public TextField nameSystemText;
    public TableView userDetailsTable;
    public TableColumn empIdCol;
    public TableColumn userTypeCol;
    public TableColumn nameSystemCol;
    public TableColumn usernameCol;
    public Label userNameLbl;
    public ComboBox userTypeCombo;
    public PasswordField passwordPw;
    public TextField userIdTxt;

    EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmpIdCombo();
    }
    private void loadEmpIdCombo(){
        ObservableList<String> list = FXCollections.observableList(employeeDao.retrieveAllId());
        selectEmpIdCombo.setValue(list);
    }

    public void selectEmpComboOnAction(ActionEvent actionEvent) {
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
    }

    public void editBtnOnAction(ActionEvent actionEvent) {
    }

    public void deactivateBtnOnAction(ActionEvent actionEvent) {
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
    }
}
