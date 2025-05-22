package com.wiftwift.functions.trigonometric;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TanTest {
    
    private Sin sinReal;
    private Sin sinStub;
    private Cos cosReal;
    private Cos cosStub;
    private Tan tanWithRealFunctions;
    private Tan tanWithStubFunctions;
    private final double DELTA = 1e-6;
    
    @Before
    public void setUp() {
        sinReal = new Sin(1e-10);
        sinStub = new Sin(1e-10);
        sinStub.enableStub();
        
        cosReal = new Cos(1e-10, sinReal);
        cosStub = new Cos(1e-10, sinStub);
        cosStub.enableStub();
        
        tanWithRealFunctions = new Tan(1e-10, sinReal, cosReal);
        tanWithStubFunctions = new Tan(1e-10, sinStub, cosStub);
    }
    
    @Test
    public void testCommonValues() {
        assertEquals(0.0, tanWithRealFunctions.calculate(0), DELTA);
        assertEquals(1 / Math.sqrt(3), tanWithRealFunctions.calculate(Math.PI / 6), DELTA);
        assertEquals(1.0, tanWithRealFunctions.calculate(Math.PI / 4), DELTA);
        assertEquals(Math.sqrt(3), tanWithRealFunctions.calculate(Math.PI / 3), DELTA);
        assertEquals(0.0, tanWithRealFunctions.calculate(Math.PI), DELTA);
        assertEquals(0.0, tanWithRealFunctions.calculate(2 * Math.PI), DELTA);
    }
    
    @Test
    public void testCommonValuesWithStub() {
        assertEquals(0.0, tanWithStubFunctions.calculate(0), DELTA);
        assertEquals(1 / Math.sqrt(3), tanWithStubFunctions.calculate(Math.PI / 6), DELTA);
        assertEquals(1.0, tanWithStubFunctions.calculate(Math.PI / 4), DELTA);
        assertEquals(Math.sqrt(3), tanWithStubFunctions.calculate(Math.PI / 3), DELTA);
        assertEquals(0.0, tanWithStubFunctions.calculate(Math.PI), DELTA);
        assertEquals(0.0, tanWithStubFunctions.calculate(2 * Math.PI), DELTA);
    }
    
    @Test
    public void testRealVsStub() {
        double[] testPoints = {-1.19352, -2.45128, -5.69378, -7.4767, -8.73447, -5, -6, -2, 
                              2.56578, 2, 0.2, 1, 8, 10};
        
        for (double x : testPoints) {
            try {
                double realResult = tanWithRealFunctions.calculate(x);
                double stubResult = tanWithStubFunctions.calculate(x);
                
                assertEquals("Tan should give same result with real and stub functions for x=" + x,
                            realResult, stubResult, DELTA);
            } catch (ArithmeticException e) {
                System.out.println("Skipping point x=" + x + " (near singularity)");
            }
        }
    }
    
    @Test(expected = ArithmeticException.class)
    public void testSingularityAtPiOverTwo() {
        tanWithRealFunctions.calculate(Math.PI / 2);
    }
}
