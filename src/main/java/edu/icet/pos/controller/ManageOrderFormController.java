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
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.entity.ProductEntity;
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
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.ArrayList;
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
    public TableView<OrderDetail> cartDetailTable;
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

    private List<String> cartArrayList = new ArrayList<>();
    //private ObservableList<String> cartObservableList;
    double unitPrice;

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

    public void placeOrderBtnOnAction(ActionEvent actionEvent) {
    }

    public void selectProductComboOnAction() {
        String selectedProduct = String.valueOf(selectProductNameCombo.getValue());
        List<Product> productList = productBo.retrieveById(selectedProduct);

        String selectedProductId = productList.get(0).getId();
        String description = productList.get(0).getDescription();

        productIdLbl.setText(selectedProductId);
        productNameLbl.setText(productList.get(0).getName());
        productDescriptionLbl.setText(description.isEmpty() ? "N/A":description);
        productSizeLbl.setText(String.valueOf(productList.get(0).getSize()));

        loadActiveStockDetails(selectedProductId);
    }

    public void addToCartBtnOnAction() {
        OrderEntity orderEntity = new OrderEntity(orderIdLbl.getText());
        ProductEntity productEntity = new ModelMapper().map(
                productBo.retrieveById(selectProductNameCombo.getValue()).get(0), ProductEntity.class);

        orderEntity.addProduct(productEntity);

        //orderDetailBo.retrieveByOrderId(orderIdLbl.getText());

        /*Orders orders = new Orders(
                orderIdLbl.getText(),
                activeStockIdLbl.getText(),
                orderDetailBo.retrieveCountOrderId(orderIdLbl.getText()),

        );*/
        /*Product product = productBo.retrieveById(productIdLbl.getText()).get(0);

        double price = Integer.parseInt(requiredQtyTxt.getText()) * Double.parseDouble(unitPriceLbl.getText());
        OrderDetail orderDetail = new OrderDetail(
                orders,
                product,
                activeStockIdLbl.getText(),
                Integer.parseInt(requiredQtyTxt.getText()),
                price,
                0.0
        );
        orderDetailBo.save(orderDetail);

        loadCartTable();*/
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

    private void loadCartTable() {
        /*Orders orders = new Orders(orderIdLbl.getText());
        List<OrderDetail> orderDetailList = orderDetailBo.retrieveByOrderEntity(orders);
        log.info(orderDetailList.toString()+"#######");
        ObservableList<OrderDetail> cartObservableList = FXCollections.observableList(orderDetailList);
        cartDetailTable.setItems(cartObservableList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>(""));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>(""));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));*/
    }

    private void loadActiveStockDetails(String productId) {
        Product product = productBo.retrieveById(productId).get(0);
        List<Stock> stockList = stockBo.retrieveActiveStockByProduct(product);
        Stock stock = stockList.get(0);

        activeStockIdLbl.setText(stock.getId());
        unitPrice = stock.getUnitPrice();
        unitPriceLbl.setText(String.valueOf(unitPrice));
    }

    @Override
    void save() {

    }

    @Override
    void clearForm() {

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
