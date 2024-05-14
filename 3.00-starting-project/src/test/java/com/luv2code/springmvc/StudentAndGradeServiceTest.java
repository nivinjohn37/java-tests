package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.repository.HistoryGradeDao;
import com.luv2code.springmvc.repository.MathGradeDao;
import com.luv2code.springmvc.repository.ScienceGradeDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {
    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MathGradeDao mathGradeDao;

    @Autowired
    private ScienceGradeDao scienceGradeDao;

    @Autowired
    private HistoryGradeDao historyGradeDao;

    @Value("${sql.scripts.create.student}")
    private String sqlAddStudent;

    @Value("${sql.scripts.create.math.grade}")
    private String sqlAddMathStudent;

    @Value("${sql.scripts.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.scripts.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.scripts.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.scripts.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.scripts.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.scripts.delete.history.grade}")
    private String sqlDeleteHistoryGrade;
    @BeforeEach
    public void setUpDatabase() {
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathStudent);
        jdbcTemplate.execute(sqlAddHistoryGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
    }

    @Test
    public void createStudentService() {
        studentService.createStudent("One", "Test", "one.test@email.com");
        CollegeStudent student = studentDao.findByEmailAddress("one.test@email.com");
        assertEquals("one.test@email.com", student.getEmailAddress(), "find by email");
    }

    @Test
    public void isStudentNullTest() {
        assertTrue(studentService.isStudentNotNull(2));
        CollegeStudent student = studentDao.findByEmailAddress("two.test@email.com");
        assertEquals("two.test@email.com", student.getEmailAddress(), "find by email");
        assertFalse(studentService.isStudentNotNull(0));
    }

    @Test
    public void deleteStudentTest() {
        Optional<CollegeStudent> deletedStudent = studentDao.findById(2);
        Optional<MathGrade> deletedMathGrade = mathGradeDao.findById(1);
        Optional<HistoryGrade> deletedHistoryGrade = historyGradeDao.findById(1);
        Optional<ScienceGrade> deletedScienceGrade = scienceGradeDao.findById(1);

        assertTrue(deletedStudent.isPresent());

        assertTrue(deletedMathGrade.isPresent());
        assertTrue(deletedHistoryGrade.isPresent());
        assertTrue(deletedScienceGrade.isPresent());

        studentService.deleteStudent(2);
        deletedStudent = studentDao.findById(2);
        deletedMathGrade = mathGradeDao.findById(1);
        deletedHistoryGrade = historyGradeDao.findById(1);
        deletedScienceGrade = scienceGradeDao.findById(1);

        assertFalse(deletedMathGrade.isPresent());
        assertFalse(deletedHistoryGrade.isPresent());
        assertFalse(deletedScienceGrade.isPresent());
        assertFalse(deletedStudent.isPresent(), "return false");
    }

    @Test
    public void getGradeBookServiceTest() {
        Iterable<CollegeStudent> iterableStudents = studentService.getGradebook();
        List<CollegeStudent> studentsList = new ArrayList<>();
        for (CollegeStudent student : iterableStudents) {
            studentsList.add(student);
        }
        assertEquals(1, studentsList.size());
    }

    @Sql("/insertData.sql")
    @Test
    public void getGradeBookServiceSqlTest() {
        Iterable<CollegeStudent> iterableStudents = studentService.getGradebook();
        List<CollegeStudent> studentsList = new ArrayList<>();
        for (CollegeStudent student : iterableStudents) {
            studentsList.add(student);
        }
        assertEquals(8, studentsList.size());
    }

    @Test
    public void createGradeService() {
        //create the grade
        assertTrue(studentService.createGrade(80.50, 2, "math"));
        assertTrue(studentService.createGrade(80.50, 2, "science"));
        assertTrue(studentService.createGrade(80.50, 2, "history"));

        //Get all grades with studentId
        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(2);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(2);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(2);

        //Verify there is grades
        assertTrue(mathGrades.iterator().hasNext(), "Student has math grades");
        assertTrue(scienceGrades.iterator().hasNext(), "Student has science grades");
        assertTrue(historyGrades.iterator().hasNext(), "Student has history grades");

        //Verify the size of the grades
        assertEquals(2, ((Collection<MathGrade>) mathGrades).size(), "Student has math grades");
        assertEquals(2, ((Collection<ScienceGrade>) scienceGrades).size(), "Student has science grades");
        assertEquals(2, ((Collection<HistoryGrade>) historyGrades).size(), "Student has math grades");

    }

    @Test
    public void createGradeServiceReturnFalse() {
        assertFalse(studentService.createGrade(104.2, 2, "math"));
        assertFalse(studentService.createGrade(-2.1, 2, "math"));
        assertFalse(studentService.createGrade(24.2, 12, "math"), "valid student found");
        assertFalse(studentService.createGrade(24.2, 2, "english"));
    }

    @Test
    public void deleteGradeServiceTest() {
        assertEquals(2, studentService.deleteGrade(1, "math"), "Returns student id after delete");
        assertEquals(2, studentService.deleteGrade(1, "science"), "Returns student id after delete");
        assertEquals(2, studentService.deleteGrade(1, "history"), "Returns student id after delete");
    }

    @Test
    public void studentInformation() {
        GradebookCollegeStudent gradebookCollegeStudent
                = studentService.getStudentInformation(2);
        assertNotNull(gradebookCollegeStudent);
        assertEquals(2, gradebookCollegeStudent.getId());
        assertEquals("Two", gradebookCollegeStudent.getFirstname());
        assertEquals(1, gradebookCollegeStudent
                .getStudentGrades().getHistoryGradeResults().size());
        assertEquals(1, gradebookCollegeStudent
                .getStudentGrades().getScienceGradeResults().size());
        assertEquals(1, gradebookCollegeStudent
                .getStudentGrades().getMathGradeResults().size());
    }

    @Test
    public void studentInformatioServiceReturnNull() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.getStudentInformation(0);
        assertNull(gradebookCollegeStudent);
    }

    @AfterEach
    public void setAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
    }
}
