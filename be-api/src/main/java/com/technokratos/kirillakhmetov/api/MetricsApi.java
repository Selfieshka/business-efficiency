package com.technokratos.kirillakhmetov.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/api/metrics")
public interface MetricsApi {
    @GetMapping
    Map<String, Map<String, Double>> getMethodMetrics();
}
