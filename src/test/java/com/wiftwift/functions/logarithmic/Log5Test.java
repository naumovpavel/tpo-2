package com.wiftwift.functions.logarithmic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Log5Test {
    
    private Ln lnReal;
    private Ln lnStub;
    private Log5 log5WithRealLn;
    private Log5 log5WithStubLn;
    private final double DELTA = 1e-6;
    
    @Before
    public void setUp() {
        lnReal = new Ln(1e-10);
        lnStub = new Ln(1e-10);
        lnStub.enableStub();
        
        log5WithRealLn = new Log5(1e-10, lnReal);
        log5WithStubLn = new Log5(1e-10, lnStub);
    }
    
    @Test
    public void testCommonValues() {
        assertEquals(0.0, log5WithRealLn.calculate(1), DELTA);
        assertEquals(1.0, log5WithRealLn.calculate(5), DELTA);
        assertEquals(2.0, log5WithRealLn.calculate(25), DELTA);
        assertEquals(3.0, log5WithRealLn.calculate(125), DELTA);
    }
    
    @Test
    public void testCommonValuesWithStub() {
        assertEquals(0.0, log5WithStubLn.calculate(1), DELTA);
        assertEquals(1.0, log5WithStubLn.calculate(5), DELTA);
        assertEquals(2.0, log5WithStubLn.calculate(25), DELTA);
        assertEquals(3.0, log5WithStubLn.calculate(125), DELTA);
    }
    
    @Test
    public void testRealVsStub() {
        double[] testPoints = {0.2, 1, 2, 2.56578, 8, 10};
        
        for (double x : testPoints) {
            double realResult = log5WithRealLn.calculate(x);
            double stubResult = log5WithStubLn.calculate(x);
            
            assertEquals("Log5 should give same result with real and stub ln for x=" + x,
                        realResult, stubResult, DELTA);
        }
    }
    
    @Test(expected = ArithmeticException.class)
    public void testInvalidInput_Zero() {
        log5WithRealLn.calculate(0);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testInvalidInput_Negative() {
        log5WithRealLn.calculate(-1);
    }
}
