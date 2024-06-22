package edu.icet.pos.controller;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.util.BoType;
import edu.icet.pos.util.EmailUtil;
import edu.icet.pos.util.OTPGenerator;
import edu.icet.pos.util.PasswordBasedEncryption;
import jakarta.mail.MessagingException;
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
public class ResetPasswordFormController extends PasswordBasedEncryption {
    @FXML
    private TextField emailTxt;

    @FXML
    private PasswordField newPw;

    @FXML
    private TextField otpTxt;

    @FXML
    private PasswordField reenteredNewPw;

    @FXML
    private Button resetPwBtn;
    private String email;
    private String otp;
    private String newPassword;

    private final UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    @FXML
    void resetPasswordBtnOnAction() {
        email = emailTxt.getText();
        newPassword = newPw.getText();

        try {
            generateOTP();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void otpTxtOnAction() {
        boolean isValid = validateOTP();
        if (isValid){
            String encryptedPw = getEncryptedPassword(newPassword);
            int rowCount = userBo.resetPassword(email, encryptedPw);
            if (rowCount>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Password reset successfully !").show();
                clearForm();
            }else {
                new Alert(Alert.AlertType.WARNING, "Please try again !").show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Resend OTP !").show();
        }
    }

    private void generateOTP() throws IOException {
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

    private boolean validateOTP(){
        return otp.equals(otpTxt.getText());
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
