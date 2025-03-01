package mypos;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Example_TableViewOrder extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TableView<Person> tableView = new TableView<>();

        // 創建列並設置為可編輯
        tableView.setEditable(true);

        TableColumn<Person, String> column1 = new TableColumn<>("名字");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        //定義欄位成為可編輯的
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setOnEditCommit(
            event -> {
                Person person = event.getRowValue();
                person.setFirstName(event.getNewValue());
            }
        );

        TableColumn<Person, String> column2 = new TableColumn<>("姓氏");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Person, Integer> column3 = new TableColumn<>("年齡");
        column3.setCellValueFactory(new PropertyValueFactory<>("age"));
        column3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        column3.setOnEditCommit(
            event -> {
                Person person = event.getRowValue();
                person.setAge(event.getNewValue());
            }
        );

        // 將列添加到表格中
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);


        // 添加數據到表格:用 ObservableList最方便
        ObservableList<Person> data = FXCollections.observableArrayList(
                        new Person("John", "Goodman", 25),  
                        new Person("Jane","'Wood", 30), 
                        new Person("Bob", "Smith", 40)
        );
        tableView.setItems(data);
        
        // 添加數據到表格: 逐筆加入
        tableView.getItems().add(new Person("John", "Doe", 30));
        tableView.getItems().add(new Person("Jane", "Deer", 25));        

        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //若是放在內部，則必須是public靜態類別，這樣JavaFx才能讀取它(一定要public才讀得到)，否則會有以下錯誤訊息:
    // cannot access a member of class mypos.TableViewExample$Person (in module mypos)
    //放在外部最好，跟我們以前例行的做法一樣: 放在一個獨立的檔案Person.java 類別名稱為 public class Person{}
    public static class Person {
        private String firstName;
        private String lastName;
        private int age;

        public Person(String fName, String lName, int age) {
            this.firstName = fName;
            this.lastName = lName;
            this.age = age;
        }

        // Getters and Setters
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }//Person

} //TableViewExample
