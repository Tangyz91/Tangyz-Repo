module mypos {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql; //資料庫
    exports mypos;
    exports mypos_db;
    exports mypos_integration;
    exports models;
}
