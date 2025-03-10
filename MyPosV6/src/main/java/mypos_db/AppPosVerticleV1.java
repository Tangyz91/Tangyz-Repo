package mypos_db;

///PosV4
import java.util.TreeMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import models.Order;
import models.OrderDAO;
import models.OrderDetail;
import models.Product;
import models.ReadCategoryProduct;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AppPosVerticleV1 extends Application {

    //***********產生資料DAO來使用訂單輸入資料庫之功能
    private OrderDAO orderDao = new OrderDAO();

    //1宣告全域變數)並取得三種菜單的磁磚窗格，隨時被取用。
    private TilePane menuJuice = getProductCategoryMenu("麵食");
    TilePane menuTea = getProductCategoryMenu("飲品");
    TilePane menuCoffee = getProductCategoryMenu("炸物");

    //ObservableList有新增或刪除都會觸動table的更新，也就是發生任何改變時都被通知
    //以下視窗元件有操作到models目錄下的OrderDetail，因此要修改專案模組設定
    private ObservableList<OrderDetail> order_list;
    //顯示訂單詳情表格
    private TableView<OrderDetail> table;

    // 產品資訊
    private final TreeMap<String, Product> product_dict = ReadCategoryProduct.readProduct();

    //顯示訂單詳情白板
    private final TextArea display = new TextArea();

    // 產品菜單磁磚窗格，置放你需要用到的菜單
    public TilePane getProductCategoryMenu(String category) {

        //取得產品清單(呼叫靜態方法取得)
        TreeMap<String, Product> product_dict = ReadCategoryProduct.readProduct();
        //磁磚窗格
        TilePane category_menu = new TilePane(); //
        category_menu.setVgap(10);  //垂直間隙
        category_menu.setHgap(10);
        //設定一個 row有4個columns，放不下就放到下一個row
        category_menu.setPrefColumns(4);

        //將產品清單內容一一置放入產品菜單磁磚窗格
        for (String item_id : product_dict.keySet()) {
            //用if選擇產品類別
            if (product_dict.get(item_id).getCategory().equals(category)) {
                //定義新增一筆按鈕
                Button btn = new Button();

                //width, height 按鈕外框的大小，你要自行調整，讓它美觀。沒有設定外框會大小不一不好看
                btn.setPrefSize(120, 120);
                //btn.setText(product_dict.get(item_id).getName()); //不要顯示文字，顯示圖片就好

                //按鈕元件顯示圖片Creating a graphic (image)
                //讀出圖片
                Image img = new Image("/images/" + product_dict.get(item_id).getPhoto());
                ImageView imgview = new ImageView(img);//圖片顯示物件
                imgview.setFitHeight(80); //設定圖片高度，你要自行調整，讓它美觀
                imgview.setPreserveRatio(true); //圖片的寬高比維持

                //Setting a graphic to the button
                btn.setGraphic(imgview); //按鈕元件顯示圖片
                category_menu.getChildren().add(btn);  //放入菜單磁磚窗格

                //定義按鈕事件-->點選一次，就加入購物車，再點選一次，數量要+1
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //新增一筆訂單到order_list (ArrayList)
                        addToCart(item_id);
                        //order_list.add(new Order("p-109", "新增的果汁", 30, 1));
                        System.out.println(product_dict.get(item_id).getName());
                    }
                });
            }
        }
        return category_menu;
    }//getProductCategoryMenu()

    //2.宣告一個容器(全域變數) menuContainerPane，裝不同種類的菜單，菜單類別選擇按鈕被按下，立即置放該種類的菜單。
    VBox menuContainerPane = new VBox();

    //3.多一個窗格(可以用磁磚窗格最方便)置放菜單類別選擇按鈕，置放於主視窗的最上方區域。
    public TilePane getMenuSelectionContainer() {

        //定義"麵食類"按鈕
        Button btnNoodle = new Button();
        btnNoodle.setText("麵食");
        btnNoodle.getStyleClass().setAll("button", "info");//設定css
        btnNoodle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuContainerPane.getChildren().clear();//先刪除原有的窗格再加入新的類別窗格
                menuContainerPane.getChildren().add(menuJuice);
            }
        });
        //定義"飲品類"按鈕
        Button btnDrink = new Button("飲品");
        btnDrink.getStyleClass().setAll("button", "info");//設定css
        btnDrink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //menuContainerPane.getChildren().clear();//先刪除原有的窗格再加入新的類別窗格
                //menuContainerPane.getChildren().add(menuTea);
                select_category_menu(e);
            }
        });
        //定義"炸物類"按鈕
        Button btnFried = new Button("炸物");
        btnFried.getStyleClass().setAll("button", "info");//設定css
        btnFried.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                select_category_menu(e);
            }
        });

        //使用磁磚窗格，放置前面三個按鈕
        TilePane conntainerCategoryMenuBtn = new TilePane();
        // 自訂button css讓它呈現綠色
        conntainerCategoryMenuBtn.getStylesheets().add("css/myButton.css");

        //conntainerCategoryMenuBtn.setAlignment(Pos.CENTER_LEFT);
        //conntainerCategoryMenuBtn.setPrefColumns(6); //
        conntainerCategoryMenuBtn.setVgap(10);
        conntainerCategoryMenuBtn.setHgap(10);

        conntainerCategoryMenuBtn.getChildren().add(btnNoodle);
        conntainerCategoryMenuBtn.getChildren().add(btnDrink);
        conntainerCategoryMenuBtn.getChildren().add(btnFried);

        return conntainerCategoryMenuBtn;
    } // getMenuSelectionContainer()方法

    // 前述三個類別按鈕可以呼叫以下事件
    public void select_category_menu(ActionEvent event) {
        String category = ((Button) event.getSource()).getText();
        menuContainerPane.getChildren().clear();//先刪除原有的窗格再加入新的類別窗格
        switch (category) {
            case "麵食":
                menuContainerPane.getChildren().add(menuJuice);
                break;
            case "飲品":
                menuContainerPane.getChildren().add(menuTea);
                break;
            case "炸物":
                menuContainerPane.getChildren().add(menuCoffee);
                break;
            default:
                break;
        }

    }//select_category_menu

    //====表格新增項目刪除項目之操作區塊
    public TilePane getOrderOperationContainer() {

        //========================
        //新增項目、刪除項目之操作區塊------------------------
        //定義新增一筆按鈕
        Button btnAdd = new Button();
        btnAdd.setText("新增一筆項目");
        btnAdd.getStyleClass().setAll("button", "primary");//設定css
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //新增一筆
                order_list.add(new OrderDetail("p-109", "新增的麵食", 30, 1));
                System.out.println("新增一筆");
                //addToCart("p-j-101");
            }
        });

        //定義刪除一筆按鈕，使用lambda寫法
        Button btnDelete = new Button("刪除一筆");
        btnDelete.getStyleClass().setAll("button", "danger");//設定css
        btnDelete.setOnAction((ActionEvent event) -> {
            //從table中取得目前被選到的項目
            OrderDetail selectedItem = table.getSelectionModel().getSelectedItem();
            //從表格table中或是從order_list刪除這一筆，擇一進行
            //table.getItems().remove(selectedItem);
            order_list.remove(selectedItem);
            checkTotal();
            System.out.println("執行刪除訂單!");
            System.out.println(selectedItem.getProduct_name());
        });

        //定義結帳按鈕
        Button btnCheck = new Button("結帳");
        btnCheck.getStyleClass().setAll("button", "success");
        btnCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                checkSave();
            }
        });

        //放置任務功能按鈕的窗格容器-----------------
        TilePane operationBtnTile = new TilePane();
        //operationBtnTile.setAlignment(Pos.CENTER_LEFT);
        operationBtnTile.setPrefColumns(6);//寬度?
        operationBtnTile.setVgap(10);
        operationBtnTile.setHgap(10);

        //置放新增刪除按鈕到操作區塊---------------
        operationBtnTile.getChildren().add(btnAdd);
        operationBtnTile.getChildren().add(btnDelete);
        operationBtnTile.getChildren().add(btnCheck);

        //將操作區塊置放到根容器---------
        //root.getChildren().addAll(operationBtnTile);
        //=====================================
        return operationBtnTile;
    } //getOperationBtnTile()

    //####初始化所有元件與事件並將所有元件放入root
    public void initializeOrderTable() {

        //################################
        //訂單order_list初始化----------------------        
        //訂單陣列串列初始化FXCollections類別有很多靜態方法可以操作ObservableList的"訂單陣列串列"
        //可以這樣取得一個空的串列
        order_list = FXCollections.observableArrayList();
        //若已知有兩筆訂單可以這樣設定
        /*
        order_list = FXCollections.observableArrayList(
                new OrderDetail("p-101", "葡萄汁", 80, 3),
                new OrderDetail("p-102", "番茄汁", 70, 1)
        );
        
        //也可以這樣加入一筆訂單
        order_list.add(new OrderDetail("p-103", "西瓜汁", 80, 3));
        //checkTotal();
         */

        //表格talbe初始化----------------------   
        table = new TableView<>();
        table.setEditable(true); //表格允許修改
        table.setPrefHeight(300);

        //定義第一個欄位column"品名"，其值來自於Order物件的某個String變數
        TableColumn<OrderDetail, String> order_item_name = new TableColumn<>("品名");
        //置放哪個變數值?指定這個欄位 對應到OrderDetail的"product_name"實例變數值
        order_item_name.setCellValueFactory(new PropertyValueFactory("product_name"));

        //若要允許修改此欄位
        //order_item_name.setCellFactory(TextFieldTableCell.forTableColumn()    );
        order_item_name.setPrefWidth(100); //設定欄位寬度
        order_item_name.setMinWidth(100);
        //定義欄位column"價格"
        TableColumn<OrderDetail, Integer> order_item_price = new TableColumn<>("價格");
        order_item_price.setCellValueFactory(new PropertyValueFactory("product_price"));
        //定義欄位column"數量"
        TableColumn<OrderDetail, Integer> order_item_qty = new TableColumn<>("數量");
        order_item_qty.setCellValueFactory(new PropertyValueFactory("quantity"));

        //這個欄位值內容可以被修改，在表格內是以文字格式顯示
        //new IntegerStringConverter()是甚麼?
        //因為quantity是整數，因此須將使用者輸入的字串格式轉為整數，才能異動OrderDetail物件，否則會報錯!
        order_item_qty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        //傳統寫法: 內部匿名類別inner anonymous class(可用滑鼠左鍵自動切換各種寫法)
        //定義修改完成後驅動的事件，使用lambda函式寫法，用->符號，有以下三種寫法
        order_item_qty.setOnEditCommit(event -> {
            //order_item_qty.setOnEditCommit((var event) -> {
            //order_item_qty.setOnEditCommit((CellEditEvent<OrderDetail, Integer> event) -> {
            int row_num = event.getTablePosition().getRow();//哪一筆(編號)被修改
            int new_val = event.getNewValue(); //取得使用者輸入的新數值 (整數)
            OrderDetail target = event.getTableView().getItems().get(row_num); //取得該筆果汁的參考位址
            //修改成新的數值 該筆訂單存放於order_list
            target.setQuantity(new_val);

            //更新總價
            checkTotal();

            System.out.println("哪個產品被修改數量:" + order_list.get(row_num).getProduct_name()); //顯示修改後的數值
            System.out.println("數量被修改為:" + order_list.get(row_num).getQuantity()); //顯示修改後的數值
        });

        //表格內置放的資料來自於order_list，有多個項目，依據置放順序顯示
        table.setItems(order_list);

        //table也可以這樣加入一筆訂單
        //table.getItems().add(new OrderDetail("p-104", "奇異果汁", 50, 2));
        //checkTotal();
        //把3個欄位加入table中
        table.getColumns().addAll(order_item_name, order_item_price, order_item_qty);

        //表格最後一欄是空白，不要顯示!
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //###################################
    } //initializeOrderTable()

    //計算總金額
    public void checkTotal() {
        double total = 0;
        //將所有的訂單顯示在表格   計算總金額
        for (OrderDetail od : order_list) {
            //加總
            total += od.getProduct_price() * od.getQuantity();
        }

        //顯示總金額於display
        String totalmsg = String.format("%s %d\n", "總金額:", Math.round(total));
        display.setText(totalmsg);
    }

    //加入購物車 檢查是否有重複的飲料
    public void addToCart(String item_id) {

        boolean duplication = false;
        for (int i = 0; i < order_list.size(); i++) {
            if (order_list.get(i).getProduct_id().equals(item_id)) {
                int qty = order_list.get(i).getQuantity() + 1; //拿出qty並+1
                order_list.get(i).setQuantity(qty);
                duplication = true;
                table.refresh();
                checkTotal();
                System.out.println(item_id + "此筆已經加入購物車，數量+1");
                break;
            }
        }
        if (!duplication) { //若是新項目則新增這一筆
            OrderDetail new_ord = new OrderDetail(
                    item_id,
                    product_dict.get(item_id).getName(),
                    product_dict.get(item_id).getPrice(),
                    1);
            order_list.add(new_ord);
            System.out.println(item_id);

            checkTotal(); //更新總金額
        }
    }

    public VBox get_root_pane() {
        //菜單模組
        //根容器 所有的元件都放在裡面container，最後再放進布景中scene，布景再放進舞台中stage
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.getStylesheets().add("/css/bootstrap3.css");

        //塞入菜單選擇區塊
        root.getChildren().add(getMenuSelectionContainer());

        //取得菜單磁磚窗格並放入根容器
        menuContainerPane.getChildren().add(menuJuice); //在這裡將菜單窗格塞入飲料菜單
        root.getChildren().add(menuContainerPane);

        //===================
        //訂單輸入模組
        //塞入增加表格刪除項目操作之容器
        root.getChildren().add(getOrderOperationContainer());
        //塞入表格
        initializeOrderTable(); //表格要初始化
        root.getChildren().add(table);

        //塞入白板display
        display.setPrefColumnCount(8);
        root.getChildren().add(display);

        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {

        //取得 root pane
        VBox root = get_root_pane();
        //塞入布景
        Scene scene = new Scene(root);
        stage.setTitle("ミス日式麵食屋");
        stage.setScene(scene);
        stage.show();
    } // start()    

    //結帳存檔
    private void checkSave() {
//        // 顯示填寫客戶資訊的對話框
//        Optional<String[]> customerInfoResult = showCustomerInfoDialog();
//        customerInfoResult.ifPresent(data -> {
//            // 取得使用者填寫的客戶資訊
//            String customerName = data[0];
//            String customerPhone = data[1];
//            String customerAddress = data[2];
//        });

        // 計算總金額
        int Amount = 0;
        for (OrderDetail od : order_list) {
            Amount += od.getProduct_price() * od.getQuantity();
        }

        // 檢查總金額是否大於0
        if (Amount > 0) {
            display.setText("結帳中，列印發票...\n");

            //append_order_to_csv(); //將這一筆訂單附加儲存到檔案或資料庫
            //這裡要取得不重複的order_num編號
            String order_num = orderDao.getMaxOrderNum();

            if (order_num == null) {
                order_num = "ord-100";
            }

            System.out.println(order_num);
            System.out.println(order_num.split("-")[1]);

            //將現有訂單編號加上1
            int serial_num = Integer.parseInt(order_num.split("-")[1]) + 1;
            System.out.println(serial_num);

            //每家公司都有其訂單或產品的編號系統，這裡用ord-xxx表之
            String new_order_num = "ord-" + serial_num;

            //int sum = checkTotal();
            int total = 0;
            //將所有的訂單顯示在表格   計算總金額
            for (OrderDetail od : order_list) {
                //加總
                total += od.getProduct_price() * od.getQuantity();
            }

            Order crt = new Order();
            crt.setOrder_num(new_order_num);
            crt.setTotal_price(total);
            crt.setCustomer_name("無姓名");
            crt.setCustomer_phtone("無電話");
            crt.setCustomer_address("無地址");

            //寫入一筆訂單到資料庫
            orderDao.insertCart(crt);

            //逐筆寫入訂單明細
            for (int i = 0; i < order_list.size(); i++) {
                OrderDetail item = new OrderDetail();
                item.setOrder_num(new_order_num); //設定訂單編號
                item.setProduct_id(order_list.get(i).getProduct_id()); //設定產品編號
                item.setQuantity(order_list.get(i).getQuantity());//設定訂購數量 多少杯

                orderDao.insertOrderDetailItem(item);
            }

            order_list.clear();
        } else {
            display.setText("菜單中沒有任何產品!\n");
        }
    }

    // 儲存訂單資訊到資料庫
    private void saveOrderToDatabase(String customerName, String customerPhone, String customerAddress) {
        Order crt = new Order();

        crt.setCustomer_name(customerName);
        crt.setCustomer_phtone(customerPhone);
        crt.setCustomer_address(customerAddress);

        //寫入一筆訂單到資料庫
        orderDao.insertCart(crt);

        //逐筆寫入訂單明細
        for (int i = 0; i < order_list.size(); i++) {
            OrderDetail item = new OrderDetail();

            item.setProduct_id(order_list.get(i).getProduct_id()); //設定產品編號
            item.setQuantity(order_list.get(i).getQuantity());//設定訂購數量 多少杯

            orderDao.insertOrderDetailItem(item);
        }
    }

    // 顯示填寫客戶資訊的對話框
    private Optional<String[]> showCustomerInfoDialog() {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("填寫顧客資訊");
        dialog.setHeaderText("填寫顧客姓名、電話和地址");

        /// 設定對話框按鈕
        ButtonType confirmButtonType = new ButtonType("確定", ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);       

        
        // 建立表單元件
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // 姓名欄位
        TextField nameField = new TextField();
        nameField.setPromptText("姓名");

        // 電話欄位
        TextField phoneField = new TextField();
        phoneField.setPromptText("電話");

        // 地址欄位
        TextField addressField = new TextField();
        addressField.setPromptText("地址");

        // 將欄位加入表單
        grid.add(new Label("姓名:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("電話:"), 0, 1);
        grid.add(phoneField, 1, 1);
        grid.add(new Label("地址:"), 0, 2);
        grid.add(addressField, 1, 2);

        // 將表單加入對話框
        dialog.getDialogPane().setContent(grid);

        // 確認按鈕的事件處理
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return new String[]{nameField.getText(), phoneField.getText(), addressField.getText()};
            }
            return null;
        });

        // 顯示對話框並取得結果
        return dialog.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
