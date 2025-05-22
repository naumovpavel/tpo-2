package com.wiftwift;

import com.wiftwift.functions.logarithmic.Log2;
import com.wiftwift.functions.logarithmic.Log3;
import com.wiftwift.functions.logarithmic.Log5;
import com.wiftwift.functions.logarithmic.Ln;
import com.wiftwift.functions.trigonometric.Sin;
import com.wiftwift.functions.trigonometric.Cos;
import com.wiftwift.functions.trigonometric.Tan;
import com.wiftwift.functions.trigonometric.Csc;

public class SystemSolver {
    private double epsilon = 1e-6;
    
    protected Sin sin;
    protected Cos cos;
    protected Tan tan;
    protected Csc csc;
    protected Ln ln;
    protected Log2 log2;
    protected Log3 log3;
    protected Log5 log5;
    
    public SystemSolver() {
        initializeFunctions();
    }
    
    protected void initializeFunctions() {
        sin = new Sin(epsilon);
        cos = new Cos(epsilon, sin);
        tan = new Tan(epsilon, sin, cos);
        csc = new Csc(epsilon, sin);
        ln = new Ln(epsilon);
        log2 = new Log2(epsilon, ln);
        log3 = new Log3(epsilon, ln);
        log5 = new Log5(epsilon, ln);
    }
    
    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
        initializeFunctions();
    }
    
    public double solve(double x) {
        if (x <= 0) {
            return solveNegative(x);
        } else {
            return solvePositive(x);
        }
    }
    
    public double solveNegative(double x) {
        try {
            double sinValue = sin.calculate(x);
            double cosValue = cos.calculate(x);
            double tanValue = tan.calculate(x);
            
            if (Math.abs(sinValue) < epsilon) {
                throw new ArithmeticException("Cosecant is undefined (division by zero)");
            }
            
            double cscValue = csc.calculate(x);
            
            if (Math.abs(tanValue) < epsilon) {
                throw new ArithmeticException("Division by zero (tan(x) is too close to zero)");
            }
            
            double step1 = sinValue - cosValue;
            double step2 = step1 - tanValue;
            double step3 = Math.pow(step2, 2);
            double step4 = cscValue - (cscValue / tanValue);
            double step5 = step3 + step4;
            double result = Math.pow(step5, 2);
            
            return result;
        } catch (ArithmeticException e) {
            return Double.NaN;
        }
    }
    
    public double solvePositive(double x) {
        if (x <= 0) {
            throw new ArithmeticException("Logarithms are undefined for x <= 0");
        }
        
        if (Math.abs(x - 1) < epsilon) {
            return 0.0;
        }
        
        try {
            double log2Value = log2.calculate(x);
            double log3Value = log3.calculate(x);
            double log5Value = log5.calculate(x);
            
            if (Math.abs(log2Value) < epsilon) {
                throw new ArithmeticException("Division by zero (log_2(x) is too close to zero)");
            }
            
            double denominator1 = (log3Value + log3Value) / log2Value;
            double denominator = Math.pow(log3Value, 3) + denominator1;
            
            if (Math.abs(denominator) < epsilon) {
                throw new ArithmeticException("Division by zero (denominator is too close to zero)");
            }
            
            double result = log5Value / denominator;
            
            return result;
        } catch (ArithmeticException e) {
            return Double.NaN;
        }
    }
}
