package com.wiftwift.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class CsvWriter {
    public static void writeFunction(String filename, String header, 
                                     double start, double end, 
                                     double step, Function<Double, Double> function) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(header + "\n");
            
            for (double x = start; x <= end; x += step) {
                try {
                    double y = function.apply(x);
                    writer.write(String.format("%.6f,%.6f\n", x, y));
                } catch (ArithmeticException e) {
                    writer.write(String.format("%.6f,undefined\n", x));
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
