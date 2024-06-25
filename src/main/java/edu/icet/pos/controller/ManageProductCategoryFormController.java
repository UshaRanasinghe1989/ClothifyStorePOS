package edu.icet.pos.controller;

import com.mysql.cj.log.Log;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.dto.Category;
import edu.icet.pos.dto.User;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.GetModelMapper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.UnknownEntityException;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
public class ManageProductCategoryFormController extends SuperFormController implements Initializable {
    @FXML
    private Label categoryIdTxt;
    @FXML
    private ComboBox<String> selectCategoryIdCombo;
    @FXML
    private TextField categoryNameTxt;
    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableColumn<Category, String> categoryIdCol;
    @FXML
    private TableColumn<Category, String> categoryNameCol;
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
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private final CategoryBo categoryBo = BoFactory.getInstance().getBo(BoType.CATEGORY);
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = setDisplayName();
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
        loadId();
        loadCategoryIdCombo();
        loadDetailTable();
    }

    @Override
    void save() {
        try {
            boolean isSaved = categoryBo.save(getCategoryObj());
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Category saved successfully !").show();
                loadId();
                loadCategoryIdCombo();
                clearForm();
            }else {
                new Alert(Alert.AlertType.ERROR, "Please try again !").show();
            }
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    private Category getCategoryObj() {
        String id = categoryIdTxt.getText();
        String name = categoryNameTxt.getText();
        Category category = null;

        if (id.isEmpty() || name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all details !").show();
        } else{
            category = new Category(
                    id,
                    name
            );
        }
        return category;
    }

    @Override
    public void clearForm() {
        categoryNameTxt.setText("");
    }

    @Override
    void loadId() {
        int number=0;
        try {
            List<String> list = categoryBo.retrieveAllId();
            List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
            String lastCatId = sortedList.get(sortedList.size() - 1);
            number = Integer.parseInt(lastCatId.split("CAT")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            categoryIdTxt.setText("CAT0001");
        }
        number++;
        categoryIdTxt.setText(String.format("CAT%04d", number));
    }

    @Override
    void loadDetailTable() {
        try {
            List<Category> categoryList = categoryBo.retrieveAll();
            ObservableList<Category> observableList = FXCollections.observableList(categoryList);
            categoryTableView.setItems(observableList);

            categoryIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    void searchDetailById() {
        String selectedCategory = selectCategoryIdCombo.getValue();
        List<Category> categoryList = categoryBo.retrieveById(selectedCategory);
        Category category = mapper.map(categoryList.get(0), Category.class);
        categoryNameTxt.setText(category.getName());
    }

    @Override
    void updateById() {
        if (loadConfirmAlert("Confirm update ?")){
            int rowCountUpdated = categoryBo.update(getCategoryObj());

            if (rowCountUpdated>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Category name updated successfully !").show();
                selectCategoryIdCombo.getSelectionModel().clearSelection();
                loadId();
                loadDetailTable();
                clearForm();
            }else {
                new Alert(Alert.AlertType.ERROR, "Please try again !").show();
            }
        }
    }

    public void saveBtnOnAction() {
        save();
    }

    public void updateBtnOnAction() {
        updateById();
    }

    public void catIdComboOnAction() {
        searchDetailById();
    }

    //MENU - LEFT BORDER
    public User setDisplayName(){
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
    }

    public void dashboardBtnOnAction() {
        try {
            loadDashboardForm(dashboardBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    private void loadCategoryIdCombo(){
        try {
            List<String> list = categoryBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableArrayList(list);
            selectCategoryIdCombo.setItems(observableList);
        }catch (NullPointerException e){
            log.info("Null pointer exception !");
        }
    }
}
