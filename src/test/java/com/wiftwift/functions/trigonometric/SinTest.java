package com.wiftwift.functions.trigonometric;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SinTest {
    
    private Sin sin;
    private final double DELTA = 1e-6;
    
    @Before
    public void setUp() {
        sin = new Sin(1e-10);
    }
    
    @Test
    public void testCommonValues() {
        assertEquals(0.0, sin.calculate(0), DELTA);
        assertEquals(0.5, sin.calculate(Math.PI / 6), DELTA);
        assertEquals(1 / Math.sqrt(2), sin.calculate(Math.PI / 4), DELTA);
        assertEquals(Math.sqrt(3) / 2, sin.calculate(Math.PI / 3), DELTA);
        assertEquals(1.0, sin.calculate(Math.PI / 2), DELTA);
        assertEquals(0.0, sin.calculate(Math.PI), DELTA);
        assertEquals(-1.0, sin.calculate(3 * Math.PI / 2), DELTA);
        assertEquals(0.0, sin.calculate(2 * Math.PI), DELTA);
    }
    
    @Test
    public void testNegativeAngles() {
        assertEquals(0.0, sin.calculate(-0), DELTA);
        assertEquals(-0.5, sin.calculate(-Math.PI / 6), DELTA);
        assertEquals(-1 / Math.sqrt(2), sin.calculate(-Math.PI / 4), DELTA);
        assertEquals(-Math.sqrt(3) / 2, sin.calculate(-Math.PI / 3), DELTA);
        assertEquals(-1.0, sin.calculate(-Math.PI / 2), DELTA);
        assertEquals(0.0, sin.calculate(-Math.PI), DELTA);
        assertEquals(1.0, sin.calculate(-3 * Math.PI / 2), DELTA);
        assertEquals(0.0, sin.calculate(-2 * Math.PI), DELTA);
    }
    
    @Test
    public void testStabVsReal() {
        double[] testPoints = {-1.19352, -2.45128, -5.69378, -7.4767, -8.73447, -5, -6, -2, 
                              2.56578, 2, 0.2, 1, 8, 10};
        
        for (double x : testPoints) {
            double realValue = sin.calculate(x);
            
            sin.enableStub();
            double stubValue = sin.calculate(x);
            sin.disableStub();
            
            assertEquals("Sin stub and real calculation should match for x=" + x, 
                        realValue, stubValue, DELTA);
        }
    }
    
    @Test
    public void testPeriodicity() {
        for (double x = -10; x <= 10; x += 0.5) {
            assertEquals(sin.calculate(x), sin.calculate(x + 2 * Math.PI), DELTA);
        }
    }
    
    @Test
    public void testTaylorSeriesConvergence() {
        for (double x = -Math.PI; x <= Math.PI; x += 0.1) {
            assertEquals(Math.sin(x), sin.calculate(x), DELTA);
        }
    }
}
