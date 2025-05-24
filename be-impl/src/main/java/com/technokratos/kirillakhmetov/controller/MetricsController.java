package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.api.MetricsApi;
import com.technokratos.kirillakhmetov.aspect.MetricsAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MetricsController implements MetricsApi {
    private final MetricsAspect metricsAspect;

    @GetMapping
    @Override
    public Map<String, Map<String, Double>> getMethodMetrics() {
        return metricsAspect.getMetricsSummary();
    }
}