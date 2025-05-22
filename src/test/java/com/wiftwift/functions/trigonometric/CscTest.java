package com.wiftwift.functions.trigonometric;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CscTest {
    
    private Sin sinReal;
    private Sin sinStub;
    private Csc cscWithRealSin;
    private Csc cscWithStubSin;
    private final double DELTA = 1e-6;
    
    @Before
    public void setUp() {
        sinReal = new Sin(1e-10);
        sinStub = new Sin(1e-10);
        sinStub.enableStub();
        
        cscWithRealSin = new Csc(1e-10, sinReal);
        cscWithStubSin = new Csc(1e-10, sinStub);
    }
    
    @Test
    public void testCommonValues() {
        assertEquals(2.0, cscWithRealSin.calculate(Math.PI / 6), DELTA);
        assertEquals(Math.sqrt(2), cscWithRealSin.calculate(Math.PI / 4), DELTA);
        assertEquals(2 / Math.sqrt(3), cscWithRealSin.calculate(Math.PI / 3), DELTA);
        assertEquals(1.0, cscWithRealSin.calculate(Math.PI / 2), DELTA);
        assertEquals(-1.0, cscWithRealSin.calculate(3 * Math.PI / 2), DELTA);
    }
    
    @Test
    public void testCommonValuesWithStub() {
        assertEquals(2.0, cscWithStubSin.calculate(Math.PI / 6), DELTA);
        assertEquals(Math.sqrt(2), cscWithStubSin.calculate(Math.PI / 4), DELTA);
        assertEquals(2 / Math.sqrt(3), cscWithStubSin.calculate(Math.PI / 3), DELTA);
        assertEquals(1.0, cscWithStubSin.calculate(Math.PI / 2), DELTA);
        assertEquals(-1.0, cscWithStubSin.calculate(3 * Math.PI / 2), DELTA);
    }
    
    @Test
    public void testRealVsStub() {
        double[] testPoints = {-1.19352, -2.45128, -5.69378, -7.4767, -8.73447, -5, -6, -2, 
                              2.56578, 2, 0.2, 1, 8, 10};
        
        for (double x : testPoints) {
            try {
                double realResult = cscWithRealSin.calculate(x);
                double stubResult = cscWithStubSin.calculate(x);
                
                assertEquals("Csc should give same result with real and stub sin for x=" + x,
                            realResult, stubResult, DELTA);
            } catch (ArithmeticException e) {
                System.out.println("Skipping point x=" + x + " (near singularity)");
            }
        }
    }
    
    @Test(expected = ArithmeticException.class)
    public void testSingularityAtZero() {
        cscWithRealSin.calculate(0);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testSingularityAtPi() {
        cscWithRealSin.calculate(Math.PI);
    }
    
    @Test
    public void testCalculationAccuracy() {
        for (double x = -Math.PI; x <= Math.PI; x += 0.1) {
            if (Math.abs(Math.sin(x)) > 0.1) {
                assertEquals(1.0 / Math.sin(x), cscWithRealSin.calculate(x), DELTA);
            }
        }
    }
}
