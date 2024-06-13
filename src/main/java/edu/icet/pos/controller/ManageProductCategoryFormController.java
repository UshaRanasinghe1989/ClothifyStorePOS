package edu.icet.pos.controller;

import com.mysql.cj.log.Log;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.dto.Category;
import edu.icet.pos.util.BoType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.UnknownEntityException;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Slf4j
public class ManageProductCategoryFormController implements FormController, Initializable {
    public Label categoryIdTxt;
    public ComboBox selectCategoryIdCombo;
    public TextField categoryNameTxt;
    public TableView categoryTableView;
    public TableColumn categoryIdCol;
    public TableColumn categoryNameCol;
    public Label currentDateLbl;
    public Label timerLbl;

    CategoryBo categoryBo = BoFactory.getInstance().getBo(BoType.CATEGORY);
    List categoryList;
    ObservableList<Category> observableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateTime();
        getCategoryId();
        loadCategoryIdCombo();

        categoryIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        loadCategoryTable();
    }

    @Override
    public void loadDateTime() {
        currentDateLbl.setText(getCurrentDate());
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
    public String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(currentDate);
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

    @Override
    public void clearForm() {
        categoryNameTxt.setText("");
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        Category category = new Category(
                categoryIdTxt.getText(),
                categoryNameTxt.getText()
        );
        boolean isSaved = categoryBo.save(category);
        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION, "Category saved successfully !").show();
            getCategoryId();
            loadCategoryIdCombo();
            clearForm();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please try again !").show();
        }
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        Category category = new Category(
                selectCategoryIdCombo.getValue().toString(),
                categoryNameTxt.getText()
        );
        if (loadConfirmAlert("Confirm update ?")){
            int rowCountUpdated = categoryBo.update(category);

            if (rowCountUpdated>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Category name updated successfully !").show();
                selectCategoryIdCombo.getSelectionModel().clearSelection();
                getCategoryId();
                loadCategoryTable();
                clearForm();
            }else {
                new Alert(Alert.AlertType.ERROR, "Please try again !").show();
            }
        }
    }

    public void catIdComboOnAction(ActionEvent actionEvent) {
        categoryList = categoryBo.retrieveById(selectCategoryIdCombo.getValue().toString());
        Category category = new ModelMapper().map(categoryList.get(0), Category.class);
        categoryNameTxt.setText(category.getName());
    }
    private void loadCategoryTable(){
        try {
            categoryList = categoryBo.retrieveAll();
            observableList = FXCollections.observableList(categoryList);
            categoryTableView.setItems(observableList);
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    private void getCategoryId(){
        String lastCatId=null;
        int number=0;
        try {
            categoryList = FXCollections.observableList(categoryBo.retrieveAllId());
            lastCatId = (String) categoryList.get(categoryList.size() - 1);
            number = Integer.parseInt(lastCatId.split("CAT")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            categoryIdTxt.setText("CAT0001");
        }
        number++;
        categoryIdTxt.setText(String.format("CAT%04d", number));
    }

    private void loadCategoryIdCombo(){
        try {
            List list = categoryBo.retrieveAllId();
            ObservableList observableList = FXCollections.observableArrayList(list);
            selectCategoryIdCombo.setItems(observableList);
        }catch (NullPointerException e){
            log.info("Null pointer exception !");
        }
    }
}
