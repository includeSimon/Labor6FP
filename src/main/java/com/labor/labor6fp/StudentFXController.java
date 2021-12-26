package com.labor.labor6fp;

import com.labor.labor6fp.Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentFXController implements Initializable {
    //configure student table from log in window
    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student, Integer> id;
    @FXML private TableColumn<Student, String> firstName;
    @FXML private TableColumn<Student, String> lastName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set up the columns in the table
        id.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<Student,String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Student,String>("lastName"));

        //load data
        tableView.setItems(getStudents());
    }

    /**
     * This method will return an ObservableList of Student objects
     */
    public ObservableList<Student> getStudents(){
        ObservableList<Student> student  = FXCollections.observableArrayList();
        //student.add()
        return student;
    }
}
