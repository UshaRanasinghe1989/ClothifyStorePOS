package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.bo.custom.PaymentBo;
import edu.icet.pos.dto.OrderDetail;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Payment;
import edu.icet.pos.dto.holderDto.OrderDetailsHolder;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.PaymentType;
import edu.icet.pos.util.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Date;
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

    private String orderId;
    private double paymentAmount;

    private static final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDERS);
    private static final PaymentBo paymentBo = BoFactory.getInstance().getBo(BoType.PAYMENT);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPaymentTypeCombo();
        setOrderData();
    }

    public void savePaymentBtnOnAction() {
        boolean isSaved = paymentBo.save(getPaymentObject());

        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION, "Payment saved successfully !").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please try again !").show();
        }
    }
    private void setOrderData(){
        OrderDetailsHolder holder = OrderDetailsHolder.getInstance();
        Orders orders = holder.getOrders();
        orderId = orders.getOrderId();
        paymentAmount = orders.getGrossAmount() - orders.getDiscount();
        orderIdPaymentLbl.setText(orderId);
        paymentAmountLbl.setText(String.valueOf(paymentAmount));
    }
    private void loadPaymentTypeCombo(){
        ObservableList<PaymentType> typeOptions = FXCollections.observableArrayList();
        typeOptions.add(PaymentType.CASH);
        typeOptions.add(PaymentType.CARD);
        selectPaymentTypeCombo.setItems(typeOptions);
    }
    private Payment getPaymentObject(){
        return new Payment(
                getOrderObject(),
                selectPaymentTypeCombo.getValue(),
                Double.parseDouble(paidAmountTxt.getText()),
                Double.parseDouble(balanceTxt.getText()),
                new Date()
        );
    }
    private Orders getOrderObject(){
        return orderBo.retrieveById(orderId).get(0);
    }

    public void paidAmountTxtOnAction() {
        double balanceAmount = paymentAmount - Double.parseDouble(paidAmountTxt.getText());
        balanceTxt.setText(String.valueOf(balanceAmount*(-1)));
    }
}
