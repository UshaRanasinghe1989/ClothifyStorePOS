package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.dto.Category;
import edu.icet.pos.dto.Product;
import edu.icet.pos.dto.Supplier;
import edu.icet.pos.dto.User;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.GetModelMapper;
import edu.icet.pos.util.ProductSizes;
import edu.icet.pos.util.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class ManageProductFromController extends SuperFormController implements Initializable {
    @FXML
    private TextField productNameText;
    @FXML
    private TextField imageOnePathTxt;
    @FXML
    private TextField imageTwoPathTxt;
    @FXML
    private TextField imageThreePathTxt;
    @FXML
    private TableView<Product> productDetailTable;
    @FXML
    private TableColumn<Product, String> idCol;
    @FXML
    private TableColumn<Product, String> nameCol;
    @FXML
    private TableColumn<Product, String> sizeCol;
    @FXML
    private TableColumn<Product, String> descriptionCol;
    @FXML
    private TextField productIdText;
    @FXML
    private ComboBox<String> categoryNameCombo;
    @FXML
    private TextField selectedCatIdHiddenTxt;
    @FXML
    private ComboBox<String> supplierNameCombo;
    @FXML
    private TextField selectedSupplierIdHiddenTxt;
    @FXML
    private ComboBox<ProductSizes> sizeCombo;
    @FXML
    private TextField descriptionTxt;
    @FXML
    private TextField imageFourPathTxt;
    @FXML
    private TextField imageFivePathTxt;
    @FXML
    private ImageView imageOne;
    //MENU FIELDS
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
    private User user;

    private final ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    private final CategoryBo categoryBo = BoFactory.getInstance().getBo(BoType.CATEGORY);
    private final SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = setDisplayName();
        getCurrentTime(timerLbl);
        getCurrentDate(currentDateLbl);
        loadId();
        loadCategoryNamesCombo();
        loadSupplierNamesCombo();
        loadSizeCombo();
        loadDetailTable();
    }

    public void saveBtnOnAction() {
        save();
    }

    public void updateBtnOnAction() {
        updateById();
    }

    public void searchBtnOnAction() {
        searchDetailById();
    }

    public void categoryNameComboOnAction() {
        try {
            String categoryName = categoryNameCombo.getValue();
            if (categoryName != null) {
                List<String> list = categoryBo.retrieveCatIdByName(categoryName);
                selectedCatIdHiddenTxt.setText(list.get(0));
            }
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }

    public void supplierNameComboOnAction() {
        try {
            String supplierName = supplierNameCombo.getValue();
            if (supplierName != null) {
                List<String> list = supplierBo.retrieveSupplierIdByName(supplierName);
                selectedSupplierIdHiddenTxt.setText(list.get(0));
            }
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }

    /*public void browseImgOneBtnOnAction(ActionEvent actionEvent) {
    }

    public void browseImgTwoBtnOnAction(ActionEvent actionEvent) {
    }

    public void browseImgThreeBtnOnAction(ActionEvent actionEvent) {
    }

    public void browseImgFourBtnOnAction(ActionEvent actionEvent) {
    }

    public void browseImgFiveBtnOnAction(ActionEvent actionEvent) {
    }*/

    @Override
    void save() {
        List<Category> categoryList = categoryBo.retrieveById(selectedCatIdHiddenTxt.getText());
        Category category = categoryList.get(0);

        List<Supplier> supplierList = supplierBo.retrieveById(selectedSupplierIdHiddenTxt.getText());
        Supplier supplier = supplierList.get(0);

        boolean isSaved = productBo.save(getProductObj(category, supplier));
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Product saved successfully !").show();
            loadId();
            loadDetailTable();
            clearForm();
        } else {
            new Alert(Alert.AlertType.ERROR, "Please try again !").show();
        }
    }

    @Override
    void clearForm() {
        productNameText.setText("");
        categoryNameCombo.valueProperty().set(null);
        selectedCatIdHiddenTxt.setText("");
        supplierNameCombo.valueProperty().set(null);
        selectedSupplierIdHiddenTxt.setText("");
        sizeCombo.valueProperty().set(null);
        descriptionTxt.setText("");
    }

    @Override
    void loadId() {
        String lastId = null;
        int number = 0;
        try {
            List<String> list = productBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableList(list);
            lastId = observableList.get(observableList.size() - 1);
            number = Integer.parseInt(lastId.split("PRO")[1]);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            productIdText.setText("PRO0001");
        }
        number++;
        productIdText.setText(String.format("PRO%04d", number));
    }

    @Override
    void loadDetailTable() {
        try {
            List<Product> list = productBo.retrieveAll();
            ObservableList<Product> observableList = FXCollections.observableList(list);
            productDetailTable.setItems(observableList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    void searchDetailById() {
        try {
            List<Product> list = productBo.retrieveById(productIdText.getText());
            Product product = mapper.map(list.get(0), Product.class);
            productNameText.setText(product.getName());

            List<Category> categoryList = categoryBo.retrieveById(product.getCategory().getId());
            Category category = mapper.map(categoryList.get(0), Category.class);
            categoryNameCombo.setValue(category.getName());

            List<Supplier> supplierList = supplierBo.retrieveById(product.getSupplier().getId());
            Supplier supplier = mapper.map(supplierList.get(0), Supplier.class);
            supplierNameCombo.setValue(supplier.getName());

            sizeCombo.setValue(product.getSize());
            descriptionTxt.setText(product.getDescription());
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    void updateById() {
        if (loadConfirmAlert("Confirm update ?")) {
            try {
                List<Category> categoryList = categoryBo.retrieveById(selectedCatIdHiddenTxt.getText());
                Category category = categoryList.get(0);

                List<Supplier> supplierList = supplierBo.retrieveById(selectedSupplierIdHiddenTxt.getText());
                Supplier supplier = supplierList.get(0);

                int updatedRowCount = productBo.update(getProductObj(category, supplier));
                if (updatedRowCount > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Product updated successfully !").show();
                    loadId();
                    loadDetailTable();
                    clearForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Please try again !").show();
                }
            } catch (NullPointerException e) {
                log.info(e.getMessage());
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

    public void logoutBtnOnAction(ActionEvent event) {
        //PENDING
    }

    public void dashboardBtnOnAction() {
        try {
            loadDashboardForm(dashboardBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    private void loadSizeCombo() {
        ObservableList<ProductSizes> observableList = FXCollections.observableArrayList();
        observableList.add(ProductSizes.X_SMALL);
        observableList.add(ProductSizes.SMALL);
        observableList.add(ProductSizes.MEDIUM);
        observableList.add(ProductSizes.LARGE);
        observableList.add(ProductSizes.X_LARGE);
        observableList.add(ProductSizes.NEW_BORN);
        observableList.add(ProductSizes.THREE_MONTHS);
        observableList.add(ProductSizes.SIX_MONTHS);
        observableList.add(ProductSizes.ONE_YEAR);
        observableList.add(ProductSizes.TWO_YEARS);
        sizeCombo.setItems(observableList);
    }

    private void loadSupplierNamesCombo() {
        try {
            List<String> list = categoryBo.retrieveCategoryNames();
            ObservableList<String> observableList = FXCollections.observableList(list);
            categoryNameCombo.setItems(observableList);
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }

    private void loadCategoryNamesCombo() {
        try {
            List<String> list = supplierBo.retrieveSupplierNames();
            ObservableList<String> observableList = FXCollections.observableList(list);
            supplierNameCombo.setItems(observableList);
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }
    private Product getProductObj(Category category, Supplier supplier) {
        String id = productIdText.getText();
        String name = productNameText.getText();
        String description = descriptionTxt.getText();
        ProductSizes size = sizeCombo.getValue();
        Product product = null;

        if (id.isEmpty() || name.isEmpty() || description.isEmpty() || size==null){
            new Alert(Alert.AlertType.WARNING, "Please fill all details !").show();
        }else {
            product = new Product(
                    id,
                    category,
                    supplier,
                    name,
                    description,
                    size,
                    new Date()
            );
        }
        return product;
    }
}
