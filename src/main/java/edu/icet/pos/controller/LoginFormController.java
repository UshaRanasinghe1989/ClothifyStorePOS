package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.dto.User;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.PasswordBasedEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController extends PasswordBasedEncryption {
    public TextField userNameTxt;
    public PasswordField passwordPw;

    public static final String saltValue = PasswordBasedEncryption.getSaltvalue(30);
    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {
        if (verifyUserLogin()){
            //LOAD DASHBORD
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/admin-dashboard-form.fxml"))));
            stage.show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please try again !").show();
        }
    }
    private boolean verifyUserLogin(){
        String userName = userNameTxt.getText();
        String password = passwordPw.getText();
        String securedPassword = getSecuredPassword(userName);
        boolean status = PasswordBasedEncryption.verifyUserPassword(password, securedPassword);
        return status;
    }
    public String getSecuredPassword(String username){
        User user = userBo.retrieveByUsername(username);
        return user.getPassword();
    }
}
