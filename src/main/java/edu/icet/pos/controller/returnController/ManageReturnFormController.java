package edu.icet.pos.controller.returnController;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.bo.custom.OrderDetailBo;
import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.bo.custom.ReturnBo;
import edu.icet.pos.dto.*;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.ReturnReason;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@Slf4j
public class ManageReturnFormController implements ReturnInterface, Initializable {
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;
    @FXML
    public TableView<OrderDetail> orderDetailTable;
    @FXML
    public TableColumn<OrderDetail, Integer> idCol;
    @FXML
    public TableColumn<OrderDetail, Integer> qtyCol;
    @FXML
    public TableColumn<OrderDetail, Double> discountCol;
    @FXML
    public TableColumn<OrderDetail, Double> priceCol;
    @FXML
    public Label orderIdLbl;
    @FXML
    public ComboBox<String> selectOrderCombo;
    @FXML
    public Label productIdLbl;
    @FXML
    public ComboBox<String> selectReturnProductCombo;
    @FXML
    public Label productNameLbl;
    @FXML
    public Label purchasedQtyLbl;
    @FXML
    public ComboBox<ReturnReason> selectReturnReasonCombo;
    @FXML
    public TextArea returnDescriptionTxtArea;
    @FXML
    public Label customerNameLbl;
    @FXML
    public Label customerIdLbl;
    @FXML
    public Label grossAmountLbl;
    @FXML
    public Label orderDiscountLbl;
    @FXML
    public Label netAmountLbl;
    @FXML
    public TextField returnedQtyTxt;
    @FXML
    public Label returnedProductPriceLbl;

    private static final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDERS);
    private static final OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private static final ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    private static final ReturnBo returnsBo = BoFactory.getInstance().getBo(BoType.RETURN);
    private String selectedOrderId;
    private Orders selectedOrderObj;
    private Product returnedProductObj;
    private double returnedProductUnitPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentDate();
        getCurrentTime();
        loadOrdersCombo();
        loadReturnReasonCombo();

        //
        selectReturnProductCombo.setDisable(true);
    }

    public void saveReturnBtnOnAction() {
        boolean isSaved = save();
        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION, "Return saved successfully !").show();
            clearForm();
        }else {
            new Alert(Alert.AlertType.WARNING, "Please try again !").show();
        }
    }

    public void selectOrderComboOnAction() {
        loadOrderProductsCombo();
        loadCustomerDetails();
        loadOrderDetailTable();
        loadOrderDetails();

        //
        selectReturnProductCombo.setDisable(false);
    }

    public void clearBtnOnAction() {
        clearForm();
    }

    public void selectReturnProductComboOnAction() {
        loadProductDetails();
    }

    @Override
    public void loadOrdersCombo() {
        try {
            List<String> ordersList = orderBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableList(ordersList);

            selectOrderCombo.setItems(observableList);
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    public void loadReturnReasonCombo() {
        ObservableList<ReturnReason> options = FXCollections.observableArrayList();
        options.add(ReturnReason.CUSTOMER_LOST_INTEREST);
        options.add(ReturnReason.WRONG_SIZE);
        options.add(ReturnReason.DAMAGED);
        selectReturnReasonCombo.setItems(options);
    }

    @Override
    public void loadCustomerDetails() {
        try {
            Customer customer = selectedOrderObj.getCustomer();
            customerIdLbl.setText(String.valueOf(customer.getId()));
            customerNameLbl.setText(customer.getName());
        }catch (NullPointerException e){
            customerIdLbl.setText("N/A");
            customerNameLbl.setText("N/A");
        }
    }

    @Override
    public void loadOrderDetailTable() {
        List<OrderDetail> orderDetailList = orderDetailBo.retrieveByOrderId(selectedOrderObj);
        ObservableList<OrderDetail> observableList = FXCollections.observableList(orderDetailList);

        orderDetailTable.setItems(observableList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("detailId"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @Override
    public void loadOrderDetails() {
        Orders orders = orderBo.retrieveById(selectedOrderId).get(0);

        grossAmountLbl.setText(String.valueOf(orders.getGrossAmount()));
        orderDiscountLbl.setText(String.valueOf(orders.getDiscount()));
        netAmountLbl.setText(String.valueOf(orders.getNetAmount()));
    }

    @Override
    public void loadOrderProductsCombo() {
        selectedOrderId = selectOrderCombo.getValue();
        selectedOrderObj = orderBo.retrieveById(selectedOrderId).get(0);
        List<Product> productsList = orderDetailBo.retrieveProductsByOrderId(selectedOrderObj);
        List<String> productIdList = new ArrayList<>();

        productsList.forEach(product -> productIdList.add(product.getId()));

        ObservableList<String> observableList = FXCollections.observableList(productIdList);
        selectReturnProductCombo.setItems(observableList);
    }

    @Override
    public void loadProductDetails() {
        String productId = selectReturnProductCombo.getValue();
        Product returnedProductObj = productBo.retrieveById(productId).get(0);

        List<OrderDetail> orderDetailList = orderDetailBo.retrieveByOrderId(selectedOrderObj);
        for (OrderDetail orderDetail : orderDetailList) {
            Orders orderOnList = orderDetail.getOrders();
            Product productOnList = orderDetail.getProduct();

            if (orderOnList.equals(selectedOrderObj) && productOnList.equals(returnedProductObj)){
                productNameLbl.setText(productOnList.getName());
                purchasedQtyLbl.setText(String.valueOf(orderDetail.getQuantity()));
                returnedProductUnitPrice = orderDetail.getPrice()/orderDetail.getQuantity();
            }
        }
    }

    public void returnedQtyTxtOnAction() {
        try {
            int returnedProductQty = Integer.parseInt(returnedQtyTxt.getText());
            double returnedProductPrice = returnedProductUnitPrice*returnedProductQty;
            returnedProductPriceLbl.setText(String.valueOf(returnedProductPrice));
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.WARNING, "Invalid quantity !").show();
        }
    }

    @Override
    public boolean save() {
        return returnsBo.save(getReturnObject());
    }

    @Override
    public void clearForm() {
        selectOrderCombo.valueProperty().setValue("");
        selectReturnProductCombo.valueProperty().setValue("");
        productNameLbl.setText("");
        purchasedQtyLbl.setText("");
        returnedQtyTxt.setText("");
        returnDescriptionTxtArea.setText("");
    }

    @Override
    public void getCurrentTime() {
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

    @Override
    public void getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDateLbl.setText(dateFormat.format(currentDate));
    }

    @Override
    public boolean loadConfirmAlert(String msg) {
        boolean isConfirm = false;
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, btnYes, btnNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(btnNo)==btnYes) isConfirm = true;

        return isConfirm;
    }

    private Returns getReturnObject(){
        return new Returns(
                selectedOrderObj,
                selectReturnProductCombo.getValue(),
                Integer.parseInt(returnedQtyTxt.getText()),
                Double.parseDouble(returnedProductPriceLbl.getText()),
                selectReturnReasonCombo.getValue(),
                returnDescriptionTxtArea.getText(),
                new Date()
        );
    }
    private int updateReturnStatus(Orders ordersDto, Product productDto){
        return orderDetailBo.updateReturnStatus(selectedOrderObj, returnedProductObj);
    }
}
