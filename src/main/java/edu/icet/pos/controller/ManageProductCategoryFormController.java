package edu.icet.pos.controller;

import com.mysql.cj.log.Log;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.dto.Category;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.GetModelMapper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
public class ManageProductCategoryFormController extends SuperFormController implements Initializable {
    @FXML
    public Label categoryIdTxt;
    @FXML
    public ComboBox<String> selectCategoryIdCombo;
    @FXML
    public TextField categoryNameTxt;
    @FXML
    public TableView<Category> categoryTableView;
    @FXML
    public TableColumn<Category, String> categoryIdCol;
    @FXML
    public TableColumn<Category, String> categoryNameCol;
    @FXML
    public Label currentDateLbl;
    @FXML
    public Label timerLbl;

    ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    CategoryBo categoryBo = BoFactory.getInstance().getBo(BoType.CATEGORY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentDate(currentDateLbl);
        getCurrentTime(timerLbl);
        loadId();
        loadCategoryIdCombo();
        loadDetailTable();
    }

    @Override
    void save() {
        Category category = new Category(
                categoryIdTxt.getText(),
                categoryNameTxt.getText()
        );
        try {
            boolean isSaved = categoryBo.save(category);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Category saved successfully !").show();
                loadId();
                loadCategoryIdCombo();
                clearForm();
            }else {
                new Alert(Alert.AlertType.ERROR, "Please try again !").show();
            }
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    public void clearForm() {
        categoryNameTxt.setText("");
    }

    @Override
    void loadId() {
        int number=0;
        try {
            ObservableList<String> categoryList = FXCollections.observableList(categoryBo.retrieveAllId());
            String lastCatId = categoryList.get(categoryList.size() - 1);
            number = Integer.parseInt(lastCatId.split("CAT")[1]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            categoryIdTxt.setText("CAT0001");
        }
        number++;
        categoryIdTxt.setText(String.format("CAT%04d", number));
    }

    @Override
    void loadDetailTable() {
        try {
            List<Category> categoryList = categoryBo.retrieveAll();
            ObservableList<Category> observableList = FXCollections.observableList(categoryList);
            categoryTableView.setItems(observableList);

            categoryIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }

    @Override
    void searchDetailById() {
        String selectedCategory = selectCategoryIdCombo.getValue();
        List<Category> categoryList = categoryBo.retrieveById(selectedCategory);
        Category category = mapper.map(categoryList.get(0), Category.class);
        categoryNameTxt.setText(category.getName());
    }

    @Override
    void updateById() {
        Category category = new Category(
                selectCategoryIdCombo.getValue(),
                categoryNameTxt.getText()
        );
        if (loadConfirmAlert("Confirm update ?")){
            int rowCountUpdated = categoryBo.update(category);

            if (rowCountUpdated>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Category name updated successfully !").show();
                selectCategoryIdCombo.getSelectionModel().clearSelection();
                loadId();
                loadDetailTable();
                clearForm();
            }else {
                new Alert(Alert.AlertType.ERROR, "Please try again !").show();
            }
        }
    }

    public void saveBtnOnAction() {
        save();
    }

    public void updateBtnOnAction() {
        updateById();
    }

    public void catIdComboOnAction() {
        searchDetailById();
    }

    private void loadCategoryIdCombo(){
        try {
            List<String> list = categoryBo.retrieveAllId();
            ObservableList<String> observableList = FXCollections.observableArrayList(list);
            selectCategoryIdCombo.setItems(observableList);
        }catch (NullPointerException e){
            log.info("Null pointer exception !");
        }
    }
}
