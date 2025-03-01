package mis.c112118216;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AccountFXMLController implements Initializable {

    @FXML
    private Button btnCredit;
    @FXML
    private Button btnDebit;
    @FXML
    private Button btnBalance;
    @FXML
    private TextField amountField;
    @FXML
    private TextArea displayArea;

    private Account acc1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // 初始化一個帳號物件 帳戶餘額為 0
        acc1 = new Account();
        System.out.println("視窗被產生時呼叫 initialize()");

    }

    public AccountFXMLController() {
        System.out.println("視窗的建構子被呼叫");
    }

    @FXML
    private void handleCredit(ActionEvent event) {
        // amount 變數放置使用者的輸入金額
        int amount = Integer.parseInt(amountField.getText());
        //呼叫存款方法
        acc1.credit(amount); //重點!!!
        String msg = String.format("存款%d\n 帳戶餘額:%d\n\n", amount, acc1.getBalance());
        displayArea.appendText(msg);

    }

    @FXML
    private void handleDebit(ActionEvent event) {
        // amount 變數放置使用者的輸入金額
        int amount = Integer.parseInt(amountField.getText());
        //呼叫存款方法
        if (acc1.getBalance() >= amount) {
            acc1.debit(amount); //重點!!!
            String msg = String.format("提款%d\n 帳戶餘額:%d\n\n", amount, acc1.getBalance());
            displayArea.appendText(msg);
        } else {
            String msg = String.format("提款%d\n 帳戶餘額:%d\n 帳戶不足:%d\n\n", amount, acc1.getBalance(), amount - acc1.getBalance());
            displayArea.appendText(msg);
        }

    }

    @FXML
    private void handleBalance(ActionEvent event) {
        String msg = String.format("帳戶餘額:%d\n\n", acc1.getBalance());
        displayArea.appendText(msg);
    }

    @FXML
    private void clear(ActionEvent event) {
        amountField.setText("");
    }

    //一整套Account方法
    private static class Account {

        private int balance;

        public Account() {
            balance = 0;
            System.out.println("初始化一個無參數 Account 物件");
        }

        public Account(int initialBalance) {
            if (initialBalance > 0.0) {
                balance = initialBalance;
            } else {
                balance = 0;
            }
            System.out.println("初始化一個 Account 物件");
        }

        public void credit(int amount) {
            balance = balance + amount;
        }

        public int getBalance() {
            return balance;
        }

        public boolean debit(int amount) {
            if (balance >= amount) {
                balance = balance - amount;
                return true; //表示成功提扣款
            }
            return false; //表示帳戶不足提款失敗
        }

    }//Account class
}
