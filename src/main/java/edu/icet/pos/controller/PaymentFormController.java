package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.bo.custom.PaymentBo;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Payment;
import edu.icet.pos.dto.User;
import edu.icet.pos.dto.holder_dto.CurrentUserHolder;
import edu.icet.pos.dto.holder_dto.OrderDetailsHolder;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.PaymentType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

@Slf4j
public class PaymentFormController implements Initializable {
    @FXML
    private Label currentDateLbl;
    @FXML
    private Label timerLbl;
    @FXML
    private TextField paidAmountTxt;
    @FXML
    private ComboBox<PaymentType> selectPaymentTypeCombo;
    @FXML
    private TextField balanceTxt;
    @FXML
    private Label orderIdPaymentLbl;
    @FXML
    private Label paymentAmountLbl;
    private User currentUser;
    private String orderId;
    private double paymentAmount;
    private static final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDERS);
    private static final PaymentBo paymentBo = BoFactory.getInstance().getBo(BoType.PAYMENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = setUser();
        getCurrentDate();
        getCurrentTime();
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
    void getCurrentTime(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            timerLbl.setText(
                    localTime.getHour() +" : "+localTime.getMinute()+ " : "+ localTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    void getCurrentDate(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDateLbl.setText(dateFormat.format(currentDate));
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
        Payment paymentObj=null;
        PaymentType paymentType = selectPaymentTypeCombo.getValue();
        double paidAmount = Double.parseDouble(paidAmountTxt.getText());
        double balanceAmount = Double.parseDouble(balanceTxt.getText());

        if (getOrderObject()==null || paymentType==null || paidAmount==0.0 || balanceAmount==0.0){
            new Alert(Alert.AlertType.WARNING, "Please fill all details !").show();
        }else {
            paymentObj = new Payment(
                    getOrderObject(),
                    paymentType,
                    paidAmount,
                    balanceAmount,
                    new Date(),
                    currentUser.getId()
            );
        }
        return paymentObj;
    }
    private Orders getOrderObject(){
        return orderBo.retrieveById(orderId).get(0);
    }

    public void paidAmountTxtOnAction() {
        double balanceAmount = paymentAmount - Double.parseDouble(paidAmountTxt.getText());
        balanceTxt.setText(String.valueOf(balanceAmount*(-1)));
    }
    private User setUser(){
        return CurrentUserHolder.getInstance().getUser();
    }
}
