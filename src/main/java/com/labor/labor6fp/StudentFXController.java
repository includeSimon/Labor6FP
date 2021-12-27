package com.labor.labor6fp;

import com.labor.labor6fp.Exceptions.InputException;
import com.labor.labor6fp.Exceptions.NullException;
import com.labor.labor6fp.Model.Student;
import com.labor.labor6fp.Repository.StudentJdbcRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentFXController {
    private Student currentStudent;

    @FXML private Label id;
    @FXML private Label firstName;
    @FXML private Label lastName;

    public StudentFXController() throws SQLException {

    }

    public void initData(Student student){
        currentStudent = student;
        id.setText(Integer.toString(currentStudent.getId()));
        firstName.setText(currentStudent.getFirstName());
        lastName.setText(currentStudent.getLastName());
    }

}
