package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.dto.User;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.PasswordBasedEncryption;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LoginFormController extends PasswordBasedEncryption {
    @FXML
    public TextField userNameTxt;
    @FXML
    public PasswordField passwordPw;

    public static final String SALTVALUE = PasswordBasedEncryption.getSaltvalue(30);
    public Button loginBtn;

    public String userName;
    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void loginBtnOnAction() throws IOException {
        if (verifyUserLogin()){
            //CLOSE LOGIN FORM
            Stage loginForm = (Stage) loginBtn.getScene().getWindow();
            loginForm.close();
            //LOAD DASHBORD
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/admin-dashboard-form.fxml"))));
            stage.show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please try again !").show();
        }
    }
    private boolean verifyUserLogin(){
        userName = userNameTxt.getText();
        String password = passwordPw.getText();
        String securedPassword = getSecuredPassword(userName);
        return PasswordBasedEncryption.verifyUserPassword(password, securedPassword);
    }
    public String getSecuredPassword(String username){
        User user = userBo.retrieveByUsername(username);
        return user.getPassword();
    }

    public void resetPasswordLinkOnAction() throws IOException {
        //LOAD PASSWORD RESET FORM
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/reset-password-form.fxml"))));
        stage.show();
    }
}
