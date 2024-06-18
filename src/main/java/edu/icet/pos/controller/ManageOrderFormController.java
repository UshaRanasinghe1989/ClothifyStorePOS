package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.bo.custom.OrderDetailBo;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.bo.custom.StockBo;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Product;
import edu.icet.pos.dto.Stock;
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
    public Label totalAmountLbl;
    @FXML
    public TextField customerPhoneTxt;
    @FXML
    public Label customerNameLbl;
    @FXML
    public Label productIdLbl;
    @FXML
    public TextField customerEmailTxt;
    @FXML
    public ComboBox<String> selectProductNameCombo;
    public Label unitPriceLbl;
    public Label activeStockIdLbl;
    public Label priceLbl;
    public Label stockDiscountLbl;
    public Label totalDiscountLbl;
    public Label grossAmountLbl;
    public Label netAmountLbl;

    double unitPrice;
    Product selectedProduct;
    private ObservableList<CartTable> cartTableList = FXCollections.observableArrayList();

    private final ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    private final StockBo stockBo = BoFactory.getInstance().getBo(BoType.STOCK);
    private final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDERS);
    private final OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);

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
        double grossTotal = 0.0;
        double discountTotal = 0.0;
        double netTotal = 0.0;

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

        for (int i =0; i<cartTableList.size(); i++){
            newOrderDetail = new OrderDetail(
                    newOrder,
                    productBo.retrieveById(cartTableList.get(i).getProductId()).get(0),
                    cartTableList.get(i).getStockId(),
                    cartTableList.get(i).getProductQuantity(),
                    cartTableList.get(i).getPrice(),
                    cartTableList.get(i).getDiscount()
            );
            orderDetailList.add(newOrderDetail);
        }
        return orderDetailList;
    }

    public void quantityTxtOnAction() {
        Double price = unitPrice * Integer.parseInt(requiredQtyTxt.getText());
        priceLbl.setText(String.valueOf(price));
    }

    public void customerPhoneTxtOnAction(ActionEvent actionEvent) {
    }

    public void searchCustomerBtnOnAction(ActionEvent actionEvent) {
    }

    public void clearOrderBtnOnAction(ActionEvent actionEvent) {
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

    private void loadActiveStockDetails(String productId) {
        Product product = productBo.retrieveById(productId).get(0);
        List<Stock> stockList = stockBo.retrieveActiveStockByProduct(product);
        Stock stock = stockList.get(0);

        activeStockIdLbl.setText(stock.getId());
        unitPrice = stock.getUnitPrice();
        unitPriceLbl.setText(String.valueOf(unitPrice));
        stockDiscountLbl.setText(String.valueOf(stock.getDiscount()));
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

    }

    @Override
    void searchDetailById() {

    }

    @Override
    void updateById() {

    }
}
