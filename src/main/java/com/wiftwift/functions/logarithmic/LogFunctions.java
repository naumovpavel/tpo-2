package com.wiftwift.functions.logarithmic;

public class LogFunctions {
    private final Ln ln;
    private final Log2 log2;
    private final Log3 log3;
    private final Log5 log5;
    
    public LogFunctions(double epsilon) {
        this.ln = new Ln(epsilon);
        this.log2 = new Log2(epsilon, ln);
        this.log3 = new Log3(epsilon, ln);
        this.log5 = new Log5(epsilon, ln);
    }
    
    public Ln getLn() {
        return ln;
    }
    
    public Log2 getLog2() {
        return log2;
    }
    
    public Log3 getLog3() {
        return log3;
    }
    
    public Log5 getLog5() {
        return log5;
    }
    
    public void enableStubs() {
        ln.enableStub();
        log2.enableStub();
        log3.enableStub();
        log5.enableStub();
    }
    
    public void disableStubs() {
        ln.disableStub();
        log2.disableStub();
        log3.disableStub();
        log5.disableStub();
    }
}
