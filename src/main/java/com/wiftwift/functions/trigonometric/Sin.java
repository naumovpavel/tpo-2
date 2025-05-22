package com.wiftwift.functions.trigonometric;

import java.util.HashMap;
import java.util.Map;

public class Sin {
    private final double epsilon;
    private final Map<Double, Double> stubValues = new HashMap<>();
    private boolean useStub = false;
    
    public Sin(double epsilon) {
        this.epsilon = epsilon;
        initializeStub();
    }
    
    private void initializeStub() {
        stubValues.put(-Math.PI, 0.0);
        stubValues.put(-Math.PI/2, -1.0);
        stubValues.put(-Math.PI/4, -Math.sqrt(2)/2);
        stubValues.put(-Math.PI/6, -0.5);
        stubValues.put(0.0, 0.0);
        stubValues.put(Math.PI/6, 0.5);
        stubValues.put(Math.PI/4, Math.sqrt(2)/2);
        stubValues.put(Math.PI/3, Math.sqrt(3)/2);
        stubValues.put(Math.PI/2, 1.0);
        stubValues.put(2*Math.PI/3, Math.sqrt(3)/2);
        stubValues.put(3*Math.PI/4, Math.sqrt(2)/2);
        stubValues.put(5*Math.PI/6, 0.5);
        stubValues.put(Math.PI, 0.0);
        
        stubValues.put(-1.19352, Math.sin(-1.19352));
        stubValues.put(-2.45128, Math.sin(-2.45128));
        stubValues.put(-5.69378, Math.sin(-5.69378));
        stubValues.put(-7.4767, Math.sin(-7.4767));
        stubValues.put(-8.73447, Math.sin(-8.73447));
        stubValues.put(-5.0, Math.sin(-5.0));
        stubValues.put(-6.0, Math.sin(-6.0));
        stubValues.put(-2.0, Math.sin(-2.0));
        stubValues.put(2.56578, Math.sin(2.56578));
        stubValues.put(2.0, Math.sin(2.0));
        stubValues.put(0.2, Math.sin(0.2));
        stubValues.put(1.0, Math.sin(1.0));
        stubValues.put(8.0, Math.sin(8.0));
        stubValues.put(10.0, Math.sin(10.0));
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
        
        // sin(x) = x - x^3/3! + x^5/5! - x^7/7! + ...
        double result = 0;
        double term = x;
        int n = 1;
        
        while (Math.abs(term) > epsilon) {
            result += term;
            term = -term * x * x / ((2 * n) * (2 * n + 1));
            n++;
        }
        
        return result;
    }
    
    private double normalizeAngle(double x) {
        double twoPI = 2 * Math.PI;
        return x - twoPI * Math.floor(x / twoPI + 0.5);
    }
}
