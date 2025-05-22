package com.wiftwift.functions.logarithmic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Log3Test {
    
    private Ln lnReal;
    private Ln lnStub;
    private Log3 log3WithRealLn;
    private Log3 log3WithStubLn;
    private final double DELTA = 1e-6;
    
    @Before
    public void setUp() {
        lnReal = new Ln(1e-10);
        lnStub = new Ln(1e-10);
        lnStub.enableStub();
        
        log3WithRealLn = new Log3(1e-10, lnReal);
        log3WithStubLn = new Log3(1e-10, lnStub);
    }
    
    @Test
    public void testCommonValues() {
        assertEquals(0.0, log3WithRealLn.calculate(1), DELTA);
        assertEquals(1.0, log3WithRealLn.calculate(3), DELTA);
        assertEquals(2.0, log3WithRealLn.calculate(9), DELTA);
        assertEquals(3.0, log3WithRealLn.calculate(27), DELTA);
    }
    
    @Test
    public void testCommonValuesWithStub() {
        assertEquals(0.0, log3WithStubLn.calculate(1), DELTA);
        assertEquals(1.0, log3WithStubLn.calculate(3), DELTA);
        assertEquals(2.0, log3WithStubLn.calculate(9), DELTA);
        assertEquals(3.0, log3WithStubLn.calculate(27), DELTA);
    }
    
    @Test
    public void testRealVsStub() {
        double[] testPoints = {0.2, 1, 2, 2.56578, 8, 10};
        
        for (double x : testPoints) {
            double realResult = log3WithRealLn.calculate(x);
            double stubResult = log3WithStubLn.calculate(x);
            
            assertEquals("Log3 should give same result with real and stub ln for x=" + x,
                        realResult, stubResult, DELTA);
        }
    }
    
    @Test(expected = ArithmeticException.class)
    public void testInvalidInput_Zero() {
        log3WithRealLn.calculate(0);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testInvalidInput_Negative() {
        log3WithRealLn.calculate(-1);
    }
}
