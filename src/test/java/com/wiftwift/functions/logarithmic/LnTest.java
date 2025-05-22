package com.wiftwift.functions.logarithmic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LnTest {
    
    private Ln ln;
    private final double DELTA = 1e-6;
    
    @Before
    public void setUp() {
        ln = new Ln(1e-10);
    }
    
    @Test
    public void testCommonValues() {
        assertEquals(0.0, ln.calculate(1), DELTA);
        assertEquals(Math.log(2), ln.calculate(2), DELTA);
        assertEquals(1.0, ln.calculate(Math.E), DELTA);
        assertEquals(Math.log(5), ln.calculate(5), DELTA);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testInvalidInput_Zero() {
        ln.calculate(0);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testInvalidInput_Negative() {
        ln.calculate(-1);
    }
    
    @Test
    public void testStabVsReal() {
        double[] testPoints = {0.2, 1, 2, 2.56578, 8, 10};
        
        for (double x : testPoints) {
            double realValue = ln.calculate(x);
            
            ln.enableStub();
            double stubValue = ln.calculate(x);
            ln.disableStub();

            System.out.printf("%f %f %f",realValue, stubValue, x);
            
            assertEquals("Ln stub and real calculation should match for x=" + x, 
                        realValue, stubValue, DELTA);
        }
    }
    
    @Test
    public void testLargeValues() {
        assertEquals(Math.log(100), ln.calculate(100), DELTA);
        assertEquals(Math.log(1000), ln.calculate(1000), DELTA);
    }
    
    @Test
    public void testSmallValues() {
        assertEquals(Math.log(0.1), ln.calculate(0.1), DELTA);
        assertEquals(Math.log(0.01), ln.calculate(0.01), DELTA);
        assertEquals(Math.log(0.001), ln.calculate(0.001), DELTA);
    }
}
