
package mis.c112118216bmi;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BmiFXMLController implements Initializable {
    
    
    @FXML
    private TextField heightField;
    @FXML
    private TextField weightField;
    @FXML
    private TextArea display;
    @FXML
    private Button suggest;
    @FXML
    private TextField displayBmi;
    @FXML
    private Button Bmi;
    @FXML
    private Button clear;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }


    @FXML
    private void Suggest(ActionEvent event) {
        //簡單的方法
        double h = Integer.parseInt(heightField.getText()) / 100.0;
        int w = Integer.parseInt(weightField.getText());
        double bmi = w / Math.pow(h, 2);
        
        String msg = "";
        if (bmi > 25) {
            msg = "太重";
        } else if (bmi < 20) {
            msg = "太輕";
        } else {
            msg = "正常";
        }
        display.setText(msg);
    }

    @FXML
    private void clear(ActionEvent event) {
        heightField.setText("");
        weightField.setText("");               
    }

    @FXML
    private void calculateBmi(ActionEvent event) {
        //簡單的方法
        double h = Integer.parseInt(heightField.getText()) / 100.0;
        int w = Integer.parseInt(weightField.getText());
        double bmi = w / Math.pow(h, 2);
        displayBmi.setText(String.format("%.1f", bmi));
    }

}
