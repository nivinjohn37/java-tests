package com.luv2code.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {
    //print all number from 1 to 100 and check fizzbuzz
    // if number is divisible by 3, print Fizz
    //if number is divisible by 5, print Buzz
    //if number is divisible by 3 and 5, print FizzBuzz
    //if not divisible by 3 or 5, return number

    @DisplayName("Divisible by Three")
    @Test
    @Order(1)
    void testDivisibleByThree() {
        String expected = "Fizz";
        assertEquals(expected, FizzBuzz.compute(3), "Should return Fizz");
    }

    @DisplayName("Divisible by Five")
    @Test
    @Order(2)
    void testDivisibleByFive() {
        String expected = "Buzz";
        assertEquals(expected, FizzBuzz.compute(5), "Should return Buzz");
    }

    @DisplayName("Divisible by Three and Five")
    @Test
    @Order(3)
    void testDivisibleByThreeAndFive() {
        String expected = "FizzBuzz";
        assertEquals(expected, FizzBuzz.compute(15), "Should return FizzBuzz");
    }

    @DisplayName("Not Divisible by Three or Five")
    @Test
    @Order(4)
    void testNotDivisibleByThreeOrFive() {
        String expected = "11";
        assertEquals(expected, FizzBuzz.compute(11), "Should return number");
    }


    @DisplayName("Not Divisible by Three or Five")
    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/small-test-data.csv")
    @Order(5)
    void testParamaterizedTest(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value), "Should return number");
    }

    @DisplayName("Check if numbers from 1 to 100 are fizzbuzzed!")
    @Test
    void testCheckIfNumbersFrom1To100AreFizzbuzzed() {
        assertDoesNotThrow(() -> {
            for (int i = 1; i <= 100; i++) {
                FizzBuzz.compute(i);
            }
        });
    }
}
