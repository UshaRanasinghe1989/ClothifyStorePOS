package edu.icet.pos.controller.return_controller;

public interface ReturnInterface {
    void loadOrdersCombo();
    void loadReturnReasonCombo();
    void loadCustomerDetails();
    void loadOrderDetailTable();
    void loadOrderDetails();
    void loadOrderProductsCombo();
    void loadProductDetails();
    boolean save();
    void clearForm();
    void getCurrentTime();
    void getCurrentDate();
    boolean loadConfirmAlert(String msg);
}
