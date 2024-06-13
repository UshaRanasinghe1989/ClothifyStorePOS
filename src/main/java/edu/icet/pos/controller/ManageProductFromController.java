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
    public Label currentDateLbl;
    public Label timerLbl;
    public TextField productNameText;
    public TextField imageOnePathTxt;
    public TextField imageTwoPathTxt;
    public TextField imageThreePathTxt;
    public TableView productDetailTable;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn sizeCol;
    public TableColumn descriptionCol;
    public TextField productIdText;
    public ComboBox categoryNameCombo;
    public TextField selectedCatIdHiddenTxt;
    public ComboBox supplierNameCombo;
    public TextField selectedSupplierIdHiddenTxt;
    public ComboBox sizeCombo;
    public TextField descriptionTxt;
    public TextField imageFourPathTxt;
    public TextField imageFivePathTxt;
    public ImageView imageOne;

    ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    CategoryBo categoryBo = BoFactory.getInstance().getBo(BoType.CATEGORY);
    SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    List list;
    ObservableList observableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentTime(timerLbl);
        getCurrentDate(currentDateLbl);
        loadId();
        loadCategoryNamesCombo();
        loadSupplierNamesCombo();
        loadSizeCombo();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        loadDetailTable();
    }

    private void loadSizeCombo() {
        observableList = FXCollections.observableArrayList();
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
            list = categoryBo.retrieveCategoryNames();
            observableList = FXCollections.observableList(list);
            categoryNameCombo.setItems(observableList);
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    private void loadCategoryNamesCombo() {
        try {
            list = supplierBo.retrieveSupplierNames();
            observableList = FXCollections.observableList(list);
            supplierNameCombo.setItems(observableList);
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        save();
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        updateById();
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
        searchDetailById();
    }

    public void categoryNameComboOnAction(ActionEvent actionEvent) {
        try {
            list = categoryBo.retrieveCatIdByName(categoryNameCombo.getValue().toString());
            selectedCatIdHiddenTxt.setText(list.get(0).toString());
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    public void supplierNameComboOnAction(ActionEvent actionEvent) {
        try {
            list = supplierBo.retrieveSupplierIdByName(supplierNameCombo.getValue().toString());
            selectedSupplierIdHiddenTxt.setText(list.get(0).toString());
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
        List categoryList = categoryBo.retrieveById(selectedCatIdHiddenTxt.getText());
        CategoryEntity categoryEntity = new ModelMapper().map(categoryList.get(0), CategoryEntity.class);
        List supplierList = supplierBo.retrieveById(selectedSupplierIdHiddenTxt.getText());
        SupplierEntity supplierEntity = new ModelMapper().map(supplierList.get(0), SupplierEntity.class);

        Product product = new Product(
                productIdText.getText(),
                categoryEntity,
                supplierEntity,
                productNameText.getText(),
                descriptionTxt.getText(),
                sizeCombo.getValue().toString(),
                new Date()
        );
        boolean isSaved = productBo.save(product);
        if(isSaved){
            new Alert(Alert.AlertType.CONFIRMATION, "Product saved successfully !").show();
            loadId();
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
            list = productBo.retrieveAllId();
            observableList = FXCollections.observableList(list);
            lastId = (String) observableList.get(observableList.size() - 1);
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
            list = productBo.retrieveAll();
            observableList = FXCollections.observableList(list);
            productDetailTable.setItems(observableList);
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    void searchDetailById() {
        try {
            list = productBo.retrieveById(productIdText.getText());
            Product product = new ModelMapper().map(list.get(0), Product.class);
            productNameText.setText(product.getName());

            List categoryList = categoryBo.retrieveById(product.getCategoryEntity().getId());
            Category category = new ModelMapper().map(categoryList.get(0), Category.class);
            categoryNameCombo.setValue(category.getName());

            List supplierList = supplierBo.retrieveById(product.getSupplierEntity().getId());
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
                List categoryList = categoryBo.retrieveById(selectedCatIdHiddenTxt.getText());
                CategoryEntity categoryEntity = new ModelMapper().map(categoryList.get(0), CategoryEntity.class);
                List supplierList = supplierBo.retrieveById(selectedSupplierIdHiddenTxt.getText());
                SupplierEntity supplierEntity = new ModelMapper().map(supplierList.get(0), SupplierEntity.class);

                Product product = new Product(
                        productIdText.getText(),
                        categoryEntity,
                        supplierEntity,
                        productNameText.getText(),
                        descriptionTxt.getText(),
                        sizeCombo.getValue().toString(),
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
