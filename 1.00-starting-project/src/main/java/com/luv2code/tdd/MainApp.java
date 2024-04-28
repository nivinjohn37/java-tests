package com.luv2code.tdd;

import java.util.stream.IntStream;

public class MainApp {
    public static void main(String[] args) {

        IntStream.range(1, 100).forEach(i -> System.out.println(FizzBuzz.compute(i)));
    }
}
