package com.wiftwift.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;

public class CsvWriterTest {
    
    private final String TEST_FILE = "test_output.csv";
    
    @Before
    public void setUp() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @After
    public void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    public void testWriteFunction() throws IOException {
        Function<Double, Double> function = x -> x * x;
        
        CsvWriter.writeFunction(TEST_FILE, "x,f(x)", -2.0, 2.0, 1.0, function);
        
        File file = new File(TEST_FILE);
        assertTrue(file.exists());
        
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        
        assertEquals("x,f(x)", lines.get(0));
        
        assertEquals(5 + 1, lines.size());
        
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            assertEquals(x * x, y, 1e-6);
        }
    }
    
    @Test
    public void testWriteFunctionWithUndefinedValues() throws IOException {
        Function<Double, Double> function = x -> {
            if (Math.abs(x) < 1e-10) {
                throw new ArithmeticException("Division by zero");
            }
            return 1.0 / x;
        };
        
        CsvWriter.writeFunction(TEST_FILE, "x,f(x)", -1.0, 1.0, 0.5, function);
        
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        
        assertEquals(5 + 1, lines.size());
        
        String[] parts = lines.get(3).split(",");
        assertEquals("0.000000", parts[0]);
        assertEquals("undefined", parts[1]);
    }
}
