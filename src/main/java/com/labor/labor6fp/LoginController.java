package com.labor.labor6fp;

import com.labor.labor6fp.Exceptions.InputException;
import com.labor.labor6fp.Exceptions.NullException;
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
    protected void Login(ActionEvent event) throws IOException, SQLException, InputException, NullException {
        String[] splitStr = username.getText().split("\\s+");
        if (studentRepo.exists(splitStr[0],splitStr[1])){
            status.setText("Login Success! Welcome " + splitStr[0] + " " + splitStr[1]);
            status.setTextFill(Color.GREEN);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("StudentLogInWindow.fxml"));
            Scene studentWindow = new Scene(loader.load());
            StudentFXController controller = loader.getController();
            controller.initData(studentRepo.findOne(studentRepo.findIdByName(splitStr[0],splitStr[1])));

            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(studentWindow);
            stage.show();


            /*Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StudentLogInWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
*/

        }
        else status.setText("Login failed!");
    }
}