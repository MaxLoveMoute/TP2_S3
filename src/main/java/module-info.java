module org.example.tp2_s3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.tp2_s3 to javafx.fxml;
    exports org.example.tp2_s3;
}