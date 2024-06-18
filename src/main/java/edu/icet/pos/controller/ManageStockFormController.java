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
import javafx.fxml.FXML;
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
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;
    @FXML
    public TextField initialQuantityTxt;
    @FXML
    public TableView<Stock> stockDetailsTable;
    @FXML
    public TableColumn<Stock, String> idCol;
    @FXML
    public TableColumn<Stock, String> initialQtyCol;
    @FXML
    public TableColumn<Stock, String> availableQtyCol;
    @FXML
    public TableColumn<Stock, String> unitPriceCol;
    @FXML
    public TableColumn<Stock, String> isActiveCol;
    @FXML
    public TableColumn<Stock, String> discountCol;
    @FXML
    public Label userNameLbl;
    @FXML
    public ComboBox<String> productIdCombo;
    @FXML
    public TextField stockIdTxt;
    @FXML
    public TextField availableQuantityTxt;
    @FXML
    public TextField unitPriceTxt;
    @FXML
    public TextField stockDiscountPercentageTxt;

    private final StockBo stockBo = BoFactory.getInstance().getBo(BoType.STOCK);
    private final ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);

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
            List<String> list = productBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableList(list);
            productIdCombo.setItems(observableList);
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

    public void deactivateBtnOnAction() {
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

    public void searchBtnOnAction() {
        searchDetailById();
    }

    //OVERRIDDEN METHODS FROM SuperFormController ABSTRACT CLASS
    @Override
    void save() {
        String selectedProductId = productIdCombo.getValue();
        Product product = productBo.retrieveById(selectedProductId).get(0);
        Stock stock = new Stock(
                stockIdTxt.getText(),
                product,
                Integer.parseInt(initialQuantityTxt.getText()),
                Integer.parseInt(availableQuantityTxt.getText()),
                Double.parseDouble(unitPriceTxt.getText()),
                Float.parseFloat(stockDiscountPercentageTxt.getText()),
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
            List<String> list = stockBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableList(list);
            lastId = observableList.get(observableList.size() - 1);
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
            List<Stock> list = stockBo.retrieveAll();
            ObservableList<Stock> observableList = FXCollections.observableList(list);
            stockDetailsTable.setItems(observableList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            isActiveCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
            initialQtyCol.setCellValueFactory(new PropertyValueFactory<>("initialQty"));
            availableQtyCol.setCellValueFactory(new PropertyValueFactory<>("availableQty"));
            unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    void searchDetailById() {
        try {
            List<Stock> list = stockBo.retrieveById(stockIdTxt.getText());
            Stock stock = new ModelMapper().map(list.get(0), Stock.class);

            List<Product> productList = productBo.retrieveById(stock.getProduct().getId());
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
        String selectedProductId = productIdCombo.getValue();
        Product product = productBo.retrieveById(selectedProductId).get(0);

        Stock stock = new Stock(
                stockIdTxt.getText(),
                product,
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
