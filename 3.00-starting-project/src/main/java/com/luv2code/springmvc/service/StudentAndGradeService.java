package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Student;
import com.luv2code.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    @Autowired
    StudentDao studentDao;

    public void createStudent(String firstName, String lastName, String email) {

    }

    public boolean isStudentNotNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        if(student.isPresent()) {
            return true;
        }
        return false;
    }

    public void deleteStudent(int id) {
        if(isStudentNotNull(id)){
            studentDao.deleteById(id);
        }
    }
}
