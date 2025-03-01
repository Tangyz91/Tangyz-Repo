module mis.easymath {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens mis.easymath to javafx.fxml;
    exports mis.easymath;
}
