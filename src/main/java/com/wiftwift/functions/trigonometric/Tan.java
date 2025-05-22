package com.wiftwift.functions.trigonometric;

import java.util.HashMap;
import java.util.Map;

public class Tan {
    private final double epsilon;
    private final Sin sin;
    private final Cos cos;
    private final Map<Double, Double> stubValues = new HashMap<>();
    private boolean useStub = false;
    
    public Tan(double epsilon, Sin sin, Cos cos) {
        this.epsilon = epsilon;
        this.sin = sin;
        this.cos = cos;
        initializeStub();
    }
    
    private void initializeStub() {
        stubValues.put(-Math.PI/3, -Math.sqrt(3));
        stubValues.put(-Math.PI/4, -1.0);
        stubValues.put(-Math.PI/6, -1/Math.sqrt(3));
        stubValues.put(0.0, 0.0);
        stubValues.put(Math.PI/6, 1/Math.sqrt(3));
        stubValues.put(Math.PI/4, 1.0);
        stubValues.put(Math.PI/3, Math.sqrt(3));
        
        stubValues.put(-1.19352, Math.tan(-1.19352));
        stubValues.put(-2.45128, Math.tan(-2.45128));
        stubValues.put(-5.69378, Math.tan(-5.69378));
        stubValues.put(-7.4767, Math.tan(-7.4767));
        stubValues.put(-8.73447, Math.tan(-8.73447));
        stubValues.put(-5.0, Math.tan(-5.0));
        stubValues.put(-6.0, Math.tan(-6.0));
        stubValues.put(-2.0, Math.tan(-2.0));
        stubValues.put(2.56578, Math.tan(2.56578));
        stubValues.put(2.0, Math.tan(2.0));
        stubValues.put(0.2, Math.tan(0.2));
        stubValues.put(1.0, Math.tan(1.0));
        stubValues.put(8.0, Math.tan(8.0));
        stubValues.put(10.0, Math.tan(10.0));
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
        double cosValue = cos.calculate(x);
        
        if (Math.abs(cosValue) < epsilon) {
            throw new ArithmeticException("Tangent is undefined at x = " + x);
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
        
        double sinValue = sin.calculate(x);
        return sinValue / cosValue;
    }
    
    private double normalizeAngle(double x) {
        double PI = Math.PI;
        return ((x + PI) % (2 * PI)) - PI;
    }
}
