package com.labor.labor6fp.Repository;

import com.labor.labor6fp.Exceptions.NullException;
import com.labor.labor6fp.Model.Course;

import java.util.List;
import java.util.UUID;

public class CourseRepository extends InMemoryRepository<Course>{
    public CourseRepository(List<Course> courseList) {

        super(courseList);
    }

    //returns the first occurence of course with the given id
    @Override
    public Course findOne(int id) {

        for(Course course : this.objectList)
        {
            if(course.getId() == id)
                return course;
        }

        return null;
    }

    //saves the course to repository if it doesn't exist, otherwise the course gets returned
    @Override
    public Course save(Course obj) {

        if (this.findOne(obj.getId()) != null)
            return obj;

        this.objectList.add(obj);
        return null;
    }

    //modifies a course
    @Override
    public Course update(Course obj) {

        //check if objects exists and return it if it does
        Course course = this.findOne(obj.getId());
        if (course == null)
            return obj;

        //remove old object and insert the new one
        this.objectList.remove(course);
        this.objectList.add(obj);
        return null;
    }

    //deletes a course with given id
    @Override
    public Course delete(int id) {

        //object does not exist
        if (this.findOne(id) == null)
            return null;

        //removing object with the given id and return it
        Course deletedCourseCopy  = this.findOne(id);
        this.objectList.remove(deletedCourseCopy);

        return deletedCourseCopy;
    }
}
