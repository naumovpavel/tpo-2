package com.wiftwift.functions.logarithmic;

import java.util.HashMap;
import java.util.Map;

public class Log5 {
    private final double epsilon;
    private final Ln ln;
    private final Map<Double, Double> stubValues = new HashMap<>();
    private boolean useStub = false;
    
    public Log5(double epsilon, Ln ln) {
        this.epsilon = epsilon;
        this.ln = ln;
        initializeStub();
    }
    
    private void initializeStub() {
        stubValues.put(1.0, 0.0);
        stubValues.put(5.0, 1.0);
        stubValues.put(25.0, 2.0);
        stubValues.put(125.0, 3.0);
        stubValues.put(625.0, 4.0);
        stubValues.put(3125.0, 5.0);
        
        stubValues.put(0.2, Math.log(0.2) / Math.log(5));
        stubValues.put(1.0, 0.0);
        stubValues.put(2.0, Math.log(2.0) / Math.log(5));
        stubValues.put(2.56578, Math.log(2.56578) / Math.log(5));
        stubValues.put(8.0, Math.log(8.0) / Math.log(5));
        stubValues.put(10.0, Math.log(10.0) / Math.log(5));
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
        if (x <= 0) {
            throw new ArithmeticException("Logarithm base 5 is undefined for x <= 0");
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
        
        double lnX = ln.calculate(x);
        double ln5 = ln.calculate(5.0);
        return lnX / ln5;
    }
}
