package com.labor.labor6fp;

import com.labor.labor6fp.Exceptions.InputException;
import com.labor.labor6fp.Exceptions.NullException;
import com.labor.labor6fp.Model.Course;
import com.labor.labor6fp.Model.Student;
import com.labor.labor6fp.Model.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeacherFXController implements Initializable {
    private Teacher currentTeacher;

    //tableView declarations
//    @FXML private TableView<Teacher> tableView;
//    @FXML private TableColumn<Teacher,Integer> teacherId;
//    @FXML private TableColumn<Teacher,String> teacherFirstName;
//    @FXML private TableColumn<Teacher,String> teacherLastName;

    //teacher information
    @FXML Label teacherId;
    @FXML Label teacherFirstName;
    @FXML Label teacherLastName;


    @FXML private Button backToLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        teacherId.setCellValueFactory(new PropertyValueFactory<Teacher,Integer>("id"));
//        teacherFirstName.setCellValueFactory(new PropertyValueFactory<Teacher,String>("firstName"));
//        teacherLastName.setCellValueFactory(new PropertyValueFactory<Teacher,String>("lastName"));
    }

    /**
     * this method takes the teacher information from login controller and sets it in new scene
     *
     * @param teacher teacher chosen in login window
     */
    public void initData(Teacher teacher) throws SQLException, InputException, NullException {
        currentTeacher = teacher;
        teacherId.setText(Integer.toString(currentTeacher.getId()));
        teacherFirstName.setText(currentTeacher.getFirstName());
        teacherLastName.setText(currentTeacher.getLastName());

        //we need to load the data after the teacher has been received
        //loadTableData();
        //loadChoiceBoxData();
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("LoginStudents.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();
    }
}
