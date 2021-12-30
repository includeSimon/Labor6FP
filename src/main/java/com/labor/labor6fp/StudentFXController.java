package com.labor.labor6fp;

import com.labor.labor6fp.Exceptions.InputException;
import com.labor.labor6fp.Exceptions.NullException;
import com.labor.labor6fp.Model.*;
import com.labor.labor6fp.Repository.CourseJdbcRepository;
import com.labor.labor6fp.Repository.CourseStudentJdbc;
import com.labor.labor6fp.Repository.StudentJdbcRepository;
import com.labor.labor6fp.Repository.TeacherJdbcRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class StudentFXController implements  Initializable{
    //student and repository declarations
    private Student currentStudent;
    private CourseJdbcRepository courseRepo;
    private CourseStudentJdbc enrolledStudents;
    private StudentJdbcRepository studentRepo;

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

    //choicebox elements
    @FXML private ChoiceBox choiceBox;
    @FXML private Button choiceBoxButtonSelect;
    @FXML private Button choiceBoxButtonEnroll;
    @FXML private Button choiceBoxButtonUnenroll;
    @FXML private Label choiceBoxLabel;

    //constructor
    public StudentFXController() throws SQLException {
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

    /**
     * this method takes the student information from login controller and sets it in new scene
     *
     * @param student student chosen in login window
     */
    public void initData(Student student, CourseJdbcRepository courseRepo , StudentJdbcRepository studentRepo, TeacherJdbcRepository teacherRepo, CourseStudentJdbc enrolledStudents) throws SQLException, InputException, NullException {
        currentStudent = student;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrolledStudents = enrolledStudents;
        studentId.setText(Integer.toString(currentStudent.getId()));
        firstName.setText(currentStudent.getFirstName());
        lastName.setText(currentStudent.getLastName());
        credits.setText(Integer.toString(currentStudent.getTotalCredits()));

        //we need to load the data after the student has been received
        loadTableData();
        loadChoiceBoxData();
    }

    /**
     * Loads the data into the table view
     */
    public void loadTableData(){
        try {
            tableView.setItems(getCourses());
        } catch (SQLException | InputException | NullException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads data(courses) into the choiceBox
     */
    public void loadChoiceBoxData() throws SQLException, InputException, NullException {
        List<Course> courseList = courseRepo.findAll();

        //add course to choicebox only if student is not enrolled in course
        for (Course course : courseList)
            //if (enrolledStudents.findOne(course.getId(),currentStudent.getId()) == -1)
                choiceBox.getItems().add(Integer.toString(course.getId()));
    }

    private ObservableList<Course> getCourses() throws SQLException, InputException, NullException {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        List<Course> courseList = courseRepo.findAll();

        //searching each course and checking if student is enrolled
        int creditsSum = 0;
        for (Course course  : courseList)
            if (enrolledStudents.findOne(course.getId(),currentStudent.getId()) != -1){ //if current student is enrolled in this course
                course.setEnrolled("Yes");
                creditsSum += course.getCredits();
            }
            else course.setEnrolled("No");

         //after credits have been calculated, assign them to label and student
        currentStudent.setTotalCredits(creditsSum);


        updateStudent();

        credits.setText(Integer.toString(creditsSum));

        //adding courses to table
        for (Course course : courseList)
            courses.add(course);

        return courses;
    }

    /**
     * modifies student repository by updating student
     * @throws SQLException
     * @throws InputException
     * @throws NullException
     */
    public void updateStudent() throws SQLException, InputException, NullException {
        studentRepo.update(currentStudent);
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();
    }

    /**
     * Shows course name in choiceBoxLabel by using course id from choiceBox
     * @throws SQLException
     * @throws InputException
     * @throws NullException
     */
    public void choiceSelectButtonPushed() throws SQLException, InputException, NullException {
        choiceBoxLabel.setText("Course selected: " + courseRepo.findOne(Integer.valueOf(choiceBox.getValue().toString())).getName());
    }

    /**
     * Enrolls current student to selected course from choiceBox
     */
    public void choiceEnrollButtonPushed() throws SQLException, NullException {
        int courseId = Integer.parseInt(choiceBox.getValue().toString());
        enrolledStudents.save(courseId,currentStudent.getId());
    }

    /**
     * Unenrolls current student from selected course in choiceBox
     */
    public void choiceUnenrollButtonPushed() throws SQLException, NullException, InputException {
        int choiceBoxCourseId = Integer.parseInt(choiceBox.getValue().toString());
        int pairId = enrolledStudents.findOne(choiceBoxCourseId,currentStudent.getId());

        //delete pair from enrolled Students
        enrolledStudents.delete(pairId);

        //modify student credits
        int remainingCredits = currentStudent.getTotalCredits() - courseRepo.findOne(choiceBoxCourseId).getCredits();
        currentStudent.setTotalCredits(remainingCredits);

        //update student
        studentRepo.update(currentStudent);
    }
}

