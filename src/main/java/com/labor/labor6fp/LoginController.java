package com.labor.labor6fp;

import com.labor.labor6fp.Exceptions.InputException;
import com.labor.labor6fp.Exceptions.NullException;
import com.labor.labor6fp.Repository.StudentJdbcRepository;
import com.labor.labor6fp.Repository.TeacherJdbcRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    private StudentJdbcRepository studentRepo;
    private TeacherJdbcRepository teacherRepo;
    @FXML
    private Label status;

    @FXML
    private TextField username;

    @FXML Button loginStudent;
    @FXML Button loginTeacher;

    public LoginController() throws SQLException {
        studentRepo = new StudentJdbcRepository();
        teacherRepo = new TeacherJdbcRepository();
    }

    @FXML
    protected void pressedLoginStudentButton(ActionEvent event) throws IOException, SQLException, InputException, NullException {
        String[] splitStr = username.getText().split("\\s+");   //split strig by space
        try {
            if (studentRepo.exists(splitStr[0], splitStr[1])) {
                status.setText("Login Success! Welcome " + splitStr[0] + " " + splitStr[1]);
                status.setTextFill(Color.GREEN);

                //custom loader declaration so that we can use student controller
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("StudentLogInWindow.fxml"));
                Scene studentWindow = new Scene(loader.load());
                StudentFXController controller = loader.getController();

                //send information to student controller
                controller.initData(studentRepo.findOne(studentRepo.findIdByName(splitStr[0], splitStr[1])));


                //get and change scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(studentWindow);
                stage.show();

            } else status.setText("Login failed!");
        } catch (Exception e){  //is a text that is not composed of two words and space is entered
            e.printStackTrace();
            status.setText("Login failed!");
            status.setTextFill(Color.RED);
        }
    }

    @FXML
    protected void pressedLoginTeacherButton(ActionEvent event) throws IOException, SQLException, InputException, NullException {
        String[] splitStr = username.getText().split("\\s+");   //split strig by space
        try {
            if (teacherRepo.exists(splitStr[0], splitStr[1])) {
                status.setText("Login Success! Welcome " + splitStr[0] + " " + splitStr[1]);
                status.setTextFill(Color.GREEN);

                //custom loader declaration so that we can use teacher controller
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("TeacherLogInWindow.fxml"));
                Scene studentWindow = new Scene(loader.load());
                TeacherFXController controller = loader.getController();

                //send information to student controller
                controller.initData(teacherRepo.findOne(teacherRepo.findIdByName(splitStr[0], splitStr[1])));


                //get and change scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(studentWindow);
                stage.show();

            } else status.setText("Login failed!");
        } catch (Exception e){  //is a text that is not composed of two words and space is entered
            e.printStackTrace();
            status.setText("Login failed!");
            status.setTextFill(Color.RED);
        }
    }
}