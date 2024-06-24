package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.dto.User;
import edu.icet.pos.dto.holder_dto.CurrentUserHolder;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.PasswordBasedEncryption;
import edu.icet.pos.util.UserType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LoginFormController extends PasswordBasedEncryption {
    @FXML
    private ImageView padlockImageView;
    @FXML
    private TextField userNameTxt;
    @FXML
    private PasswordField passwordPw;
    @FXML
    private Button loginBtn;
    private String userName;
    private final UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void loginBtnOnAction() {
        try {
            loadSystemLogin();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
    //PASSWORD FIELD
    public void passwordOnAction() {
        try {
            loadSystemLogin();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
    //RETRIEVE USER OBJECT BY USERNAME FROM DB
    public String getSecuredPassword(String username){
        try {
            User user = userBo.retrieveByUsername(username);
            return user.getPassword();
        }catch (IndexOutOfBoundsException e){
            log.info(e.getMessage());
        }
        return null;
    }
    //LOAD PASSWORD RESET FORM
    public void resetPasswordLinkOnAction() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/reset-password-form.fxml"))));
        stage.show();
    }
    //COMPARE SECURED PW FROM DB WITH USER ENTERED PW
    private boolean verifyUserLogin(){
        userName = userNameTxt.getText();
        String password = passwordPw.getText();
        String securedPassword = getSecuredPassword(userName);
        return PasswordBasedEncryption.verifyUserPassword(password, securedPassword);
    }
    //PROCEED LOGIN
    private void loadSystemLogin() throws IOException {
        if (verifyUserLogin()){
            //GET CURRENT USER ENTITY
            User user = userBo.retrieveByUsername(userName);
            setCurrentUser(user);
            //CLOSE LOGIN FORM
            Stage loginForm = (Stage) loginBtn.getScene().getWindow();
            loginForm.close();

            if (user.getType().equals(UserType.ADMIN)) {
                //LOAD REPORT DASHBORD FOR ADMIN
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/admin-dashboard-form.fxml"))));
                stage.show();
            }else {
                //LOAD ORDERS FORM FOR USERS
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-orders-form.fxml"))));
                stage.show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Wrong Username or Password, Please try again !").show();
        }
    }
    //HOLD USER DETAILS
    private void setCurrentUser(User user){
        CurrentUserHolder currentUserHolder = CurrentUserHolder.getInstance();
        currentUserHolder.setUser(user);
    }
}
