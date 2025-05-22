package com.wiftwift.functions.trigonometric;

import java.util.HashMap;
import java.util.Map;

public class Csc {
    private final double epsilon;
    private final Sin sin;
    private final Map<Double, Double> stubValues = new HashMap<>();
    private boolean useStub = false;
    
    public Csc(double epsilon, Sin sin) {
        this.epsilon = epsilon;
        this.sin = sin;
        initializeStub();
    }
    
    private void initializeStub() {
        stubValues.put(-Math.PI/2, -1.0);
        stubValues.put(-Math.PI/3, -2/Math.sqrt(3));
        stubValues.put(-Math.PI/4, -Math.sqrt(2));
        stubValues.put(-Math.PI/6, -2.0);
        stubValues.put(Math.PI/6, 2.0);
        stubValues.put(Math.PI/4, Math.sqrt(2));
        stubValues.put(Math.PI/3, 2/Math.sqrt(3));
        stubValues.put(Math.PI/2, 1.0);
        
        stubValues.put(-1.19352, 1 / Math.sin(-1.19352));
        stubValues.put(-2.45128, 1 / Math.sin(-2.45128));
        stubValues.put(-5.69378, 1 / Math.sin(-5.69378));
        stubValues.put(-7.4767, 1 / Math.sin(-7.4767));
        stubValues.put(-8.73447, 1 / Math.sin(-8.73447));
        stubValues.put(-5.0, 1 / Math.sin(-5.0));
        stubValues.put(-6.0, 1 / Math.sin(-6.0));
        stubValues.put(-2.0, 1 / Math.sin(-2.0));
        stubValues.put(2.56578, 1 / Math.sin(2.56578));
        stubValues.put(2.0, 1 / Math.sin(2.0));
        stubValues.put(0.2, 1 / Math.sin(0.2));
        stubValues.put(1.0, 1 / Math.sin(1.0));
        stubValues.put(8.0, 1 / Math.sin(8.0));
        stubValues.put(10.0, 1 / Math.sin(10.0));
    }
    
    public void enableStub() {
        useStub = true;
    }
    
    public void disableStub() {
        useStub = false;
    }
    
    public void addStubValue(double x, double result) {
        stubValues.put(x, result);
    }
    
    public double calculate(double x) {
        x = normalizeAngle(x);
        double sinValue = sin.calculate(x);
        
        if (Math.abs(sinValue) < epsilon) {
            throw new ArithmeticException("Cosecant is undefined at x = " + x);
        }
        
        if (useStub) {
            Double stubResult = stubValues.get(x);
            if (stubResult != null) {
                return stubResult;
            }
            
            double delta = 1e-10;
            for (Map.Entry<Double, Double> entry : stubValues.entrySet()) {
                if (Math.abs(entry.getKey() - x) < delta) {
                    return entry.getValue();
                }
            }
        }
        
        return 1 / sinValue;
    }
    
    private double normalizeAngle(double x) {
        double PI = Math.PI;
        return ((x + PI) % (2 * PI)) - PI;
    }
}
