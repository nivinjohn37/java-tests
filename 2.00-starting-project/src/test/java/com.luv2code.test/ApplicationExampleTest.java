package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {
    private static int count = 0;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDesc;

    @Value("${info.app.version}}}")
    private String appVersion;

    @Value("${info.app.name}")
    private String schoolName;

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void beforeEach() {
        count = count + 1;
        String out1 = String.format("Testing: %s which is %s Version: %s .Execution of test method %d",
                appInfo, appDesc, appVersion, count);
        System.out.println(out1);

        student.setFirstname("Test");
        student.setLastname("One");
        student.setEmailAddress("test_one@email.com");
        studentGrades.setMathGradeResults((List.of(100.0, 85.2, 76.4, 92.1)));
        student.setStudentGrades(studentGrades);
        System.out.println(student);

    }


    @DisplayName("Add grade results for student grades")
    @Test
    public void addGradeResultsForStudentGrades() {
        assertEquals(BigDecimal.valueOf(353.7).setScale(2, RoundingMode.HALF_EVEN), BigDecimal.valueOf(studentGrades.addGradeResultsForSingleClass
                (student.getStudentGrades().getMathGradeResults())).setScale(2, RoundingMode.HALF_EVEN)
        );
    }

    @DisplayName("Add grade results for student grades not equal")
    @Test
    public void addGradeResultsForStudentGradesNotEqual() {
        assertNotEquals(BigDecimal.valueOf(353).setScale(2, RoundingMode.HALF_EVEN), BigDecimal.valueOf(studentGrades.addGradeResultsForSingleClass
                (student.getStudentGrades().getMathGradeResults())).setScale(2, RoundingMode.HALF_EVEN)
        );
    }

    @DisplayName("Create student without grade init")
    @Test
    public void createStudentWithoutGradesInit(){
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("Test");
        studentTwo.setLastname("Two");
        studentTwo.setEmailAddress("test.two@email.com");
        assertNotNull(studentTwo.getFirstname());
        assertNotNull(studentTwo.getLastname());
        assertNotNull(studentTwo.getEmailAddress());
        assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
    }

    @DisplayName("Verify students are prototypes")
    @Test
    public void verifyStudentsArePrototypes(){
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        assertNotSame(student, studentTwo);
    }

}
