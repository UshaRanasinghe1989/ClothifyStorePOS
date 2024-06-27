package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CreditNoteBo;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.bo.custom.ReturnBo;
import edu.icet.pos.dto.CreditNote;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Returns;
import edu.icet.pos.dto.User;
import edu.icet.pos.dto.holder_dto.CurrentUserHolder;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.UserType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class GenerateCreditNoteFormController implements Initializable {
    @FXML
    private Button productCategoryBtn;
    @FXML
    private Label categoryIdTxt;
    @FXML
    private Label creditNoteAmount;
    @FXML
    private ComboBox<String> selectOrderIdCombo;
    @FXML
    private ComboBox<Integer> selectReturnIdCombo;
    @FXML
    private Orders selectedOrderObj;
    @FXML
    private Returns returnsObj;
    @FXML
    private List<Returns> returnsList;
    //MENU FIELDS
    @FXML
    private Label currentDateLbl;
    @FXML
    private Label timerLbl;
    @FXML
    private Label userLbl;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button ordersBtn;
    @FXML
    private ComboBox<String> manageStockCombo;
    @FXML
    private Button supplierBtn;
    @FXML
    private Button customerBtn;
    @FXML
    private Button employeeBtn;
    @FXML
    private Button usersBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private ComboBox<String> manageReturnCombo;
    private User currentUser;

    private final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDERS);
    private final ReturnBo returnBo = BoFactory.getInstance().getBo(BoType.RETURN);
    private final CreditNoteBo creditNoteBo = BoFactory.getInstance().getBo(BoType.CREDIT_NOTE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserMenu();
        currentUser = setUser();
        loadMenu();
        loadManageStockCombo();
        loadManageReturnCombo();
        getCurrentDate();
        getCurrentTime();
        loadOrderIdCombo();
    }

    @FXML
    void orderIdComboOnAction() {
        String selectedOrderId = selectOrderIdCombo.getValue();
        try {
            selectedOrderObj = orderBo.retrieveById(selectedOrderId).get(0);
        } catch (IndexOutOfBoundsException e) {
            log.info(e.getMessage());
        }

        loadReturnIdCombo();
    }

    @FXML
    void returnIdComboOnAction() {
        try {
            int selectedReturnId = selectReturnIdCombo.getValue();

            for (int i = 0; i < returnsList.size(); i++) {
                int returnId = returnsList.get(i).getId();
                if (selectedReturnId == returnId) {
                    returnsObj = returnsList.get(i);
                    creditNoteAmount.setText(String.valueOf(returnsObj.getPrice()));
                }
            }
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }

    @FXML
    void saveBtnOnAction() {
        boolean isSaved = creditNoteBo.save(getCreditNoteObj());
        if (isSaved) {
            int returnStatusUpdateRowCount = returnStatusUpdate();
            if (returnStatusUpdateRowCount > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Credit note saved successfully !").show();
                clearForm();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please try again !").show();
        }
    }

    private void loadOrderIdCombo() {
        List<String> ordersList = orderBo.retrieveAllId();
        ObservableList<String> observableList = FXCollections.observableList(ordersList);

        selectOrderIdCombo.setItems(observableList);
    }

    private void loadReturnIdCombo() {
        returnsList = returnBo.retrieveReturnByOrder(selectedOrderObj);
        List<Integer> returnIdList = new ArrayList<>();

        for (int i = 0; i < returnsList.size(); i++) {
            returnIdList.add(returnsList.get(i).getId());
        }

        ObservableList<Integer> observableList = FXCollections.observableList(returnIdList);
        selectReturnIdCombo.setItems(observableList);
    }

    private CreditNote getCreditNoteObj() {
        return new CreditNote(
                returnsObj,
                Double.parseDouble(creditNoteAmount.getText()),
                false,
                new Date()
        );
    }

    private void clearForm() {
        selectOrderIdCombo.valueProperty().setValue(null);
        selectReturnIdCombo.valueProperty().setValue(null);
        creditNoteAmount.setText("");
    }

    private void getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDateLbl.setText(dateFormat.format(currentDate));
    }

    private void getCurrentTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            timerLbl.setText(
                    localTime.getHour() + " : " + localTime.getMinute() + " : " + localTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private int returnStatusUpdate() {
        return returnBo.updateCreditNoteStatus(returnsObj.getId());
    }

    //MENU
    public void dashboardBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) dashboardBtn.getScene().getWindow();
        currentForm.close();
        //LOAD ADMIN DASH BOARD FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/admin-dashboard-form.fxml"))));
        stage.show();
    }

    public void ordersBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) ordersBtn.getScene().getWindow();
        currentForm.close();
        //LOAD MANAGE ORDER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-orders-form.fxml"))));
        stage.show();
    }

    public void manageStockComboOnAction() throws IOException {
        String comboOption = manageStockCombo.getValue();
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) manageStockCombo.getScene().getWindow();
        currentForm.close();
        if (comboOption.equals("Manage Stocks")) {
            //LOAD MANAGE STOCK FORM
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-stock-form.fxml"))));
            stage.show();
        } else if (comboOption.equals("Manage Products")) {
            //LOAD MANAGE PRODUCT FORM
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-product-form.fxml"))));
            stage.show();
        }
    }

    public void manageReturnComboOnAction() throws IOException {
        String comboOption = manageReturnCombo.getValue();
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) manageReturnCombo.getScene().getWindow();
        currentForm.close();
        if (comboOption.equals("Generate Return Note")) {
            //LOAD MANAGE RETURN NOTE FORM
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-return-form.fxml"))));
            stage.show();
        } else if (comboOption.equals("Generate Credit Note")) {
            //LOAD MANAGE CREDIT NOTE FORM
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/generate-creditnote-form.fxml"))));
            stage.show();
        }
    }

    public void customersBtnOnAction() {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) customerBtn.getScene().getWindow();
        currentForm.close();
    }

    public void suppliersBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) supplierBtn.getScene().getWindow();
        currentForm.close();
        //LOAD SUPPLIER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-supplier-form.fxml"))));
        stage.show();
    }

    public void usersBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) usersBtn.getScene().getWindow();
        currentForm.close();
        //LOAD USER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-user-form.fxml"))));
        stage.show();
    }

    public void employeesBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) employeeBtn.getScene().getWindow();
        currentForm.close();
        //LOAD MANAGE EMPLOYEE FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-employee-form.fxml"))));
        stage.show();
    }

    public void productCategoryBtnOnAction() throws IOException {
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) productCategoryBtn.getScene().getWindow();
        currentForm.close();
        //LOAD SUPPLIER FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-product-category-form.fxml"))));
        stage.show();
    }

    public void logoutBtnOnAction() {
        CurrentUserHolder.getInstance().setUser(null);
        //CLOSE CURRENT FORM
        Stage currentForm = (Stage) logoutBtn.getScene().getWindow();
        currentForm.close();
    }

    private User setUser() {
        User user = CurrentUserHolder.getInstance().getUser();
        userLbl.setText(user.getSystemName());
        return user;
    }

    private void loadMenu() {
        CurrentUserHolder currentUserHolder = CurrentUserHolder.getInstance();
        User user = currentUserHolder.getUser();
        if (user.getType().equals(UserType.USER)) {
            userLbl.setText(user.getSystemName());
            dashboardBtn.setVisible(false);
            usersBtn.setVisible(false);
        } else {
            userLbl.setText("Admin");
        }
    }

    private void loadManageStockCombo() {
        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.add("Manage Stocks");
        typeOptions.add("Manage Products");
        manageStockCombo.setItems(typeOptions);
    }

    private void loadManageReturnCombo() {
        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.add("Generate Return Note");
        typeOptions.add("Generate Credit Note");
        manageReturnCombo.setItems(typeOptions);
    }
    private void loadUserMenu() {
        if (currentUser.getType()== UserType.USER){
            dashboardBtn.setVisible(false);
            employeeBtn.setVisible(false);
            usersBtn.setVisible(false);
        }
    }
}
