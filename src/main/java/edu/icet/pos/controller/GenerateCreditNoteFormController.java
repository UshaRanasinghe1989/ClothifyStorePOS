package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CreditNoteBo;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.bo.custom.ReturnBo;
import edu.icet.pos.dto.CreditNote;
import edu.icet.pos.dto.Orders;
import edu.icet.pos.dto.Returns;
import edu.icet.pos.util.BoType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class GenerateCreditNoteFormController implements Initializable {
    @FXML
    private Label categoryIdTxt;

    @FXML
    private Label creditNoteAmount;

    @FXML
    private Label currentDateLbl;

    @FXML
    private ComboBox<String> selectOrderIdCombo;

    @FXML
    private ComboBox<Integer> selectReturnIdCombo;

    @FXML
    private Label timerLbl;

    private Orders selectedOrderObj;
    private Returns returnsObj;
    private List<Returns> returnsList;

    private final OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDERS);
    private final ReturnBo returnBo = BoFactory.getInstance().getBo(BoType.RETURN);
    private final CreditNoteBo creditNoteBo = BoFactory.getInstance().getBo(BoType.CREDIT_NOTE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentDate();
        getCurrentTime();
        loadOrderIdCombo();
    }

    @FXML
    void orderIdComboOnAction() {
        String selectedOrderId = selectOrderIdCombo.getValue();
        try {
            selectedOrderObj = orderBo.retrieveById(selectedOrderId).get(0);
        }catch (IndexOutOfBoundsException e){
            log.info(e.getMessage());
        }

        loadReturnIdCombo();
    }

    @FXML
    void returnIdComboOnAction() {
        try {
            int selectedReturnId = selectReturnIdCombo.getValue();

            for (int i=0; i<returnsList.size(); i++){
                int returnId = returnsList.get(i).getId();
                if (selectedReturnId==returnId){
                    returnsObj = returnsList.get(i);
                    creditNoteAmount.setText(String.valueOf(returnsObj.getPrice()));
                }
            }
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @FXML
    void saveBtnOnAction() {
        boolean isSaved = creditNoteBo.save(getCreditNoteObj());
        if (isSaved){
            int returnStatusUpdateRowCount = returnStatusUpdate();
            if (returnStatusUpdateRowCount>0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Credit note saved successfully !").show();
                clearForm();
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Please try again !").show();
        }
    }

    private void loadOrderIdCombo(){
        List<String> ordersList = orderBo.retrieveAllId();
        ObservableList<String> observableList = FXCollections.observableList(ordersList);

        selectOrderIdCombo.setItems(observableList);
    }
    private void loadReturnIdCombo() {
        returnsList = returnBo.retrieveReturnByOrder(selectedOrderObj);
        List<Integer> returnIdList = new ArrayList<>();

        for (int i=0; i<returnsList.size(); i++){
            returnIdList.add(returnsList.get(i).getId());
        }

        ObservableList<Integer> observableList = FXCollections.observableList(returnIdList);
        selectReturnIdCombo.setItems(observableList);
    }
    private CreditNote getCreditNoteObj(){
        return new CreditNote(
                returnsObj,
                Double.parseDouble(creditNoteAmount.getText()),
                false,
                new Date()
        );
    }
    private void clearForm(){
        selectOrderIdCombo.valueProperty().setValue(null);
        selectReturnIdCombo.valueProperty().setValue(null);
        creditNoteAmount.setText("");
    }
    private void getCurrentDate(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDateLbl.setText(dateFormat.format(currentDate));
    }
    private void getCurrentTime(){
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
    private int returnStatusUpdate(){
        return returnBo.updateCreditNoteStatus(returnsObj.getId());
    }
}
