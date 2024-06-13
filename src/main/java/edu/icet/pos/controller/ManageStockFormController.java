package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.bo.custom.StockBo;
import edu.icet.pos.dto.Product;
import edu.icet.pos.dto.Stock;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class ManageStockFormController extends SuperFormController implements Initializable {
    public Label currentDateLbl;
    public Label timerLbl;
    public TextField initialQuantityTxt;
    public TableView stockDetailsTable;
    public TableColumn idCol;
    public TableColumn initialQtyCol;
    public TableColumn availableQtyCol;
    public TableColumn unitPriceCol;
    public Label userNameLbl;
    public ComboBox productIdCombo;
    public TextField stockIdTxt;
    public TextField availableQuantityTxt;
    public TextField unitPriceTxt;

    private List list;
    private ObservableList observableList;
    private StockBo stockBo = BoFactory.getInstance().getBo(BoType.STOCK);
    private ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //LOGICALLY INHERITED METHODS FROM SuperFormController ABSTRACT CLASS
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
        loadId();
        loadProductCombo();
        loadDetailTable();
    }

    private void loadProductCombo() {
        try {
            list = productBo.retrieveAllId();
            observableList = FXCollections.observableList(list);
            productIdCombo.setItems(observableList);
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

    public void deactivateBtnOnAction(ActionEvent actionEvent) {
        if (loadConfirmAlert("Confirm deactivate stock ?")){
            int noRowsUpdated = stockBo.deactivateById(stockIdTxt.getText());
            if (noRowsUpdated>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Stock deactivated successfully !").show();
                clearForm();
                loadDetailTable();
                loadId();
            }else {
                new Alert(Alert.AlertType.ERROR, "Please try again !").show();
            }
        }
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
        searchDetailById();
    }

    //OVERRIDDEN METHODS FROM SuperFormController ABSTRACT CLASS
    @Override
    void save() {
        String selectedProductId = productIdCombo.getValue().toString();
        ProductEntity productEntity = new ModelMapper().map(productBo.retrieveById(selectedProductId).get(0), ProductEntity.class);
        Stock stock = new Stock(
                stockIdTxt.getText(),
                productEntity,
                Integer.parseInt(initialQuantityTxt.getText()),
                Integer.parseInt(availableQuantityTxt.getText()),
                Double.parseDouble(unitPriceTxt.getText()),
                true,
                new Date()
        );
        boolean isSaved = stockBo.save(stock);
        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION, "New stock saved successfully !").show();
            loadId();
            clearForm();
            loadDetailTable();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please try againe !").show();
        }
    }

    @Override
    void clearForm() {
        productIdCombo.valueProperty().set(null);
        initialQuantityTxt.setText("");
        availableQuantityTxt.setText("");
        unitPriceTxt.setText("");
    }

    @Override
    void loadId() {
        String lastId=null;
        int number=0;
        try {
            list = stockBo.retrieveAllId();
            observableList = FXCollections.observableList(list);
            lastId = (String) observableList.get(observableList.size() - 1);
            number = Integer.parseInt(lastId.split("STK")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            stockIdTxt.setText("STK0001");
        }
        number++;
        stockIdTxt.setText(String.format("STK%04d", number));
    }

    @Override
    void loadDetailTable() {
        try {
            list = stockBo.retrieveAll();
            observableList = FXCollections.observableList(list);
            stockDetailsTable.setItems(observableList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            initialQtyCol.setCellValueFactory(new PropertyValueFactory<>("initialQty"));
            availableQtyCol.setCellValueFactory(new PropertyValueFactory<>("availableQty"));
            unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    void searchDetailById() {
        try {
            list = stockBo.retrieveById(stockIdTxt.getText());
            Stock stock = new ModelMapper().map(list.get(0), Stock.class);

            List productList = productBo.retrieveById(stock.getProductEntity().getId());
            Product product = new ModelMapper().map(productList.get(0), Product.class);
            productIdCombo.setValue(product.getId());

            initialQuantityTxt.setText(String.valueOf(stock.getInitialQty()));
            availableQuantityTxt.setText(String.valueOf(stock.getAvailableQty()));
            unitPriceTxt.setText(String.valueOf(stock.getUnitPrice()));

            loadProductCombo();
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }

    }

    @Override
    void updateById() {
        String selectedProductId = productIdCombo.getValue().toString();
        ProductEntity productEntity = new ModelMapper().map(productBo.retrieveById(selectedProductId).get(0), ProductEntity.class);

        Stock stock = new Stock(
                stockIdTxt.getText(),
                productEntity,
                Integer.parseInt(initialQuantityTxt.getText()),
                Integer.parseInt(availableQuantityTxt.getText()),
                Double.parseDouble(unitPriceTxt.getText())
        );
        if(loadConfirmAlert("Confirm update ?")) {
            try {
                int updatedRowCount = stockBo.update(stock);
                if (updatedRowCount > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Stock updated successfully !").show();
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
}
