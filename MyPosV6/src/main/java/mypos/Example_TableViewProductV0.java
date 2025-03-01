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
import models.Product;

public class Example_TableViewProductV0 extends Application {

    private final ObservableList<Product> product_list = getProductList();

    @Override
    public void start(Stage primaryStage) {
        TableView<Product> table = new TableView<>();
        table.setEditable(true); // 允許表格編輯

        // 定義表格欄位
        TableColumn<Product, String> idColumn = createColumn("Product ID", "product_id");
        TableColumn<Product, String> categoryColumn = createColumn("Category", "category");
        TableColumn<Product, String> nameColumn = createColumn("Name", "name");
        TableColumn<Product, Integer> priceColumn = createColumn("Price", "price", new IntegerStringConverter());
        TableColumn<Product, String> photoColumn = createColumn("Photo", "photo");
        TableColumn<Product, String> descriptionColumn = createColumn("Description", "description");

        // 設定欄位允許編輯後的事件處理
        setEditCommitHandler(idColumn, "product_id");
        setEditCommitHandler(categoryColumn, "category");
        setEditCommitHandler(nameColumn, "name");
        setEditCommitHandler(priceColumn, "price");
        setEditCommitHandler(photoColumn, "photo");
        setEditCommitHandler(descriptionColumn, "description");

        table.getColumns().addAll(idColumn, categoryColumn, nameColumn, priceColumn, photoColumn, descriptionColumn);
        table.setItems(product_list);

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private <T> void setEditCommitHandler(TableColumn<Product, T> column, String propertyName) {
        column.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            switch (propertyName) {
                case "product_id":
                    product.setProduct_id(event.getNewValue().toString());
                    break;
                case "category":
                    product.setCategory(event.getNewValue().toString());
                    break;
                case "name":
                    product.setName(event.getNewValue().toString());
                    break;
                case "price":
                    product.setPrice((Integer) event.getNewValue());
                    break;
                case "photo":
                    product.setPhoto(event.getNewValue().toString());
                    break;
                case "description":
                    product.setDescription(event.getNewValue().toString());
                    break;
            }
            System.out.println(propertyName + " updated: " + product);
        });
    }

    private TableColumn<Product, String> createColumn(String title, String propertyName) {
        TableColumn<Product, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        return column;
    }

    private TableColumn<Product, Integer> createColumn(String title, String propertyName, IntegerStringConverter converter) {
        TableColumn<Product, Integer> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        return column;
    }

    private ObservableList<Product> getProductList() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        // 您可以在這裡添加您的產品
        products.add(new Product("P001", "Electronics", "Smartphone", 299, "smartphone.png", "An advanced smartphone"));
        // 添加更多產品...
        return products;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
