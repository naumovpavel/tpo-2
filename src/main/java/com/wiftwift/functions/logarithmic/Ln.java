package com.wiftwift.functions.logarithmic;

import java.util.HashMap;
import java.util.Map;

public class Ln {
    private final double epsilon;
    private final Map<Double, Double> stubValues = new HashMap<>();

    private boolean useStub = false;
    private final double LN2;

    private static final int MAX_TAYLOR_ITER = 1_000_000;

    public Ln(double epsilon) {
        this.epsilon = epsilon;
        this.LN2 = computeLn2();
        initializeStub();
    }

    private double computeLn2() {
        double sum = 0.0;
        double term;
        double powerOf2 = 1.0;
        int k = 1;

        while (true) {
            powerOf2 *= 2;
            term = 1.0 / (k * powerOf2);
            if (term < epsilon) {
                sum += term;
                break;
            }
            sum += term;
            k++;
            if (k > MAX_TAYLOR_ITER) {
                break;
            }
        }
        return sum;
    }

    private void initializeStub() {
        stubValues.put(1.0, 0.0);
        stubValues.put(2.0, 0.69314718056);
        stubValues.put(3.0, 1.0986122886681098);
        stubValues.put(5.0, 1.6094379124341003);
        stubValues.put(8.0, 2.07944154168);
        stubValues.put(10.0, 2.302585092994046);

        stubValues.put(0.1, -2.302585092994046);
        stubValues.put(0.01, -4.605170185988092);
        stubValues.put(0.5, -0.6931471805599453);

        stubValues.put(2.56578, 0.94226252596);
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
            throw new ArithmeticException("Natural logarithm is undefined for x <= 0");
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
    
        return approximateLn(x);
    }

    private double approximateLn(double x) {
        double result = 0.0;
    
        while (x >= 2.0) {
            x /= 2.0;
            result += LN2;
        }
    
        while (x < 1.0) {
            x *= 2.0;
            result -= LN2;
        }
    
        double z = x - 1.0;
        double lnPart = taylorSeries(z); 
    
        return result + lnPart;
    }
    
    // ln(1+z) = z - z²/2 + z³/3 - ...
    private double taylorSeries(double z) {
        double sum = 0.0;
        double term = z;
        double zPow = z;
        int n = 1;
    
        while (Math.abs(term) > epsilon) {
            sum += term;
            n++;
            zPow *= z;
            term = ((n % 2 == 0) ? -1.0 : 1.0) * (zPow / n);
    
            if (n > MAX_TAYLOR_ITER) {
                break; 
            }
        }
    
        return sum;
    }
    

}