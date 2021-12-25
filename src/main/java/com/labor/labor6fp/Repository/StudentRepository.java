package com.labor.labor6fp.Repository;
import com.labor.labor6fp.Model.Student;

import java.util.List;

public class StudentRepository extends com.labor.labor6fp.Repository.InMemoryRepository<Student> {
    public StudentRepository(List<Student> studentList) {

        super(studentList);
    }


    @Override
    public Student findOne(int id){
        for(Student Student : this.objectList)
        {
            if(Student.getId() == id)
                return Student;
        }

        return null;
    }


    @Override
    public Student save(Student obj) {

        if (this.findOne(obj.getId()) != null)
            return obj;


        this.objectList.add(obj);
        return null;
    }


    @Override
    public Student update(Student obj) {

        //check if objects exists and return it if it does
        Student Student = this.findOne(obj.getId());
        if (Student == null)
            return obj;

        //remove old object and insert the new one
        this.objectList.remove(Student);
        this.objectList.add(obj);
        return null;
    }


    @Override
    public Student delete(int id) {

        //object does not exist
        if (this.findOne(id) == null)
            return null;

        //removing object with the given id and return it
        Student deletedStudentCopy  = this.findOne(id);
        this.objectList.remove(deletedStudentCopy);

        return deletedStudentCopy;
    }

}
