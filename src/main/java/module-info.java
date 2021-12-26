module com.labor.labor6fp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.labor.labor6fp to javafx.fxml;
    exports com.labor.labor6fp;
}