package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.*;
import edu.icet.pos.dto.*;
import edu.icet.pos.dto.tableDto.CartTable;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class ManageOrderFormController extends SuperFormController implements Initializable {
    @FXML
    public BorderPane manageOrderForm;
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;
    @FXML
    public TableView<CartTable> cartDetailTable;
    @FXML
    public TableColumn<OrderDetail, String> idCol;
    @FXML
    public TableColumn<OrderDetail, String> nameCol;
    @FXML
    public TableColumn<OrderDetail, String> unitPriceCol;
    @FXML
    public TableColumn<OrderDetail, String> qtyCol;
    @FXML
    public TableColumn<OrderDetail, String> priceCol;
    @FXML
    public Label orderIdLbl;
    @FXML
    public Label productNameLbl;
    @FXML
    public Label productDescriptionLbl;
    @FXML
    public Label productSizeLbl;
    @FXML
    public TextField requiredQtyTxt;
    @FXML
    public TextField customerPhoneTxt;
    @FXML
    public TextField customerNameTxt;
    @FXML
    public Label productIdLbl;
    @FXML
    public TextField customerEmailTxt;
    @FXML
    public ComboBox<String> selectProductNameCombo;
    @FXML
    public Label unitPriceLbl;
    @FXML
    public Label activeStockIdLbl;
    @FXML
    public Label priceLbl;
    @FXML
    public Label stockDiscountLbl;
    @FXML
    public Label totalDiscountLbl;
    @FXML
    public Label grossAmountLbl;
    @FXML
    public Label netAmountLbl;
    @FXML
    public Button updateCustomerBtn;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Label selectedCustomerId;

    double unitPrice;
    Product selectedProduct;
    private final ObservableList<CartTable> cartTableList = FXCollections.observableArrayList();
    private final ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    private final StockBo stockBo = BoFactory.getInstance().getBo(BoType.STOCK);
    private final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDERS);
    private final OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private final CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
        loadId();
        loadProductNamesCombo();
    }

    public void placeOrderBtnOnAction() {
        save();
    }

    public void selectProductComboOnAction() {
        String selectedProductId = String.valueOf(selectProductNameCombo.getValue());
        List<Product> productList = productBo.retrieveById(selectedProductId);
        selectedProduct = productList.get(0);

        String description = selectedProduct.getDescription();

        productIdLbl.setText(selectedProductId);
        productNameLbl.setText(selectedProduct.getName());
        productDescriptionLbl.setText(description.isEmpty() ? "N/A":description);
        productSizeLbl.setText(String.valueOf(selectedProduct.getSize()));

        loadActiveStockDetails(selectedProductId);
    }

    public void addToCartBtnOnAction() {
        loadDetailTable();
    }

    public void quantityTxtOnAction() {
        Double price = unitPrice * Integer.parseInt(requiredQtyTxt.getText());
        priceLbl.setText(String.valueOf(price));
    }

    public void customerPhoneTxtOnAction() {
       searchCustomerPhone();
    }

    public void searchCustomerBtnOnAction() {
        searchCustomerPhone();
    }

    public void updateCustomerBtnOnAction() {
        String name = customerNameTxt.getText();
        String email = customerEmailTxt.getText();

        Customer customer = new Customer(
                name,
                email
        );

        int updatedRowCount = customerBo.update(customer);
        if (updatedRowCount>0){
            new Alert(Alert.AlertType.CONFIRMATION, "Customer updated successfully !").show();
            clearCustomerDetailForm();
        }else {
            new Alert(Alert.AlertType.ERROR, "Try again !").show();
        }
    }

    public void addCustomerBtnOnAction() {
        String contactNo = customerPhoneTxt.getText();
        String name = customerNameTxt.getText();
        String email = customerEmailTxt.getText();

        Customer customer = new Customer(
                contactNo,
                name,
                email
        );

        boolean isSaved = customerBo.save(customer);
        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION, "Customer saved successfully !").show();
            clearCustomerDetailForm();
        }else {
            new Alert(Alert.AlertType.ERROR, "Try again !").show();
        }
    }

    public void clearOrderBtnOnAction(ActionEvent actionEvent) {
    }
    @Override
    void save() {
        String orderId = orderIdLbl.getText();
        //ORDER
        double grossAmount = 0.0;
        double discount = 0.0;
        double netAmount = 0.0;

        for (CartTable table : cartTableList) {
            grossAmount += table.getPrice();
            discount += table.getDiscount();
            netAmount = grossAmount - discount;
        }

        Orders newOrder = new Orders(
                orderId,
                cartTableList.size(),
                grossAmount,
                discount,
                netAmount,
                new Date()
        );
        boolean isOrderSaved = orderBo.save(newOrder);
        if (isOrderSaved){
            boolean isDetailListSaved = orderDetailBo.save(generateOrderDetailList(newOrder));
            if (isDetailListSaved){
                int updatedRowCount = stockBo.updateStockQty(cartTableList);
                if(updatedRowCount>0){
                    new Alert(Alert.AlertType.CONFIRMATION, "Order saved successfully !").show();
                }
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Order failed !").show();
        }
    }

    @Override
    void clearForm() {
        selectProductNameCombo.valueProperty().set("");
        productIdLbl.setText("");
        productNameLbl.setText("");
        productDescriptionLbl.setText("");
        productSizeLbl.setText("");
        unitPriceLbl.setText("");
        activeStockIdLbl.setText("");
        requiredQtyTxt.setText("");
        stockDiscountLbl.setText("");
        priceLbl.setText("");
    }

    @Override
    void loadId() {
        String lastId=null;
        int number=0;
        try {
            List<String> list = orderBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableList(list);
            lastId = observableList.get(observableList.size() - 1);
            number = Integer.parseInt(lastId.split("ORD")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            orderIdLbl.setText("ORD0001");
        }
        number++;
        orderIdLbl.setText(String.format("ORD%04d", number));
    }

    @Override
    void loadDetailTable() {
        ObservableList<CartTable> observableList = getCartTables();

        cartDetailTable.setItems(observableList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        calculateOrderTotal();
        clearForm();
    }

    @Override
    void searchDetailById() {
        String contactNo = customerPhoneTxt.getText();

        try {
            Customer customer = customerBo.retrieveByContactNo(contactNo);
            selectedCustomerId.setText(String.valueOf(customer.getId()));
            customerNameTxt.setText(customer.getName());
            customerEmailTxt.setText(customer.getEmail());
            updateCustomerBtn.setVisible(true);
            addCustomerBtn.setVisible(false);
        }catch (IndexOutOfBoundsException | NullPointerException e){
            log.info(e.getMessage());
            new Alert(Alert.AlertType.CONFIRMATION, "Customer not available !").show();
            updateCustomerBtn.setVisible(false);
            addCustomerBtn.setVisible(true);
        }
    }

    @Override
    void updateById() {

    }

    private ObservableList<CartTable> getCartTables() {
        String productId =  productIdLbl.getText();
        CartTable cartTable = new CartTable(
                productId,
                productNameLbl.getText(),
                Double.parseDouble(unitPriceLbl.getText()),
                Integer.parseInt(requiredQtyTxt.getText()),
                Double.parseDouble(priceLbl.getText()),
                Double.parseDouble(stockDiscountLbl.getText()),
                activeStockIdLbl.getText()
        );

        cartTableList.add(cartTable);

        return cartTableList;
    }

    private void calculateOrderTotal(){
        double grossTotal;
        double discountTotal;
        double netTotal;

        grossTotal = cartTableList.stream().mapToDouble(CartTable::getPrice).sum();
        discountTotal = cartTableList.stream().mapToDouble(CartTable::getDiscount).sum();
        netTotal = grossTotal - discountTotal;

        grossAmountLbl.setText(String.valueOf(grossTotal));
        totalDiscountLbl.setText(String.valueOf(discountTotal));
        netAmountLbl.setText(String.valueOf(netTotal));
    }

    private List<OrderDetail> generateOrderDetailList(Orders newOrder){
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail newOrderDetail=null;

        for (CartTable cartTable : cartTableList) {
            newOrderDetail = new OrderDetail(
                    newOrder,
                    productBo.retrieveById(cartTable.getProductId()).get(0),
                    cartTable.getStockId(),
                    cartTable.getProductQuantity(),
                    cartTable.getPrice(),
                    cartTable.getDiscount()
            );
            orderDetailList.add(newOrderDetail);
        }
        return orderDetailList;
    }
    private void searchCustomerPhone(){
        searchDetailById();
    }
    private void clearCustomerDetailForm(){
        customerNameTxt.setText("");
        customerEmailTxt.setText("");
    }
    private void loadActiveStockDetails(String productId) {
        Product product = productBo.retrieveById(productId).get(0);
        List<Stock> stockList = stockBo.retrieveActiveStockByProduct(product);
        Stock stock = stockList.get(0);

        activeStockIdLbl.setText(stock.getId());
        unitPrice = stock.getUnitPrice();
        unitPriceLbl.setText(String.valueOf(unitPrice));
        stockDiscountLbl.setText(String.valueOf(stock.getDiscount()));
    }
    private void loadProductNamesCombo(){
        try {
            List<String> productIdList = productBo.retrieveAllId();
            ObservableList<String> productNamesObservableList = FXCollections.observableList(productIdList);
            selectProductNameCombo.setItems(productNamesObservableList);

        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }
}
