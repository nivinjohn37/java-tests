package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoUtilsTest {
    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
        System.out.println("Before Each executes before each test method");
    }

    @BeforeEach
    void setupAfterEach() {
        demoUtils = new DemoUtils();
        System.out.println("After Each executes after each test method");
    }

    @Test
        //@DisplayName("Test Add")
    void testAdd() {
        System.out.println("Test Add");
        assertEquals(4, demoUtils.add(1, 3), "1+3 = 4");
    }

    @Test
    @DisplayName("Test Multiply")
    void testMultiply() {
        assertEquals(12, demoUtils.multiply(4, 3), "4 * 3 = 12");
    }

    @Test
    @Order(1)
    void testObjectNull() {
        System.out.println("Test Null");
        String test = null;
        assertNull(demoUtils.checkNull(null));
    }

    @Test
    @DisplayName("same and true")
    void testObjectSame() {
        String academy = "Luv2Code Academy";
        assertTrue(academy.equals(demoUtils.getAcademy()));
        assertSame(academy, demoUtils.getAcademy());
    }

    @Test
    @DisplayName("greater than or less than")
    void testGreater() {
        int number = 1;
        assertFalse(demoUtils.isGreater(number, 2));
    }

    @Test
    @DisplayName("array test with order")
    void testArrayOrder(){
        List<String> testList = List.of("2", "luv", "code");
        assertIterableEquals(testList, demoUtils.getAcademyInList(), "List items should be equal");
    }

    @Test
    @DisplayName("test")
    void testArray(){
        List<String> testList = List.of("2", "luv", "code");
        assertTrue(testList.equals(demoUtils.getAcademyInList()), "List items should be contained");
    }

    @Test
    void testAssertIterableEquals() {
        List<String> actualList = List.of("banana", "cherry", "apple");
        assertIterableEquals(demoUtils.getFruitList(), actualList, "Lists should be equal regardless of order");
    }

    @Test
    void testAssertLinesMatch() {
        List<String> actualList = List.of("banana", "cherry", "apple");
        assertLinesMatch(demoUtils.getFruitList(), actualList, "Lines should match exactly");
    }

    @Test
    void whenAssertingEqualityListOfStrings_thenEqual() {
        List<String> expected = List.of("Java", "\\d+", "JUnit");
        List<String> actual = List.of("Java", "11", "JUnit");

        assertLinesMatch(expected, actual);
    }
    @Test
    @DisplayName("throw test")
    void testThrow(){
        assertThrows(Exception.class, () ->{demoUtils.throwException(-1);},  "value should be greater than 1");
        assertDoesNotThrow(() ->{demoUtils.throwException(9);},  "should not throw exception if value is greater than 1");

    }
}