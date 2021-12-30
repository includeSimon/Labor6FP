package com.labor.labor6fp;

import com.labor.labor6fp.Exceptions.InputException;
import com.labor.labor6fp.Exceptions.NullException;
import com.labor.labor6fp.Model.Course;
import com.labor.labor6fp.Model.Student;
import com.labor.labor6fp.Model.Teacher;
import com.labor.labor6fp.Repository.CourseJdbcRepository;
import com.labor.labor6fp.Repository.CourseStudentJdbc;
import com.labor.labor6fp.Repository.StudentJdbcRepository;
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
import java.util.ResourceBundle;

public class TeacherFXController implements Initializable {
    //controller variables
    private Teacher currentTeacher;
    private CourseJdbcRepository courseRepo;
    private CourseStudentJdbc enrolledStudents;
    private StudentJdbcRepository studentRepo;
    private int teacherCourseId;

    //tableView declarations
    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student,Integer> studentId;
    @FXML private TableColumn<Student,String> studentFirstName;
    @FXML private TableColumn<Student,String> studentLastName;
    @FXML private TableColumn<Student,Integer> studentCredits;

    //teacher information
    @FXML Label teacherId;
    @FXML Label teacherFirstName;
    @FXML Label teacherLastName;
    @FXML Label courseName;


    @FXML private Button backToLogin;

    public TeacherFXController() throws SQLException {
        courseRepo = new CourseJdbcRepository();
        enrolledStudents = new CourseStudentJdbc();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set up columns in table
        studentId.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
        studentFirstName.setCellValueFactory(new PropertyValueFactory<Student,String>("firstName"));
        studentLastName.setCellValueFactory(new PropertyValueFactory<Student,String>("lastName"));
        studentCredits.setCellValueFactory(new PropertyValueFactory<Student,Integer>("totalCredits"));
    }

    /**
     * this method takes the teacher information from login controller and sets it in new scene
     *
     * @param teacher teacher chosen in login window
     */
    public void initData(Teacher teacher, CourseJdbcRepository courseRepo,StudentJdbcRepository studentRepo, CourseStudentJdbc enrolledStudents) throws SQLException, InputException, NullException {
        currentTeacher = teacher;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrolledStudents = enrolledStudents;
        teacherId.setText(Integer.toString(currentTeacher.getId()));
        teacherFirstName.setText(currentTeacher.getFirstName());
        teacherLastName.setText(currentTeacher.getLastName());

        //find teacher course in course repo and set course name
        courseName.setText("Not found");
        for  (Course course : courseRepo.findAll())
            if (course.getTeacherId() == currentTeacher.getId()){
                courseName.setText(course.getName());
                teacherCourseId = course.getId();
                break;  //each teacher has only one course
            }

        //we need to load the data after the teacher has been received
        loadTableData();
    }

    private void loadTableData() {
        try {
            tableView.setItems(getStudents());
        } catch (SQLException | InputException | NullException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the students enrolled to teacher's course
     * @return
     */
    private ObservableList<Student> getStudents() throws SQLException, InputException, NullException {
        ObservableList<Student> students = FXCollections.observableArrayList();

        //for each student check if it has a pair in enrolledStudents table with current teacher id
        for (Student student : studentRepo.findAll())
            if (enrolledStudents.findOne(teacherCourseId, student.getId()) != -1)
                students.add(student);

        return students;
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();
    }
}
