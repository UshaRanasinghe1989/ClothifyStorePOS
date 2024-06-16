package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.dto.Category;
import edu.icet.pos.dto.Product;
import edu.icet.pos.dto.Supplier;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.util.BoType;
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

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class ManageProductFromController extends SuperFormController implements Initializable {
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;
    @FXML
    public TextField productNameText;
    @FXML
    public TextField imageOnePathTxt;
    @FXML
    public TextField imageTwoPathTxt;
    @FXML
    public TextField imageThreePathTxt;
    @FXML
    public TableView<Product> productDetailTable;
    @FXML
    public TableColumn<Product, String> idCol;
    @FXML
    public TableColumn<Product, String> nameCol;
    @FXML
    public TableColumn<Product, String> sizeCol;
    @FXML
    public TableColumn<Product, String> descriptionCol;
    @FXML
    public TextField productIdText;
    @FXML
    public ComboBox<String> categoryNameCombo;
    @FXML
    public TextField selectedCatIdHiddenTxt;
    @FXML
    public ComboBox<String> supplierNameCombo;
    @FXML
    public TextField selectedSupplierIdHiddenTxt;
    @FXML
    public ComboBox<ProductSizes> sizeCombo;
    @FXML
    public TextField descriptionTxt;
    @FXML
    public TextField imageFourPathTxt;
    @FXML
    public TextField imageFivePathTxt;
    @FXML
    public ImageView imageOne;

    ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    CategoryBo categoryBo = BoFactory.getInstance().getBo(BoType.CATEGORY);
    SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentTime(timerLbl);
        getCurrentDate(currentDateLbl);
        loadId();
        loadCategoryNamesCombo();
        loadSupplierNamesCombo();
        loadSizeCombo();
        loadDetailTable();
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
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    private void loadCategoryNamesCombo() {
        try {
            List<String> list = supplierBo.retrieveSupplierNames();
            ObservableList<String> observableList = FXCollections.observableList(list);
            supplierNameCombo.setItems(observableList);
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
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
            if (categoryName!=null){
                List<String> list = categoryBo.retrieveCatIdByName(categoryName);
                selectedCatIdHiddenTxt.setText(list.get(0));
            }
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    public void supplierNameComboOnAction() {
        try {
            String supplierName = supplierNameCombo.getValue();
            if (supplierName!=null) {
                List<String> list = supplierBo.retrieveSupplierIdByName(supplierName);
                selectedSupplierIdHiddenTxt.setText(list.get(0));
            }
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    public void browseImgOneBtnOnAction(ActionEvent actionEvent) {
    }

    public void browseImgTwoBtnOnAction(ActionEvent actionEvent) {
    }

    public void browseImgThreeBtnOnAction(ActionEvent actionEvent) {
    }

    public void browseImgFourBtnOnAction(ActionEvent actionEvent) {
    }

    public void browseImgFiveBtnOnAction(ActionEvent actionEvent) {
    }

    @Override
    void save() {
        List<Category> categoryList = categoryBo.retrieveById(selectedCatIdHiddenTxt.getText());
        Category category = categoryList.get(0);

        List<Supplier> supplierList = supplierBo.retrieveById(selectedSupplierIdHiddenTxt.getText());
        Supplier supplier = supplierList.get(0);

        Product product = new Product(
                productIdText.getText(),
                category,
                supplier,
                productNameText.getText(),
                descriptionTxt.getText(),
                sizeCombo.getValue(),
                new Date()
        );

        boolean isSaved = productBo.save(product);
        if(isSaved){
            new Alert(Alert.AlertType.CONFIRMATION, "Product saved successfully !").show();
            loadId();
            loadDetailTable();
            clearForm();
        }else {
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
        String lastId=null;
        int number=0;
        try {
            List<String> list = productBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableList(list);
            lastId = observableList.get(observableList.size() - 1);
            number = Integer.parseInt(lastId.split("PRO")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
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
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    void searchDetailById() {
        try {
            List<Product> list = productBo.retrieveById(productIdText.getText());
            Product product = new ModelMapper().map(list.get(0), Product.class);
            productNameText.setText(product.getName());

            List<Category> categoryList = categoryBo.retrieveById(product.getCategory().getId());
            Category category = new ModelMapper().map(categoryList.get(0), Category.class);
            categoryNameCombo.setValue(category.getName());

            List<Supplier> supplierList = supplierBo.retrieveById(product.getSupplier().getId());
            Supplier supplier = new ModelMapper().map(supplierList.get(0), Supplier.class);
            supplierNameCombo.setValue(supplier.getName());

            sizeCombo.setValue(product.getSize());
            descriptionTxt.setText(product.getDescription());
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    void updateById() {
        if(loadConfirmAlert("Confirm update ?")) {
            try {
                List<Category> categoryList = categoryBo.retrieveById(selectedCatIdHiddenTxt.getText());
                Category category = categoryList.get(0);

                List<Supplier> supplierList = supplierBo.retrieveById(selectedSupplierIdHiddenTxt.getText());
                Supplier supplier = supplierList.get(0);

                Product product = new Product(
                        productIdText.getText(),
                        category,
                        supplier,
                        productNameText.getText(),
                        descriptionTxt.getText(),
                        sizeCombo.getValue(),
                        new Date()
                );
                int updatedRowCount = productBo.update(product);
                if (updatedRowCount>0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Product updated successfully !").show();
                    loadId();
                    loadDetailTable();
                    clearForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Please try again !").show();
                }
            }catch (NullPointerException e){
                log.info(e.getMessage());
            }
        }
    }
}
