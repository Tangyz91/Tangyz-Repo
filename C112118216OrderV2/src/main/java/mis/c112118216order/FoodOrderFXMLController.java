package mis.c112118216order;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Tangyz
 */
public class FoodOrderFXMLController implements Initializable {

    //項目基本資料，一維陣列比較簡易
    private int[] item = {10, 11, 12, 13}; //編號沒用到
    String[] name = {"大麥克套餐", "雙層牛肉起司堡", "香脆薯條", "可樂"};
    int[] price = {200, 80, 50, 30};
    int[] qty = new int[item.length];

    @FXML
    private TextField displaySum;
    @FXML
    private TextArea display;
    @FXML
    private Button food0Plus;
    @FXML
    private Button food0Minus;
    @FXML
    private Button food1Plus;
    @FXML
    private Button food1Minus;
    @FXML
    private Button food2Plus;
    @FXML
    private Button food2Minus;
    @FXML
    private Button food3Plus;
    @FXML
    private Button food3Minus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void printCheck(ActionEvent event) {
        
    }

    @FXML
    private void clear(ActionEvent event) {
        display.setText("");
        displaySum.setText("");
        // 將購買數量矩陣歸 0
        for (int i = 0; i < qty.length; i++) {
            qty[i] = 0;
        }

    }

    private void check() {
        display.setText("");
        int total = 0;
        for (int i = 0; i < qty.length; i++) {
            if (qty[i] != 0) {
                total += qty[i] * price[i];
                String result = String.format("%s: %d*%d\n", name[i], price[i], qty[i]);
                display.appendText(result);
            }
        }
        //顯示總金額
        String result = String.format("總金額: %d\n", total);
        display.appendText("--------------");
        display.appendText(result);
        this.displaySum.setText("$" + total);

    }

    @FXML
    private void addOne(ActionEvent event) {
        Button btn = (Button) event.getSource();
        //System.out.println(btn.getId());
        switch (btn.getId()) {
            case "food0Plus":
                qty[0]++;
                break;
            case "food1Plus":
                qty[1]++;
                break;
            case "food2Plus":
                qty[2]++;
                break;
            case "food3Plus":
                qty[3]++;
                break;

        }//switch
        check();
    }

    @FXML
    private void minusOne(ActionEvent event) {
        Button btn = (Button) event.getSource();
        switch (btn.getId()) {
            case "food0Minus":
                if (qty[0] > 0) {
                    qty[0]--;
                }
                break;
            case "food1Minus":
                if (qty[1] > 0) {
                    qty[1]--;
                }
                break;
            case "food2Minus":
                if (qty[2] > 0) {
                    qty[2]--;
                }
                break;
            case "food3Minus":
                if (qty[3] > 0) {
                    qty[3]--;                    
                }
                break;
        }
        check();
    }

}
