package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.bo.custom.ProductImageBo;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.dto.*;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
public class ManageProductFormController extends SuperFormController implements Initializable {
    @FXML
    public Button updateBtn;
    @FXML
    public Button productCategoryBtn;
    @FXML
    private TextField productNameText;
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
    //PRODUCT IMAGES
    @FXML
    private ImageView imageOne;
    @FXML
    private Label imageOneLbl;
    @FXML
    public ImageView imageTwo;
    @FXML
    public Label imageTwoLbl;
    @FXML
    public ImageView imageThree;
    @FXML
    public Label imageThreeLbl;
    @FXML
    public ImageView imageFour;
    @FXML
    public Label imageFourLbl;
    @FXML
    public ImageView imageFive;
    @FXML
    public Label imageFiveLbl;
    @FXML
    private TextField imageOnePathTxt;
    @FXML
    private TextField imageTwoPathTxt;
    @FXML
    private TextField imageThreePathTxt;
    @FXML
    private TextField imageFourPathTxt;
    @FXML
    private TextField imageFivePathTxt;

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
    private String selectedProductId;
    private User currentUser;
    private Product savedProductObj;
    private ProductImage productImageOneObj;
    private ProductImage productImageTwoObj;
    private ProductImage productImageThreeObj;
    private ProductImage productImageFourObj;
    private ProductImage productImageFiveObj;
    private FileInputStream fileInputStreamImageOne;
    private FileInputStream fileInputStreamImageTwo;
    private FileInputStream fileInputStreamImageThree;
    private FileInputStream fileInputStreamImageFour;
    private FileInputStream fileInputStreamImageFive;


    private final ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    private final CategoryBo categoryBo = BoFactory.getInstance().getBo(BoType.CATEGORY);
    private final SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    private final ProductImageBo productImageBo = BoFactory.getInstance().getBo(BoType.PRODUCT_IMAGE);
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private static final String JPG_1 = "JPG files (*.jpg)";
    private static final String JPG_2 = "*.JPG";
    private static final String JPG_3 = "*.JPEG";
    private static final String JPG_4 = "*.jpeg";
    private static final String PNG_1 = "PNG files (*.png)";
    private static final String PNG_2 = "*.PNG";
    private static final String ALERT_TRY_AGAIN = "Please try again !";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserMenu();
        currentUser = setDisplayName();
        getCurrentTime(timerLbl);
        getCurrentDate(currentDateLbl);
        loadId();
        loadCategoryNamesCombo();
        loadSupplierNamesCombo();
        loadManageReturnCombo(manageReturnCombo);
        loadManageStockCombo(manageStockCombo);
        loadSizeCombo();
        loadDetailTable();
        //HIDE IMAGE LABELS
        imageOneLbl.setVisible(false);
        imageTwoLbl.setVisible(false);
        imageThreeLbl.setVisible(false);
        imageFourLbl.setVisible(false);
        imageFiveLbl.setVisible(false);
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

    public void browseImgOneBtnOnAction() throws FileNotFoundException {
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(JPG_1, JPG_2, JPG_3, JPG_4);
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(PNG_1, PNG_2);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file!=null){
            imageOneLbl.setVisible(true);
            Image image = new Image(file.toURI().toString());
            imageOne.setImage(image);

            String imagePath = file.getAbsolutePath();
            imageOnePathTxt.setText(imagePath);

            fileInputStreamImageOne = new FileInputStream(file);
        }
    }
    public void browseImgTwoBtnOnAction() throws FileNotFoundException {
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(JPG_1, JPG_2, JPG_3, JPG_4);
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(PNG_1, PNG_2);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file!=null){
            imageTwoLbl.setVisible(true);
            Image image = new Image(file.toURI().toString());
            imageTwo.setImage(image);

            String imagePath = file.getAbsolutePath();
            imageTwoPathTxt.setText(imagePath);

            fileInputStreamImageTwo = new FileInputStream(file);
        }
    }

    public void browseImgThreeBtnOnAction() throws FileNotFoundException {
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(JPG_1, JPG_2, JPG_3, JPG_4);
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(PNG_1, PNG_2);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file!=null){
            imageThreeLbl.setVisible(true);
            Image image = new Image(file.toURI().toString());
            imageThree.setImage(image);

            String imagePath = file.getAbsolutePath();
            imageThreePathTxt.setText(imagePath);

            fileInputStreamImageThree = new FileInputStream(file);
        }
    }

    public void browseImgFourBtnOnAction() throws FileNotFoundException {
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(JPG_1, JPG_2, JPG_3, JPG_4);
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(PNG_1, PNG_2);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file!=null){
            imageFourLbl.setVisible(true);
            Image image = new Image(file.toURI().toString());
            imageFour.setImage(image);

            String imagePath = file.getAbsolutePath();
            imageFourPathTxt.setText(imagePath);

            fileInputStreamImageFour = new FileInputStream(file);
        }
    }

    public void browseImgFiveBtnOnAction() throws FileNotFoundException {
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(JPG_1, JPG_2, JPG_3, JPG_4);
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(PNG_1, PNG_2);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file!=null){
            imageFiveLbl.setVisible(true);
            Image image = new Image(file.toURI().toString());
            imageFive.setImage(image);

            String imagePath = file.getAbsolutePath();
            imageFivePathTxt.setText(imagePath);

            fileInputStreamImageFive = new FileInputStream(file);
        }
    }

    @Override
    void save() {
        List<Category> categoryList = categoryBo.retrieveById(selectedCatIdHiddenTxt.getText());
        Category category = categoryList.get(0);

        List<Supplier> supplierList = supplierBo.retrieveById(selectedSupplierIdHiddenTxt.getText());
        Supplier supplier = supplierList.get(0);

        boolean isSaved = productBo.save(getProductObj(category, supplier));
        if (isSaved) {
            savedProductObj = productBo.retrieveById(selectedProductId).get(0);
            boolean isImageSaved = saveProductImages();
            if (isImageSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Product saved successfully !").show();

                loadId();
                loadDetailTable();
                clearForm();
            }else {
                new Alert(Alert.AlertType.ERROR, ALERT_TRY_AGAIN).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, ALERT_TRY_AGAIN).show();
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

        //IMAGES FORM
        imageOneLbl.setVisible(false);
        imageTwoLbl.setVisible(false);
        imageThreeLbl.setVisible(false);
        imageFourLbl.setVisible(false);
        imageFiveLbl.setVisible(false);
        imageOnePathTxt.setText("");
        imageTwoPathTxt.setText("");
        imageThreePathTxt.setText("");
        imageFourPathTxt.setText("");
        imageFivePathTxt.setText("");
        imageOne.setImage(null);
        imageTwo.setImage(null);
        imageThree.setImage(null);
        imageFour.setImage(null);
        imageFive.setImage(null);
    }

    @Override
    void loadId() {
        String lastId = null;
        int number = 0;
        try {
            List<String> list = productBo.retrieveAllId();
            List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
            lastId = sortedList.get(sortedList.size() - 1);
            number = Integer.parseInt(lastId.split("PRO")[1]);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            productIdText.setText("PRO0001");
        }
        number++;
        String productId = String.format("PRO%04d", number);
        productIdText.setText(productId);
        selectedProductId = productId;
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
        String productId = productIdText.getText();
        if (productId.isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please enter product id !").show();
        }else {
            selectedProductId = productId;
            try {
                List<Product> list = productBo.retrieveById(selectedProductId);
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
                    new Alert(Alert.AlertType.ERROR, ALERT_TRY_AGAIN).show();
                }
            } catch (NullPointerException e) {
                log.info(e.getMessage());
            }
        }
    }

    @Override
    void loadUserMenu() {
        if (currentUser.getType()== UserType.USER){
            dashboardBtn.setVisible(false);
            employeeBtn.setVisible(false);
            usersBtn.setVisible(false);
            updateBtn.setVisible(false);
        }
    }

    //MENU - LEFT BORDER
    public User setDisplayName() {
        return setUser(userLbl);
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
    public void productCategoryBtnOnAction() {
        try {
            loadCategoryForm(productCategoryBtn);
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

    private void getProductImageOneObj() {
        try {
            if (savedProductObj!=null && !imageOnePathTxt.getText().isEmpty()) {
                productImageOneObj = new ProductImage(
                        savedProductObj,
                        imageOnePathTxt.getText(),
                        fileInputStreamImageOne.readAllBytes()
                );
            }
        } catch (IOException e) {
            log.info(e.getMessage(), productImageOneObj);
        }
    }
    private void getProductImageTwoObj() {
        try {
            if (savedProductObj!=null && !imageTwoPathTxt.getText().isEmpty()) {
                productImageTwoObj = new ProductImage(
                        savedProductObj,
                        imageTwoPathTxt.getText(),
                        fileInputStreamImageTwo.readAllBytes()
                );
            }
        } catch (IOException e) {
            log.info(e.getMessage(), productImageTwoObj);
        }
    }
    private void getProductImageThreeObj() {
        try {
            if (savedProductObj!=null && !imageThreePathTxt.getText().isEmpty()) {
                productImageThreeObj = new ProductImage(
                        savedProductObj,
                        imageThreePathTxt.getText(),
                        fileInputStreamImageThree.readAllBytes()
                );
            }
        } catch (IOException e) {
            log.info(e.getMessage(), productImageThreeObj);
        }
    }
    private void getProductImageFourObj() {
        try {
            if (savedProductObj!=null && !imageFourPathTxt.getText().isEmpty()) {
                productImageFourObj = new ProductImage(
                        savedProductObj,
                        imageFourPathTxt.getText(),
                        fileInputStreamImageFour.readAllBytes()
                );
            }
        } catch (IOException e) {
            log.info(e.getMessage(), productImageFourObj);
        }
    }
    private void getProductImageFiveObj() {
        try {
            if (savedProductObj!=null && !imageFivePathTxt.getText().isEmpty()) {
                productImageFiveObj = new ProductImage(
                        savedProductObj,
                        imageFivePathTxt.getText(),
                        fileInputStreamImageFive.readAllBytes()
                );
            }
        } catch (IOException e) {
            log.info(e.getMessage(), productImageFiveObj);
        }
    }
    private boolean saveProductImages() {
        boolean isSaved = false;
        //SAVE IMAGE ONE
        if (imageOnePathTxt.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please select at least one image of the product !").show();
        }else {
            getProductImageOneObj();
            isSaved = productImageBo.save(productImageOneObj);
        }
        //SAVE IMAGE TWO
        if (productImageTwoObj!=null){
            getProductImageTwoObj();
            isSaved = productImageBo.save(productImageTwoObj);
        }
        //SAVE IMAGE THREE
        if (productImageThreeObj!=null){
            getProductImageThreeObj();
            isSaved = productImageBo.save(productImageThreeObj);
        }
        //SAVE IMAGE FOUR
        if (productImageFourObj!=null){
            getProductImageFourObj();
            isSaved = productImageBo.save(productImageFourObj);
        }
        //SAVE IMAGE FIVE
        if (productImageFiveObj!=null){
            getProductImageFiveObj();
            isSaved = productImageBo.save(productImageFiveObj);
        }

        return isSaved;
    }
}
