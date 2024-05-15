package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Gradebook;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GradebookController {

    @Autowired
    private Gradebook gradebook;

    @Autowired
    private StudentAndGradeService studentAndGradeService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradebook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createStudent(@ModelAttribute("studend") CollegeStudent student, Model m) {
        studentAndGradeService.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradebook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    @RequestMapping(value = "/delete/student/{id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable int id, Model m) {
        if (studentAndGradeService.isStudentNotNull(id)) {
            studentAndGradeService.deleteStudent(id);
            Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradebook();
            m.addAttribute("students", collegeStudents);
            return "index";
        } else {
            return "error";
        }
    }


    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        if(!studentAndGradeService.isStudentNotNull(id)){
            return "error";
        }

        GradebookCollegeStudent studentEntity = studentAndGradeService.getStudentInformation(id);
        m.addAttribute("student", studentEntity);
        if(studentEntity.getStudentGrades().getMathGradeResults().size() > 0){
            m.addAttribute("mathAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getMathGradeResults()
            ));
        }else{
            m.addAttribute("mathAverage", "N/A");
        }

        if(studentEntity.getStudentGrades().getScienceGradeResults().size() > 0){
            m.addAttribute("scienceAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getScienceGradeResults()
            ));
        }else{
            m.addAttribute("scienceAverage", "N/A");
        }


        if(studentEntity.getStudentGrades().getHistoryGradeResults().size() > 0){
            m.addAttribute("historyAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getHistoryGradeResults()
            ));
        }else{
            m.addAttribute("historyAverage", "N/A");
        }
        return "studentInformation";
    }

}
