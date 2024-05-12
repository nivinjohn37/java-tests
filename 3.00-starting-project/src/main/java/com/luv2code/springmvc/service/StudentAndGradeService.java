package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Grade;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.models.StudentGrades;
import com.luv2code.springmvc.repository.HistoryGradeDao;
import com.luv2code.springmvc.repository.MathGradeDao;
import com.luv2code.springmvc.repository.ScienceGradeDao;
import com.luv2code.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    @Autowired
    StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    @Autowired
    MathGradeDao mathGradeDao;

    @Autowired
    ScienceGradeDao scienceGradeDao;

    @Autowired
    HistoryGradeDao historyGradeDao;

    @Autowired
    StudentGrades studentGrades;

    public void createStudent(String firstName, String lastName, String email) {
        CollegeStudent student = new CollegeStudent(firstName, lastName, email);
        studentDao.save(student);
    }

    public boolean isStudentNotNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        if (student.isPresent()) {
            return true;
        }
        return false;
    }

    public void deleteStudent(int id) {
        if (isStudentNotNull(id)) {
            studentDao.deleteById(id);
            mathGradeDao.deleteByStudentId(id);
            scienceGradeDao.deleteByStudentId(id);
            historyGradeDao.deleteByStudentId(id);
        }
    }

    public Iterable<CollegeStudent> getGradebook() {
        return studentDao.findAll();
    }

    public boolean createGrade(double grade, int id, String gradeType) {
        if (isStudentNotNull(id)) {
            if (grade >= 0 && grade <= 100) {
                if (gradeType.equals("math")) {
                    mathGrade.setId(0);
                    mathGrade.setGrade(grade);
                    mathGrade.setStudentId(id);
                    mathGradeDao.save(mathGrade);
                    return true;
                } else if (gradeType.equals("science")) {
                    scienceGrade.setId(0);
                    scienceGrade.setGrade(grade);
                    scienceGrade.setStudentId(id);
                    scienceGradeDao.save(scienceGrade);
                    return true;
                } else if (gradeType.equals("history")) {
                    historyGrade.setId(0);
                    historyGrade.setGrade(grade);
                    historyGrade.setStudentId(id);
                    historyGradeDao.save(historyGrade);
                    return true;
                }
            }
        }
        return false;
    }

    public int deleteGrade(int id, String gradeType) {
        int studentId = 0;

        if (gradeType.equals("math")) {
            Optional<MathGrade> grade = mathGradeDao.findById(id);
            if (grade.isEmpty()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            mathGradeDao.deleteById(id);
        }else if (gradeType.equals("science")) {
            Optional<ScienceGrade> grade = scienceGradeDao.findById(id);
            if (grade.isEmpty()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            scienceGradeDao.deleteById(id);
        }else if (gradeType.equals("history")) {
            Optional<HistoryGrade> grade = historyGradeDao.findById(id);
            if (grade.isEmpty()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            historyGradeDao.deleteById(id);
        }
        return studentId;
    }

    public GradebookCollegeStudent getStudentInformation(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(id);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(id);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(id);

        List<Grade> mathGradesList = new ArrayList<>();
        mathGrades.forEach(mathGradesList :: add);
        List<Grade> scienceGradesList = new ArrayList<>();
        scienceGrades.forEach(scienceGradesList :: add);
        List<Grade> historyGradesList = new ArrayList<>();
        historyGrades.forEach(historyGradesList :: add);

        studentGrades.setHistoryGradeResults(historyGradesList);
        studentGrades.setMathGradeResults(mathGradesList);
        studentGrades.setScienceGradeResults(scienceGradesList);

        return new GradebookCollegeStudent(student.get().getId(),
            student.get().getFirstname(), student.get().getLastname(),
            student.get().getEmailAddress(), studentGrades);
    }
}
