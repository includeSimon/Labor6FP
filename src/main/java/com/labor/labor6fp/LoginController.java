package com.labor.labor6fp;

import com.labor.labor6fp.Model.Course;
import com.labor.labor6fp.Model.Student;
import com.labor.labor6fp.Model.Teacher;
import com.labor.labor6fp.Repository.CourseJdbcRepository;
import com.labor.labor6fp.Repository.StudentJdbcRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    private StudentJdbcRepository studentRepo;
    @FXML
    private Label status;

    @FXML
    private TextField username;

    public LoginController() throws SQLException {
        studentRepo = new StudentJdbcRepository();
    }

    @FXML
    protected void Login(ActionEvent event) throws IOException, SQLException {
        String[] splitStr = username.getText().split("\\s+");
          if (studentRepo.exists(splitStr[0],splitStr[1])){
            status.setText("Login Success! Welcome Mr. Potter");
            status.setTextFill(Color.GREEN);
//            Stage stage = new Stage();
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginStudents.fxml"));
//            Scene scene = new Scene(fxmlLoader.load());
//            stage.setTitle("Hello!");
//            stage.setScene(scene);
//            stage.show();
        }
        else status.setText("Login failed!");
    }
}