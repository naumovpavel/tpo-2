package com.wiftwift.functions.logarithmic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Log2Test {
    
    private Ln lnReal;
    private Ln lnStub;
    private Log2 log2WithRealLn;
    private Log2 log2WithStubLn;
    private final double DELTA = 1e-6;
    
    @Before
    public void setUp() {
        lnReal = new Ln(1e-10);
        lnStub = new Ln(1e-10);
        lnStub.enableStub();
        
        log2WithRealLn = new Log2(1e-10, lnReal);
        log2WithStubLn = new Log2(1e-10, lnStub);
    }
    
    @Test
    public void testCommonValues() {
        assertEquals(0.0, log2WithRealLn.calculate(1), DELTA);
        assertEquals(1.0, log2WithRealLn.calculate(2), DELTA);
        assertEquals(2.0, log2WithRealLn.calculate(4), DELTA);
        assertEquals(3.0, log2WithRealLn.calculate(8), DELTA);
    }
    
    @Test
    public void testCommonValuesWithStub() {
        assertEquals(0.0, log2WithStubLn.calculate(1), DELTA);
        assertEquals(1.0, log2WithStubLn.calculate(2), DELTA);
        assertEquals(2.0, log2WithStubLn.calculate(4), DELTA);
        assertEquals(3.0, log2WithStubLn.calculate(8), DELTA);
    }
    
    @Test
    public void testRealVsStub() {
        double[] testPoints = {0.2, 1, 2, 2.56578, 8, 10};
        
        for (double x : testPoints) {
            double realResult = log2WithRealLn.calculate(x);
            double stubResult = log2WithStubLn.calculate(x);
            
            assertEquals("Log2 should give same result with real and stub ln for x=" + x,
                        realResult, stubResult, DELTA);
        }
    }
    
    @Test(expected = ArithmeticException.class)
    public void testInvalidInput_Zero() {
        log2WithRealLn.calculate(0);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testInvalidInput_Negative() {
        log2WithRealLn.calculate(-1);
    }
}
