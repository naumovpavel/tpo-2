package com.wiftwift.functions.trigonometric;

import java.util.HashMap;
import java.util.Map;

public class Cos {
    private final double epsilon;
    private final Sin sin;
    private final Map<Double, Double> stubValues = new HashMap<>();
    private boolean useStub = false;
    
    public Cos(double epsilon, Sin sin) {
        this.epsilon = epsilon;
        this.sin = sin;
        initializeStub();
    }
    
    private void initializeStub() {
        stubValues.put(-Math.PI, -1.0);
        stubValues.put(-Math.PI/2, 0.0);
        stubValues.put(-Math.PI/4, Math.sqrt(2)/2);
        stubValues.put(-Math.PI/6, Math.sqrt(3)/2);
        stubValues.put(0.0, 1.0);
        stubValues.put(Math.PI/6, Math.sqrt(3)/2);
        stubValues.put(Math.PI/4, Math.sqrt(2)/2);
        stubValues.put(Math.PI/3, 0.5);
        stubValues.put(Math.PI/2, 0.0);
        stubValues.put(2*Math.PI/3, -0.5);
        stubValues.put(3*Math.PI/4, -Math.sqrt(2)/2);
        stubValues.put(5*Math.PI/6, -Math.sqrt(3)/2);
        stubValues.put(Math.PI, -1.0);
        
        stubValues.put(-1.19352, Math.cos(-1.19352));
        stubValues.put(-2.45128, Math.cos(-2.45128));
        stubValues.put(-5.69378, Math.cos(-5.69378));
        stubValues.put(-7.4767, Math.cos(-7.4767));
        stubValues.put(-8.73447, Math.cos(-8.73447));
        stubValues.put(-5.0, Math.cos(-5.0));
        stubValues.put(-6.0, Math.cos(-6.0));
        stubValues.put(-2.0, Math.cos(-2.0));
        stubValues.put(2.56578, Math.cos(2.56578));
        stubValues.put(2.0, Math.cos(2.0));
        stubValues.put(0.2, Math.cos(0.2));
        stubValues.put(1.0, Math.cos(1.0));
        stubValues.put(8.0, Math.cos(8.0));
        stubValues.put(10.0, Math.cos(10.0));
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
        
        // cos(x) = 1 - x^2/2! + x^4/4! - x^6/6! + ...
        double result = 0;
        double term = 1;
        int n = 0;
        
        while (Math.abs(term) > epsilon) {
            result += term;
            term = -term * x * x / ((2 * n + 1) * (2 * n + 2));
            n++;
        }
        
        return result;
    }
    
    private double normalizeAngle(double x) {
        double twoPI = 2 * Math.PI;
        return x - twoPI * Math.floor(x / twoPI + 0.5);
    }
}
