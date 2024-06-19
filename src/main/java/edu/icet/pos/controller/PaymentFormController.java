package edu.icet.pos.controller;

import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.holderDto.OrderDetailsHolder;
import edu.icet.pos.util.PaymentType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class PaymentFormController implements Initializable {
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;
    @FXML
    public TextField paidAmountTxt;
    @FXML
    public TableView<OrderDetail> orderDetailsTable;
    @FXML
    public TableColumn<OrderDetail, String> productNameCol;
    @FXML
    public TableColumn<OrderDetail, String> quantityCol;
    @FXML
    public TableColumn<OrderDetail, String> priceCol;
    @FXML
    public TableColumn<OrderDetail, String> discountCol;
    @FXML
    public TableColumn<OrderDetail, String> postDiscountPriceCol;
    @FXML
    public Label userNameLbl;
    @FXML
    public ComboBox<PaymentType> selectPaymentTypeCombo;
    @FXML
    public TextField balanceTxt;
    @FXML
    public Label orderIdPaymentLbl;
    @FXML
    public Label paymentAmountLbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrderData();
    }

    public void savePaymentBtnOnAction(ActionEvent actionEvent) {
    }
    private void setOrderData(){
        OrderDetailsHolder holder = OrderDetailsHolder.getInstance();
        Orders orders = holder.getOrders();

        orderIdPaymentLbl.setText(orders.getOrderId());
        paymentAmountLbl.setText(String.valueOf(orders.getGrossAmount() - orders.getDiscount()));
    }
}
