package com.technokratos.kirillakhmetov.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@RequestMapping("/api/v1/metrics")
public interface MetricsApi {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Map<String, Map<String, Double>> getMethodMetrics();
}
