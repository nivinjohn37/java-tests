package com.luv2code.junitdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DemoUtilsTest {
    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
        System.out.println("Before Each executes before each test method");
    }

    @Test
    void addTest() {
        assertEquals(4, demoUtils.add(1, 3), "1+3 = 4");
    }

    @Test
    void testObjectNull() {
        String test = null;
        assertNull(demoUtils.checkNull(null));
    }
}