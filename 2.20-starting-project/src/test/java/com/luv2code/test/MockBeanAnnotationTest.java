package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockBeanAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @MockBean
    private ApplicationDao applicationDao;

    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        studentOne.setFirstname("One");
        studentOne.setLastname("One");
        studentOne.setEmailAddress("one.test@email.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("When and Verify")
    @Test
    public void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass
                (studentGrades.getMathGradeResults())).thenReturn(100.0);
        assertEquals(100.0, applicationService.
                addGradeResultsForSingleClass(studentGrades.getMathGradeResults()));
        verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
        verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @DisplayName("Find GPA")
    @Test
    public void findGPATest() {
        when(applicationDao
                .findGradePointAverage
                        (studentGrades.getMathGradeResults())).thenReturn(88.2);
        assertEquals(88.2, applicationService.findGradePointAverage(studentGrades.getMathGradeResults()));
        verify(applicationDao, times(1)).findGradePointAverage(studentGrades.getMathGradeResults());
    }

    @DisplayName("Not Null")
    @Test
    public void notNullTest() {
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
        .thenReturn(true);
        assertNotNull(applicationService.checkNull(studentGrades.getMathGradeResults()));
    }

    @DisplayName("Throw runtime error")
    @Test
    public void throwRuntimeErrorTest() {
        CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);
        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);
        assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));
        verify(applicationDao, times(1)).checkNull(nullStudent);
    }

    @DisplayName("Multiple Stubbing")
    @Test
    public void stubbingConsecutiveCalls() {
        CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);
        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("No throw after the first time");
        assertThrows(RuntimeException.class, ()->{
            applicationService.checkNull(nullStudent);
        });
        assertEquals("No throw after the first time",
                applicationService.checkNull(nullStudent));
        assertEquals(String.class,
                applicationService.checkNull(nullStudent).getClass());
        verify(applicationDao, times(3)).checkNull(nullStudent);

    }


}
