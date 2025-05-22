package com.wiftwift.functions.trigonometric;

public class TrigFunctions {
    private final Sin sin;
    private final Cos cos;
    private final Tan tan;
    private final Csc csc;
    
    public TrigFunctions(double epsilon) {
        this.sin = new Sin(epsilon);
        this.cos = new Cos(epsilon, sin);
        this.tan = new Tan(epsilon, sin, cos);
        this.csc = new Csc(epsilon, sin);
    }
    
    public Sin getSin() {
        return sin;
    }
    
    public Cos getCos() {
        return cos;
    }
    
    public Tan getTan() {
        return tan;
    }
    
    public Csc getCsc() {
        return csc;
    }
    
    public void enableStubs() {
        sin.enableStub();
        cos.enableStub();
        tan.enableStub();
        csc.enableStub();
    }
    
    public void disableStubs() {
        sin.disableStub();
        cos.disableStub();
        tan.disableStub();
        csc.disableStub();
    }
}
