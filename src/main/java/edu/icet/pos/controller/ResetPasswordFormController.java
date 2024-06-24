package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.EmailUtil;
import edu.icet.pos.util.OTPGenerator;
import edu.icet.pos.util.PasswordBasedEncryption;
import jakarta.mail.MessagingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class ResetPasswordFormController extends PasswordBasedEncryption implements Initializable {
    @FXML
    private Label warningLbl;
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField newPw;
    @FXML
    private TextField otpTxt;
    @FXML
    private PasswordField reenteredNewPw;
    private String email;
    private String otp;
    private String newPassword;
    private String tempReenteredPw="";
    private String reenteredPw="";
    private String enteredOTP = "";
    private final UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warningLbl.setVisible(false);
    }
    @FXML
    private void sendOTPBtnOnAction() {
        email = emailTxt.getText();
        if (email.isEmpty() || newPassword.isEmpty() || reenteredPw.isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please fill all details !").show();
        }else if (!newPassword.equals(reenteredPw)){
            warningLbl.setText("Re-entered password is not correct !");
            warningLbl.setVisible(true);
        }else {
            generateOTP();
        }
    }
    @FXML
    private void reenterNewPwOnAction(javafx.scene.input.KeyEvent keyEvent) {
        newPassword = newPw.getText().trim();
        char typedKey = keyEvent.getCharacter().charAt(0);
        String character = String.valueOf(typedKey);
        tempReenteredPw += character;

        while (true){
            warningLbl.setText("Re-entered password is not correct !");
            warningLbl.setVisible(true);
            break;
        }

        while (tempReenteredPw.trim().length()==newPassword.length()){
            if (newPassword.equals(tempReenteredPw.trim())) {
                warningLbl.setVisible(false);
                reenteredPw = tempReenteredPw.trim();
            }
            break;
        }
    }
    @FXML
    void otpTxtOnAction(javafx.scene.input.KeyEvent keyEvent) {
        boolean isValid = validateOTP(keyEvent);
        if (isValid){
            String encryptedPw = getEncryptedPassword(newPassword);
            int rowCount = userBo.resetPassword(email, encryptedPw);
            if (rowCount>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Password reset successfully !").show();
                clearForm();
            }else {
                new Alert(Alert.AlertType.WARNING, "Wrong Username, Please try again !").show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Resend OTP !").show();
        }
    }

    private void generateOTP() {
        String secretKey = OTPGenerator.generateSecretKey();
        otp = OTPGenerator.generateOTP(secretKey, 6);
        sendOTP();
    }
    private void sendOTP(){
        EmailUtil emailUtil = new EmailUtil();
        try {
            emailUtil.composeEmail(otp, email, "OTP sent from Clothify POS");
        } catch (MessagingException e) {
            log.info(e.getMessage());
        }
    }

    private boolean validateOTP(javafx.scene.input.KeyEvent e){
        boolean OTPisVerified=false;
        char otpChar = e.getCharacter().charAt(0);
        String otpString = String.valueOf(otpChar);
        enteredOTP += otpString;

        while(enteredOTP.length()==6){
            OTPisVerified = otp.equals(enteredOTP);
        }
        return OTPisVerified;
    }

    //GENERATE ENCRYPTED PASSWORD
    private String getEncryptedPassword(String password){
        return PasswordBasedEncryption.generateSecurePassword(password);
    }
    private void clearForm(){
        emailTxt.setText("");
        newPw.setText("");
        reenteredNewPw.setText("");
        otpTxt.setText("");
    }
}
