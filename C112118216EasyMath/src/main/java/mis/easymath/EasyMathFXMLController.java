
package mis.easymath;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class EasyMathFXMLController implements Initializable {

    @FXML
    private TextField number1;
    @FXML
    private TextField number2;
    @FXML
    private TextArea display;
    @FXML
    private Button buttonMinus;
    @FXML
    private Button bottonMul;
    @FXML
    private Button bottonDiv;
    @FXML
    private Button remainder;
    @FXML
    private Button bottonclear;
    @FXML
    private Button buttonAdd;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
/*
    private void calculate(ActionEvent event) {
        int n1 = Integer.parseInt(number1.getText());
        int n2 = Integer.parseInt(number2.getText());
        int ans = 0;
// System.out.println(event); //印出事件物件看看
        Button btn = (Button) event.getSource();
        String operator = "";
//if ("buttonAdd".equals( btn.getId() ))
        if (btn.getId().equals("buttonAdd")) {
            ans = n1 + n2;
            operator = "+";
        } else if (btn.getId().equals("buttonMinus")) {
            ans = n1 - n2;
            operator = "-";
        } else if (btn.getId().equals("buttonMul")) {
            ans = n1 * n2;
            operator = "*";
        } else if (btn.getId().equals("buttonDiv")) {
            ans = n1 / n2;
            operator = "/";
        }
        String result = String.format("%d%s%d=%d", n1, operator, n2, ans);
        display.setText(result);
    }*/

    @FXML
    private void add(ActionEvent event) {
        int n1 = Integer.parseInt(number1.getText());
        int n2 = Integer.parseInt(number2.getText());
        int ans = n1 + n2;
        String result = String.format("%d+%d=%d", n1, n2, ans);
        display.setText(result);
        // display.appendText(result);
    }

    @FXML
    private void minus(ActionEvent event) {
        int n1 = Integer.parseInt(number1.getText());
        int n2 = Integer.parseInt(number2.getText());
        int ans = n1 - n2;
        String result = String.format("%d-%d=%d", n1, n2, ans);
        display.setText(result);
    }

    @FXML
    private void multiply(ActionEvent event) {
        int n1 = Integer.parseInt(number1.getText());
        int n2 = Integer.parseInt(number2.getText());
        int ans = n1 * n2;
        String result = String.format("%d*%d=%d", n1, n2, ans);
        display.setText(result);
    }

    @FXML
    private void divide(ActionEvent event) {
        int n1 = Integer.parseInt(number1.getText());
        int n2 = Integer.parseInt(number2.getText());
        int ans = n1 / n2;
        String result = String.format("%d/%d=%d", n1, n2, ans);
        display.setText(result);
    }

    @FXML
    private void remain(ActionEvent event) {
        int n1 = Integer.parseInt(number1.getText());
        int n2 = Integer.parseInt(number2.getText());
        int ans = n1 % n2;
        String result = String.format("%d%%%d=%d", n1, n2, ans);
        display.setText(result);
    }

    @FXML
    private void clear(ActionEvent event) {
        number1.setText("");
        number2.setText("");
        display.setText("");
    }

}
