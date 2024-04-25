package com.luv2code.junitdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
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
}