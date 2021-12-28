package com.labor.labor6fp;

import com.labor.labor6fp.Exceptions.InputException;
import com.labor.labor6fp.Exceptions.NullException;
import com.labor.labor6fp.Model.*;
import com.labor.labor6fp.Repository.CourseJdbcRepository;
import com.labor.labor6fp.Repository.CourseStudentJdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.ResourceBundle;

public class StudentFXController implements  Initializable{
    private Student currentStudent;
    private CourseJdbcRepository courseRepo;
    private CourseStudentJdbc enrolledStudents;

    @FXML private Label studentId;
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private Label credits;
    @FXML private Button backToLogin;

    //configure table
    @FXML private TableView<Course> tableView;
    @FXML private TableColumn<Course,Integer> courseId;
    @FXML private TableColumn<Course,String> courseName;
    @FXML private TableColumn<Course,Integer> teacherId;
    @FXML private TableColumn<Course,Integer> courseMaxEnrollment;
    @FXML private TableColumn<Course,Integer> courseCredits;

    //yes if student is enrolled in this course, no otherwise
    @FXML private TableColumn<Course,String> courseEnrolled;

    /**
     * this method takes the student information from login controller and sets it in new scene
     *
     * @param student student chosen in login window
     */
    public void initData(Student student){
        currentStudent = student;
        studentId.setText(Integer.toString(currentStudent.getId()));
        firstName.setText(currentStudent.getFirstName());
        lastName.setText(currentStudent.getLastName());
        credits.setText(Integer.toString(currentStudent.getTotalCredits()));

        //we need to load the data after the student has been received
        loadData();
    }


    public StudentFXController() throws SQLException {
        courseRepo = new CourseJdbcRepository();
        enrolledStudents = new CourseStudentJdbc();
    }

    /**
     * Loads the data into the table view
     */
    public void loadData(){
        try {
            tableView.setItems(getCourses());
        } catch (SQLException | InputException | NullException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set up the columns in table
        courseId.setCellValueFactory(new PropertyValueFactory<Course,Integer>("id"));
        courseName.setCellValueFactory(new PropertyValueFactory<Course,String>("name"));
        teacherId.setCellValueFactory(new PropertyValueFactory<Course,Integer>("teacherId"));
        courseMaxEnrollment.setCellValueFactory(new PropertyValueFactory<Course,Integer>("maxEnrollment"));
        courseCredits.setCellValueFactory(new PropertyValueFactory<Course,Integer>("credits"));
        courseEnrolled.setCellValueFactory(new PropertyValueFactory<Course,String>("enrolled"));

    }

    private ObservableList<Course> getCourses() throws SQLException, InputException, NullException {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        List<Course> courseList = courseRepo.findAll();

        //searching each course and checking if student is enrolled
        for (Course course  : courseList)
            if (enrolledStudents.findOne(course.getId(),currentStudent.getId()) != -1)
                course.setEnrolled("Yes");
            else course.setEnrolled("No");

        //adding courses to table
        for (Course course : courseList)
            courses.add(course);

        return courses;
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("LoginStudents.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();
    }
}
