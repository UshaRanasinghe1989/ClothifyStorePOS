package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.dto.User;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.PasswordBasedEncryption;
import edu.icet.pos.util.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.*;

@Slf4j
public class ManageUserFormController extends SuperFormController implements Initializable {
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;
    @FXML
    public ComboBox<String> selectEmpIdCombo;
    @FXML
    public TextField nameSystemText;
    @FXML
    public TableView<User>userDetailsTable;
    @FXML
    public TableColumn<User, String>userIdCol;
    @FXML
    public TableColumn<User, String>userTypeCol;
    @FXML
    public TableColumn<User, String>nameSystemCol;
    @FXML
    public TableColumn<User, String>userNameCol;
    @FXML
    public Label userNameLbl;
    @FXML
    private ComboBox<UserType> userTypeCombo;
    @FXML
    public PasswordField passwordPw;
    @FXML
    public TextField userIdTxt;
    private static final String TRY_AGAIN = "Please try again !";
    private final UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    private final EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        User user = new User(
                userIdTxt.getText(),
                employeeBo.retrieveById(selectEmpIdCombo.getValue()).get(0),
                nameSystemText.getText(),
                userNameLbl.getText(),
                getEncryptedPassword(passwordPw.getText()),
                userTypeCombo.getValue(),
                true,
                new Date()
        );
        boolean isSaved = userBo.save(user);
        if(isSaved){//TRUE
            new Alert(Alert.AlertType.CONFIRMATION, "User added successfully !").show();
            clearForm();
            loadId();
            loadDetailTable();
        }else {
            new Alert(Alert.AlertType.ERROR, TRY_AGAIN).show();
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

    private void loadEmpIdCombo(){
        ObservableList<String> list = FXCollections.observableList(employeeBo.retrieveAllId());
        selectEmpIdCombo.setItems(list);
    }

    private void loadEmail(String empId){
        Employee employee = new ModelMapper().map(employeeBo.retrieveById(empId), Employee.class);
        userNameLbl.setText(employee.getEmail());
    }

    private void loadUserTypeCombo(){
        ObservableList<UserType> typeOptions = FXCollections.observableArrayList();
        typeOptions.add(UserType.ADMIN);
        typeOptions.add(UserType.USER);
        userTypeCombo.setItems(typeOptions);
    }

    //GENERATE ENCRYPTED PASSWORD
    private String getEncryptedPassword(String password){
        return PasswordBasedEncryption.generateSecurePassword(password);
    }

    @Override
    void loadId() {
        int number=0;
        try {
            List<String> list = userBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableList(list);
            String lastId = observableList.get(observableList.size() - 1);
            number = Integer.parseInt(lastId.split("USR")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
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
        User user = new ModelMapper().map(userBo.retrieveById(userIdTxt.getText()), User.class);
        userIdTxt.setText(user.getId());
        userTypeCombo.setValue(user.getType());
        userNameLbl.setText(user.getEmail());
        selectEmpIdCombo.setValue(new ModelMapper().map(user.getEmployee(), Employee.class).getId());
        nameSystemText.setText(user.getSystemName());
    }

    @Override
    void updateById() {
        User user = new User(
                userIdTxt.getText(),
                nameSystemText.getText(),
                passwordPw.getText(),
                userTypeCombo.getValue()
        );
        if (loadConfirmAlert("Confirm update ?")) {
            int updatedRowCount = userBo.update(user);
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
}
