package com.labor.labor6fp.Repository;
import com.labor.labor6fp.Model.Teacher;
import java.util.List;

public class TeacherRepository extends com.labor.labor6fp.Repository.InMemoryRepository<Teacher> {
    public TeacherRepository(List<Teacher> teacherList) {

        super(teacherList);
    }


    @Override
    public Teacher findOne(int id) {

        for(Teacher Teacher : this.objectList)
        {
            if(Teacher.getId() == id)
                return Teacher;
        }

        return null;
    }


    @Override
    public Teacher save(Teacher obj) {

        if (this.findOne(obj.getId()) != null)
            return obj;

        this.objectList.add(obj);
        return null;
    }


    @Override
    public Teacher update(Teacher obj) {

        //check if objects exists and return it if it does
        Teacher Teacher = this.findOne(obj.getId());
        if (Teacher == null)
            return obj;

        //remove old object and insert the new one
        this.objectList.remove(Teacher);
        this.objectList.add(obj);
        return null;
    }


    @Override
    public Teacher delete(int id) {

        //object does not exist
        if (this.findOne(id) == null)
            return null;

        //removing object with the given id and return it
        Teacher deletedTeacherCopy  = this.findOne(id);
        this.objectList.remove(deletedTeacherCopy);

        return deletedTeacherCopy;
    }
}
