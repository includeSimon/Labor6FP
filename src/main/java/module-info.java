module com.labor.labor6fp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.labor.labor6fp to javafx.fxml;
    exports com.labor.labor6fp;
}