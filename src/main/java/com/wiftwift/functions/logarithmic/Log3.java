package com.wiftwift.functions.logarithmic;

import java.util.HashMap;
import java.util.Map;

public class Log3 {
    private final double epsilon;
    private final Ln ln;
    private final Map<Double, Double> stubValues = new HashMap<>();
    private boolean useStub = false;
    
    public Log3(double epsilon, Ln ln) {
        this.epsilon = epsilon;
        this.ln = ln;
        initializeStub();
    }
    
    private void initializeStub() {
        stubValues.put(1.0, 0.0);
        stubValues.put(3.0, 1.0);
        stubValues.put(9.0, 2.0);
        stubValues.put(27.0, 3.0);
        stubValues.put(81.0, 4.0);
        stubValues.put(243.0, 5.0);
        
        stubValues.put(0.2, Math.log(0.2) / Math.log(3));
        stubValues.put(1.0, 0.0);
        stubValues.put(2.0, Math.log(2.0) / Math.log(3));
        stubValues.put(2.56578, Math.log(2.56578) / Math.log(3));
        stubValues.put(8.0, Math.log(8.0) / Math.log(3));
        stubValues.put(10.0, Math.log(10.0) / Math.log(3));
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
            throw new ArithmeticException("Logarithm base 3 is undefined for x <= 0");
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
        double ln3 = ln.calculate(3.0);
        return lnX / ln3;
    }
}
