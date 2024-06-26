package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.*;
import edu.icet.pos.dto.*;
import edu.icet.pos.dto.table_dto.CartTable;
import edu.icet.pos.dto.holder_dto.OrderDetailsHolder;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    @FXML
    public Button checkOutBtn;
    @FXML
    public Button paymentBtn;
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
    @FXML
    public ComboBox<String> sellerCombo;
    private User currentUser;
    private double unitPrice;
    private Product selectedProduct;
    private String sellerId;
    private final ObservableList<CartTable> cartTableList = FXCollections.observableArrayList();
    private final ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    private final StockBo stockBo = BoFactory.getInstance().getBo(BoType.STOCK);
    private final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDERS);
    private final OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private final CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private final UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = setDisplayName();
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
        loadId();
        loadSelectSellerCombo();
        loadProductNamesCombo();
        loadManageReturnCombo(manageReturnCombo);
        loadManageStockCombo(manageStockCombo);
        selectedCustomerId.setVisible(false);
        paymentBtn.setVisible(false);
    }

    public void placeOrderBtnOnAction(){
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

        //CLEAR PRICE DETAILS
        requiredQtyTxt.setText("");
        stockDiscountLbl.setText("0");
        priceLbl.setText("0.0");
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
    public void sellerComboOnAction() {
        sellerId = sellerCombo.getValue();
    }
    public void paymentBtnOnAction() {
        loadPaymentForm();
    }

    public void clearOrderBtnOnAction() {
        clearOrder();
    }

    @Override
    void save() {
        Orders newOrder = getOrderObject();

        boolean isOrderSaved = orderBo.save(newOrder);
        if (isOrderSaved){
            boolean isDetailListSaved = orderDetailBo.save(generateOrderDetailList(newOrder));
            if (isDetailListSaved){
                int updatedRowCount = stockBo.updateStockQty(cartTableList);
                if(updatedRowCount>0){
                    new Alert(Alert.AlertType.CONFIRMATION, "Order saved successfully !").show();
                    paymentBtn.setVisible(true);
                    checkOutBtn.setVisible(false);
                }
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Order failed !").show();
        }
    }

    @Override
    void clearForm() {
        selectProductNameCombo.valueProperty().set(null);
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
        String lastId;
        int number=0;
        try {
            List<String> list = orderBo.retrieveAllId();
            List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
            lastId = sortedList.get(sortedList.size() - 1);
            number = Integer.parseInt(lastId.split("ORD")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            orderIdLbl.setText("ORD0001");
        }
        number++;
        orderIdLbl.setText(String.format("ORD%04d", number));
    }

    @Override
    void loadDetailTable() {
        if (selectProductNameCombo.valueProperty() == null || requiredQtyTxt.getText().isEmpty() || Double.parseDouble(priceLbl.getText())==0.0) {
            new Alert(Alert.AlertType.WARNING, "Please select a product and enter a quantity !").show();
        } else{
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
    public void logoutBtnOnAction(ActionEvent event) {
    }

    public void dashboardBtnOnAction() {
        try {
            loadDashboardForm(dashboardBtn);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
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
        OrderDetail newOrderDetail;

        for (CartTable cartTable : cartTableList) {
            newOrderDetail = new OrderDetail(
                    newOrder,
                    productBo.retrieveById(cartTable.getProductId()).get(0),
                    cartTable.getStockId(),
                    cartTable.getProductQuantity(),
                    cartTable.getPrice(),
                    cartTable.getDiscount(),
                    false
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
    private void loadPaymentForm(){
        String orderId = orderIdLbl.getText();
        Orders orders = orderBo.retrieveById(orderId).get(0);

        try {
            OrderDetailsHolder holder = OrderDetailsHolder.getInstance();
            holder.setOrders(orders);
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/make-payment-form.fxml"))));
            stage.show();
        }catch (IOException e){
            log.info(e.getMessage());
        }finally {
            loadId();
            clearOrder();
        }
    }
    private Customer getCustomerObject(){
        Customer customerObj;
        String contactNo = customerPhoneTxt.getText();
        String name = customerNameTxt.getText();
        String email = customerEmailTxt.getText();

        if (contactNo.isEmpty() || name.isEmpty() || email.isEmpty()){
            customerObj = null;
        }else {
            customerObj = new Customer(
                    customerPhoneTxt.getText(),
                    customerNameTxt.getText(),
                    customerEmailTxt.getText()
            );
        }
        return customerObj;
    }
    private Orders getOrderObject() {
        Orders orderObj = null;
        String orderId = orderIdLbl.getText();
        int numberOfProducts = cartTableList.size();
        //ORDER
        double grossAmount = 0.0;
        double discount = 0.0;
        double netAmount = 0.0;

        for (CartTable table : cartTableList) {
            grossAmount += table.getPrice();
            discount += table.getDiscount();
            netAmount = grossAmount - discount;
        }
        if (sellerId==null) {
            new Alert(Alert.AlertType.WARNING, "Please select seller id !").show();
        } else{
            orderObj = new Orders(
                    orderId,
                    getCustomerObject(),
                    numberOfProducts,
                    grossAmount,
                    discount,
                    netAmount,
                    sellerId,
                    currentUser.getId(),
                    new Date()
            );
        }
        return  orderObj;
    }
    private void loadSelectSellerCombo() {
        List<String> userIdList = userBo.retrieveAllId();
        ObservableList<String> observableList = FXCollections.observableList(userIdList);
        sellerCombo.setItems(observableList);
    }
    private void clearOrder() {
        cartTableList.clear();
        grossAmountLbl.setText("0.0");
        netAmountLbl.setText("0.0");
        totalDiscountLbl.setText("0.0");
        sellerCombo.valueProperty().setValue(null);
    }
}
