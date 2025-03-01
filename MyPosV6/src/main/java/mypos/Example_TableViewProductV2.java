package mypos;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import models.Product;

public class Example_TableViewProductV2 extends Application {

    private final ObservableList<Product> product_list = getProductList();
    private final Label statusLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        TableView<Product> table = new TableView<>();
        table.setEditable(true);

        // 定義表格欄位
        TableColumn<Product, String> idColumn = createColumn("Product ID", "product_id");
        TableColumn<Product, String> categoryColumn = createColumn("Category", "category");
        TableColumn<Product, String> nameColumn = createColumn("Name", "name");
        TableColumn<Product, Integer> priceColumn = createColumn("Price", "price", new IntegerStringConverter());
        TableColumn<Product, String> photoColumn = createColumn("Photo", "photo");
        TableColumn<Product, String> descriptionColumn = createColumn("Description", "description");
        TableColumn<Product, Void> actionColumn = new TableColumn<>("Action");

        // 設定欄位允許編輯後的事件處理
        setEditCommitHandler(idColumn, "product_id");
        setEditCommitHandler(categoryColumn, "category");
        setEditCommitHandler(nameColumn, "name");
        setEditCommitHandler(priceColumn, "price");
        setEditCommitHandler(photoColumn, "photo");
        setEditCommitHandler(descriptionColumn, "description");

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btnDelete = new Button("Delete");
            private final Button btnUpdate = new Button("Update");
            private final Button btnDuplicate = new Button("Duplicate");
            private final Button btnSave = new Button("Save");

            {
                btnDelete.setOnAction(event -> {
                    Product product = getTableRow().getItem();
                    if (product != null) {
                        product_list.remove(product);
                        // 從資料庫刪除這一筆
                        //productDao.delete(product.getProduct_id());
                        statusLabel.setText("Deleted: " + product.getName());
                        System.out.println("Deleted: " + product.getName());
                    }
                });

                btnDelete.getStyleClass().setAll("button", "danger");
            }

            // Add similar event handlers for other buttons (Update, Duplicate, Save)
            HBox pane = new HBox(btnDelete, btnUpdate, btnDuplicate, btnSave);

            {
                pane.setAlignment(Pos.CENTER);
                pane.setSpacing(10);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                // Update the graphic when the item or empty status changes
                if (!empty) {
                    setGraphic(pane);
                } else {
                    setGraphic(null);
                }
            }
        });

        /*
        // 以下另一種寫法
        // 設定Action欄位的按鈕
        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<>() {

                    private final Button btnDelete = new Button("Delete");
                    private final Button btnUpdate = new Button("Update");
                    private final Button btnDuplicate = new Button("Duplicate");

                    {
                        btnDelete.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            product_list.remove(product);
                            statusLabel.setText("Deleted: " + product.getName());
                            System.out.println("Deleted: " + product.getName());
                        });
                        btnDelete.getStyleClass().setAll("button", "danger");

                        btnUpdate.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            // 更新邏輯
                            statusLabel.setText("Updated: " + product.getName());
                            System.out.println("Updated: " + product.getName());
                        });
                        btnUpdate.getStyleClass().setAll("button", "primary");

                        btnDuplicate.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            product_list.add(new Product(product.getProduct_id(), product.getCategory(), product.getName(), product.getPrice(), product.getPhoto(), product.getDescription()));
                            statusLabel.setText("Duplicated: " + product.getName());
                            System.out.println("Duplicated: " + product.getName());
                        });
                        btnDuplicate.getStyleClass().setAll("button", "warning");
                    }

                    HBox pane = new HBox(btnDelete, btnUpdate, btnDuplicate);

                    {
                        pane.setAlignment(Pos.CENTER);
                        pane.setSpacing(10);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : pane);
                    }
                };
            }
        };
         */
        //actionColumn.setCellFactory(cellFactory);
        table.getColumns().addAll(idColumn, categoryColumn, nameColumn, priceColumn, photoColumn, descriptionColumn, actionColumn);
        table.setItems(product_list);

        VBox vbox = new VBox(table, statusLabel);
        vbox.getStylesheets().add("/css/bootstrap3.css");

        // 添加CSS樣式
        table.getStyleClass().add("table");

        Scene scene = new Scene(vbox, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
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

    private ObservableList<Product> getProductList() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product("P001", "Electronics", "Smartphone1", 299, "smartphone.png", "An advanced smartphone"));
        products.add(new Product("P002", "Electronics", "Smartphone2", 500, "smartphone.png", "An advanced smartphone"));
        // 添加更多產品...
        return products;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
