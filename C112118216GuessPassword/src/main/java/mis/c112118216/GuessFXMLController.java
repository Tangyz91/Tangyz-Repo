package mis.c112118216;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GuessFXMLController implements Initializable {

    @FXML
    private TextField guessField;
    @FXML
    private TextArea display;
    private int ans; //使用者輸入的數字
    private final int max = 100; //1-20 之間
    private int magic;
    private Random rnd;
    private int count = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rnd = new Random();
        magic = rnd.nextInt(max) + 1;
        System.out.println("initialize()被呼叫");
    }

    @FXML
    private void guess(ActionEvent event) {
        if (!guessField.getText().isEmpty()) {
            ans = Integer.parseInt(guessField.getText());
            String result;
            if (ans > magic) {
                result = String.format("比 %d 小", ans);
            } else if (ans < magic) {
                result = String.format("比 %d 大", ans);
            } else {
                result = "猜對了";
            }
            count++;
            display.appendText(String.format("%s(次數:%d)\n", result, count));
            guessField.requestFocus(); //讓游標作用在此
            guessField.selectAll(); //欄位內的文字全選起來(會反白)
        }
    }

    @FXML
    private void playAgain(ActionEvent event) {
        magic = rnd.nextInt(max) + 1;
        count = 0;
        display.setText("");
        guessField.setText("");
        guessField.requestFocus();

    }

}
