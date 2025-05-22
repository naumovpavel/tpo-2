package com.wiftwift;

import com.wiftwift.utils.CsvWriter;

public class Main {
    public static void main(String[] args) {
        SystemSolver solver = new SystemSolver();
        solver.setEpsilon(1e-6);

        double start = -5.0;
        double end = 5.0;
        double step = 0.1;
        
        CsvWriter.writeFunction("system_function.csv", "x,f(x)", start, end, step, solver::solve);
        
        System.out.println("Results written to system_function.csv");
    }
}
