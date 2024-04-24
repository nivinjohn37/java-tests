package com.luv2code.junitdemo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DemoUtilsTest {


    @Test
    void addTest() {
        DemoUtils demoUtils = new DemoUtils();
        assertEquals(4, demoUtils.add(1, 3), "1+3 = 4");
    }
}