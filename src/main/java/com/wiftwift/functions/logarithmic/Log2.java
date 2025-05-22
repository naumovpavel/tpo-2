package com.wiftwift.functions.logarithmic;

import java.util.HashMap;
import java.util.Map;

public class Log2 {
    private final double epsilon;
    private final Ln ln;
    private final Map<Double, Double> stubValues = new HashMap<>();
    private boolean useStub = false;
    
    public Log2(double epsilon, Ln ln) {
        this.epsilon = epsilon;
        this.ln = ln;
        initializeStub();
    }
    
    private void initializeStub() {
        stubValues.put(1.0, 0.0);
        stubValues.put(2.0, 1.0);
        stubValues.put(4.0, 2.0);
        stubValues.put(8.0, 3.0);
        stubValues.put(16.0, 4.0);
        stubValues.put(32.0, 5.0);
        stubValues.put(64.0, 6.0);
        stubValues.put(128.0, 7.0);
        stubValues.put(256.0, 8.0);
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
            throw new ArithmeticException("Logarithm base 2 is undefined for x <= 0");
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
        double ln2 = ln.calculate(2.0);
        return lnX / ln2;
    }
}
