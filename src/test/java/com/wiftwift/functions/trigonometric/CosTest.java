package com.wiftwift.functions.trigonometric;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CosTest {
    
    private Sin sinReal;
    private Sin sinStub;
    private Cos cosWithRealSin;
    private Cos cosWithStubSin;
    private final double DELTA = 1e-6;
    
    @Before
    public void setUp() {
        sinReal = new Sin(1e-10);
        sinStub = new Sin(1e-10);
        sinStub.enableStub();
        
        cosWithRealSin = new Cos(1e-10, sinReal);
        cosWithStubSin = new Cos(1e-10, sinStub);
    }
    
    @Test
    public void testCommonValues() {
        assertEquals(1.0, cosWithRealSin.calculate(0), DELTA);
        assertEquals(Math.sqrt(3) / 2, cosWithRealSin.calculate(Math.PI / 6), DELTA);
        assertEquals(1 / Math.sqrt(2), cosWithRealSin.calculate(Math.PI / 4), DELTA);
        assertEquals(0.5, cosWithRealSin.calculate(Math.PI / 3), DELTA);
        assertEquals(0.0, cosWithRealSin.calculate(Math.PI / 2), DELTA);
        assertEquals(-1.0, cosWithRealSin.calculate(Math.PI), DELTA);
        assertEquals(0.0, cosWithRealSin.calculate(3 * Math.PI / 2), DELTA);
        assertEquals(1.0, cosWithRealSin.calculate(2 * Math.PI), DELTA);
    }
    
    @Test
    public void testCommonValuesWithStub() {
        assertEquals(1.0, cosWithStubSin.calculate(0), DELTA);
        assertEquals(Math.sqrt(3) / 2, cosWithStubSin.calculate(Math.PI / 6), DELTA);
        assertEquals(1 / Math.sqrt(2), cosWithStubSin.calculate(Math.PI / 4), DELTA);
        assertEquals(0.5, cosWithStubSin.calculate(Math.PI / 3), DELTA);
        assertEquals(0.0, cosWithStubSin.calculate(Math.PI / 2), DELTA);
        assertEquals(-1.0, cosWithStubSin.calculate(Math.PI), DELTA);
        assertEquals(0.0, cosWithStubSin.calculate(3 * Math.PI / 2), DELTA);
        assertEquals(1.0, cosWithStubSin.calculate(2 * Math.PI), DELTA);
    }
    
    @Test
    public void testRealVsStub() {
        double[] testPoints = {-1.19352, -2.45128, -5.69378, -7.4767, -8.73447, -5, -6, -2, 
                              2.56578, 2, 0.2, 1, 8, 10};
        
        for (double x : testPoints) {
            double realResult = cosWithRealSin.calculate(x);
            double stubResult = cosWithStubSin.calculate(x);
            
            assertEquals("Cos should give same result with real and stub sin for x=" + x,
                        realResult, stubResult, DELTA);
        }
    }
}
