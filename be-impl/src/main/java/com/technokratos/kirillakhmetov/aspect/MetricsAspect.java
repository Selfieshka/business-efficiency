package com.technokratos.kirillakhmetov.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class MetricsAspect {
    private final Map<String, MetricStats> metrics = new ConcurrentHashMap<>();

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {
    }

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void repositoryMethods() {
    }

    @Around("restControllerMethods() || serviceMethods() || repositoryMethods()")
    public Object collectMetrics(ProceedingJoinPoint pjp) throws Throwable {
        String methodSignature = pjp.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        try {
            Object result = pjp.proceed();
            return result;
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            updateMetrics(methodSignature, duration);
        }
    }

    private void updateMetrics(String methodSignature, long duration) {
        metrics.computeIfAbsent(methodSignature, k -> new MetricStats())
                .record(duration);
    }

    public Map<String, Map<String, Double>> getMetricsSummary() {
        Map<String, Map<String, Double>> summary = new HashMap<>();
        for (Map.Entry<String, MetricStats> entry : metrics.entrySet()) {
            summary.put(entry.getKey(), entry.getValue().getSummary());
        }
        return summary;
    }

    private static class MetricStats {
        private long count = 0;
        private double totalTime = 0;

        void record(long duration) {
            count++;
            totalTime += duration;
        }

        Map<String, Double> getSummary() {
            Map<String, Double> map = new HashMap<>();
            map.put("count", (double) count);
            map.put("averageTime", count > 0 ? totalTime / count : 0);
            return map;
        }
    }
}