package edu.icet.pos.controller;

public interface FormController {
    void loadDateTime();
    String getCurrentDate();
    boolean loadConfirmAlert(String msg);

    void clearForm();
}
