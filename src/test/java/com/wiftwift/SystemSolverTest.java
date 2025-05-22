package com.wiftwift;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import com.wiftwift.functions.logarithmic.Ln;
import com.wiftwift.functions.logarithmic.Log2;
import com.wiftwift.functions.logarithmic.Log3;
import com.wiftwift.functions.logarithmic.Log5;
import com.wiftwift.functions.trigonometric.Sin;
import com.wiftwift.functions.trigonometric.Cos;
import com.wiftwift.functions.trigonometric.Tan;
import com.wiftwift.functions.trigonometric.Csc;

import static org.junit.Assert.*;

public class SystemSolverTest {
    
    private SystemSolver solverWithRealFunctions;
    private SystemSolver solverWithStubs;
    private final double DELTA = 1e-5;
    
    private Sin sinStub;
    private Cos cosStub;
    private Tan tanStub;
    private Csc cscStub;
    private Ln lnStub;
    private Log2 log2Stub;
    private Log3 log3Stub;
    private Log5 log5Stub;
    
    @Before
    public void setUp() {
        solverWithRealFunctions = new SystemSolver();
        solverWithRealFunctions.setEpsilon(1e-10);
        
        initializeStubs();
        solverWithStubs = new SystemSolver() {
            @Override
            protected void initializeFunctions() {
                sin = sinStub;
                cos = cosStub;
                tan = tanStub;
                csc = cscStub;
                ln = lnStub;
                log2 = log2Stub;
                log3 = log3Stub;
                log5 = log5Stub;
            }
        };
        solverWithStubs.setEpsilon(1e-10);
    }
    
    private void initializeStubs() {
        sinStub = new Sin(1e-10);
        sinStub.enableStub();
        
        cosStub = new Cos(1e-10, sinStub);
        cosStub.enableStub();
        
        tanStub = new Tan(1e-10, sinStub, cosStub);
        tanStub.enableStub();
        
        cscStub = new Csc(1e-10, sinStub);
        cscStub.enableStub();
        
        lnStub = new Ln(1e-10);
        lnStub.enableStub();
        
        log2Stub = new Log2(1e-10, lnStub);
        log2Stub.enableStub();
        
        log3Stub = new Log3(1e-10, lnStub);
        log3Stub.enableStub();
        
        log5Stub = new Log5(1e-10, lnStub);
        log5Stub.enableStub();
    }
    
    @After
    public void tearDown() {
        if (sinStub != null) sinStub.disableStub();
        if (cosStub != null) cosStub.disableStub();
        if (tanStub != null) tanStub.disableStub();
        if (cscStub != null) cscStub.disableStub();
        if (lnStub != null) lnStub.disableStub();
        if (log2Stub != null) log2Stub.disableStub();
        if (log3Stub != null) log3Stub.disableStub();
        if (log5Stub != null) log5Stub.disableStub();
    }
    
    @Test
    public void testNegativeBranch() {
        assertEquals(0, solverWithRealFunctions.solve(-1.19352), DELTA);
        assertEquals(0.65518, solverWithRealFunctions.solve(-2.45128), DELTA);
        assertEquals(0, solverWithRealFunctions.solve(-5.69378), DELTA);
        assertEquals(0, solverWithRealFunctions.solve(-7.4767), DELTA);
        assertEquals(0.65518, solverWithRealFunctions.solve(-8.73447), DELTA);
        assertEquals(64.84669, solverWithRealFunctions.solve(-5), DELTA);
        assertEquals(60.45285, solverWithRealFunctions.solve(-6), DELTA);
        assertEquals(43.24723, solverWithRealFunctions.solve(-2), DELTA);
    }
    
    @Test
    public void testNegativeBranchWithStubs() {
        assertEquals(0, solverWithStubs.solve(-1.19352), DELTA);
        assertEquals(0.65518, solverWithStubs.solve(-2.45128), DELTA);
        assertEquals(0, solverWithStubs.solve(-5.69378), DELTA);
        assertEquals(0, solverWithStubs.solve(-7.4767), DELTA);
        assertEquals(0.65518, solverWithStubs.solve(-8.73447), DELTA);
        assertEquals(64.84669, solverWithStubs.solve(-5), DELTA);
        assertEquals(60.45285, solverWithStubs.solve(-6), DELTA);
        assertEquals(43.24723, solverWithStubs.solve(-2), DELTA);
    }
    
    @Test
    public void testPositiveBranch() {
        assertEquals(0.30931, solverWithRealFunctions.solve(2.56578), DELTA);
        assertEquals(0.28465, solverWithRealFunctions.solve(2), DELTA);
        assertEquals(0.531296, solverWithRealFunctions.solve(0.2), DELTA);
        assertEquals(0, solverWithRealFunctions.solve(1), DELTA);
        assertEquals(0.160639, solverWithRealFunctions.solve(8), DELTA);
        assertEquals(0.13666, solverWithRealFunctions.solve(10), DELTA);
    }
    
    @Test
    public void testPositiveBranchWithStubs() {
        assertEquals(0.30931, solverWithStubs.solve(2.56578), DELTA);
        assertEquals(0.28465, solverWithStubs.solve(2), DELTA);
        assertEquals(0.531296, solverWithStubs.solve(0.2), DELTA);
        assertEquals(0, solverWithStubs.solve(1), DELTA);
        assertEquals(0.160639, solverWithStubs.solve(8), DELTA);
        assertEquals(0.13666, solverWithStubs.solve(10), DELTA);
    }
    
    @Test
    public void testRealVsStubs() {
        double[] negativePoints = {-1.19352, -2.45128, -5.69378, -7.4767, -8.73447, -5, -6, -2};
        
        for (double x : negativePoints) {
            double realResult = solverWithRealFunctions.solve(x);
            double stubResult = solverWithStubs.solve(x);
            
            assertEquals("Results should match for x=" + x, realResult, stubResult, DELTA);
        }
        
        double[] positivePoints = {0.2, 1, 2, 2.56578, 8, 10};
        
        for (double x : positivePoints) {
            double realResult = solverWithRealFunctions.solve(x);
            double stubResult = solverWithStubs.solve(x);
            
            assertEquals("Results should match for x=" + x, realResult, stubResult, DELTA);
        }
    }
    
    @Test(expected = ArithmeticException.class)
    public void testDomainRestrictions() {
        solverWithRealFunctions.solvePositive(0);
    }
    
    @Test
    public void testIntegrationOfAllComponents() {
        assertEquals(0.65518, solverWithRealFunctions.solve(-2.45128), DELTA);
        assertEquals(43.24723, solverWithRealFunctions.solve(-2), DELTA);
        assertEquals(0.28465, solverWithRealFunctions.solve(2), DELTA);
        
        assertEquals(0.65518, solverWithStubs.solve(-2.45128), DELTA);
        assertEquals(43.24723, solverWithStubs.solve(-2), DELTA);
        assertEquals(0.28465, solverWithStubs.solve(2), DELTA);
    }
    
    @Test
    public void testIntegrationWithMixedComponents() {
        Sin realSin = new Sin(1e-10);
        Ln realLn = new Ln(1e-10);
        
        Cos mixedCos = new Cos(1e-10, realSin);
        mixedCos.enableStub();
        
        Tan mixedTan = new Tan(1e-10, realSin, mixedCos);
        mixedTan.enableStub();
        
        Csc mixedCsc = new Csc(1e-10, realSin);
        mixedCsc.enableStub();
        
        Log2 mixedLog2 = new Log2(1e-10, realLn);
        mixedLog2.enableStub();
        
        Log3 mixedLog3 = new Log3(1e-10, realLn);
        mixedLog3.enableStub();
        
        Log5 mixedLog5 = new Log5(1e-10, realLn);
        mixedLog5.enableStub();
        
        SystemSolver mixedSolver = new SystemSolver() {
            @Override
            protected void initializeFunctions() {
                sin = realSin;
                cos = mixedCos;
                tan = mixedTan;
                csc = mixedCsc;
                ln = realLn;
                log2 = mixedLog2;
                log3 = mixedLog3;
                log5 = mixedLog5;
            }
        };
        mixedSolver.setEpsilon(1e-10);
        
        assertEquals(0.65518, mixedSolver.solve(-2.45128), DELTA);
        assertEquals(43.24723, mixedSolver.solve(-2), DELTA);
        assertEquals(0.28465, mixedSolver.solve(2), DELTA);
    }
}
