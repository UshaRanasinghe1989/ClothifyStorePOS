package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.dto.Employee;
import edu.icet.pos.dto.User;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.PasswordBasedEncryption;
import edu.icet.pos.util.UserType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

import static edu.icet.pos.controller.LoginFormController.saltValue;

public class ManageUserFormController extends PasswordBasedEncryption implements Initializable, FormController {
    public Label currentDateLbl;
    public Label timerLbl;
    public ComboBox selectEmpIdCombo;
    public TextField nameSystemText;
    public TableView<User>userDetailsTable;
    public TableColumn<User, String>userIdCol;
    public TableColumn<User, String>userTypeCol;
    public TableColumn<User, String>nameSystemCol;
    public TableColumn<User, String>userNameCol;
    public Label userNameLbl;
    public ComboBox userTypeCombo;
    public PasswordField passwordPw;
    public TextField userIdTxt;

    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
    private ObservableList<User> userList;
    private final static String TRYAGAIN = "Please try again !";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateTime();//LOAD CURRENT DATE AND TIME
        loadEmpIdCombo();//LOAD EXISTING EMPLOYEE ID LIST
        loadUserId();//LOAD NEXT USER ID
        loadUserTypeCombo();//LOAD USER TYPES

        userList = FXCollections.observableList(userBo.retrieveAll());//LOAD EXISTING USER RECORDS

        userIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameSystemCol.setCellValueFactory(new PropertyValueFactory<>("systemName"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadUserTable();//USER DETAILS TABLE
    }

    private void loadUserTable() {
        userList = FXCollections.observableList(userBo.retrieveAll());//LOAD EXISTING USER RECORDS
        userDetailsTable.setItems(userList);
    }

    private void loadUserId() {
        userList = FXCollections.observableList(userBo.retrieveAllId());//LOAD EXISTING USER RECORDS
        String lastUserId=null;
        if (userList.isEmpty()) {
            userIdTxt.setText("USR0001");
        } else{
            lastUserId = (String) userBo.retrieveAllId().get(userBo.retrieveAllId().size() - 1);
            int number = Integer.parseInt(lastUserId.split("USR")[1]);
            number++;
            userIdTxt.setText(String.format("USR%04d", number));
        }
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
        ObservableList<Object> typeOptions = FXCollections.observableArrayList();
        typeOptions.add(UserType.ADMIN);
        typeOptions.add(UserType.USER);
        userTypeCombo.setItems(typeOptions);
    }

    public void selectEmpComboOnAction(ActionEvent actionEvent) {
        loadEmail(selectEmpIdCombo.getValue().toString());
        String empId = selectEmpIdCombo.getValue().toString();

        User user = userBo.retrieveByEmpId(
                new ModelMapper().map(employeeBo.retrieveById(empId), Employee.class)
        );

        userIdTxt.setText(user.getId());
        userTypeCombo.setValue(user.getType());
        nameSystemText.setText(user.getSystemName());
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        User user = new User(
                userIdTxt.getText(),
                new ModelMapper().map(employeeBo.retrieveById(selectEmpIdCombo.getValue().toString()), EmployeeEntity.class),
                nameSystemText.getText(),
                userNameLbl.getText(),
                getEncryptedPassword(passwordPw.getText()),
                (UserType) userTypeCombo.getValue(),
                true,
                new Date()
        );
        boolean isSaved = userBo.save(user);
        if(isSaved){//TRUE
            new Alert(Alert.AlertType.CONFIRMATION, "User added successfully !").show();
            clearForm();
            loadUserId();
            loadUserTable();
        }else {
            new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
        }
    }
    //GENERATE ENCRYPTED PASSWORD
    private String getEncryptedPassword(String password){
        return PasswordBasedEncryption.generateSecurePassword(password);
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        User user = new User(
                userIdTxt.getText(),
                nameSystemText.getText(),
                passwordPw.getText(),
                (UserType) userTypeCombo.getValue()
        );
        if (loadConfirmAlert("Confirm update ?")) {
            int updatedRowCount = userBo.update(user);
            if (updatedRowCount > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "User updated successfully !").show();
                clearForm();
                loadUserId();
                loadUserTable();
            } else {
                new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
            }
        }
    }

    public void deactivateBtnOnAction(ActionEvent actionEvent) {
        User user = new User(
                userIdTxt.getText(),
                false
        );
        if (loadConfirmAlert("Confirm deactivate ?")) {
            int deactivatedRowCount = userBo.deactivateById(user);
            if (deactivatedRowCount > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "User deactivated successfully !").show();
                clearForm();
                loadUserId();
                loadUserTable();
            } else {
                new Alert(Alert.AlertType.ERROR, TRYAGAIN).show();
            }
        }
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
        User user = new ModelMapper().map(userBo.retrieveById(userIdTxt.getText()), User.class);
        userIdTxt.setText(user.getId());
        userTypeCombo.setValue(user.getType());
        userNameLbl.setText(user.getEmail());
        selectEmpIdCombo.setValue(user.getEmployeeEntity().getId());
        nameSystemText.setText(user.getSystemName());
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(currentDate);
    }

    @Override
    public boolean loadConfirmAlert(String msg) {
        boolean isConfirm = false;
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, btnYes, btnNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(btnNo)==btnYes) isConfirm = true;

        return isConfirm;
    }

    @Override
    public void clearForm() {
        nameSystemText.setText("");
        userNameLbl.setText("");
        selectEmpIdCombo.valueProperty().setValue(null);
        userTypeCombo.valueProperty().set(null);
        passwordPw.setText("");
    }
}
