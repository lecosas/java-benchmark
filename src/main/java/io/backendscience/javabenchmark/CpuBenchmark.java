package io.backendscience.javabenchmark;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CpuBenchmark {

//    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors(); // Use all available cores
//    private static final long ITERATIONS = 10_000_000_000L; // Heavy computation
//
//    public static void main(String[] args) {
//        System.out.println("Starting CPU benchmark with " + THREAD_COUNT + " threads...");
//
//        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
//
//        long startTime = System.nanoTime();
//        long startCpuTime = getCpuTime(threadMXBean);
//
//        for (int i = 0; i < THREAD_COUNT; i++) {
//            executorService.execute(CpuBenchmark::performHeavyComputation);
//        }
//
//        executorService.shutdown();
//        while (!executorService.isTerminated()) {
//            // Wait for all threads to finish
//        }
//
//        long endTime = System.nanoTime();
//        long endCpuTime = getCpuTime(threadMXBean);
//
//        System.out.println("Total elapsed time: " + (endTime - startTime) / 1_000_000 + " ms");
//        System.out.println("Total CPU time used: " + (endCpuTime - startCpuTime) / 1_000_000 + " ms");
//        System.out.println("CPU Time per thread: " + (endCpuTime - startCpuTime) / THREAD_COUNT / 1_000_000 + " ms");
//    }
//
//    private static void performHeavyComputation() {
//        double result = 0;
//        for (long i = 0; i < ITERATIONS; i++) {
////            result += Math.sqrt(i); // CPU-intensive operation
//            result += i; // CPU-intensive operation
//        }
//        System.out.println("Computation done by " + Thread.currentThread().getName() + " result: " + result);
//    }
//
//    private static long getCpuTime(ThreadMXBean threadMXBean) {
//        long totalCpuTime = 0;
//        for (long id : threadMXBean.getAllThreadIds()) {
//            totalCpuTime += threadMXBean.getThreadCpuTime(id);
//        }
//        return totalCpuTime;
//    }
}
