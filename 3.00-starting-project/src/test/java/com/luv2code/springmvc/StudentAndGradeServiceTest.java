package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {
    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUpDatabase() {
        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address)" +
                " values (2, 'Two', 'Test', 'two.test@email.com') ");
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
        assertTrue(deletedStudent.isPresent());
        studentService.deleteStudent(2);
        deletedStudent = studentDao.findById(2);
        assertFalse(deletedStudent.isPresent(), "return false");
    }

    @AfterEach
    public void setAfterTransaction(){
        jdbcTemplate.execute("delete from student");
    }
}
