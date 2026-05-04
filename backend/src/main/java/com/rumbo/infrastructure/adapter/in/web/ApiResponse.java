package com.rumbo.infrastructure.adapter.in.web;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiResponse<T>(
        boolean success,
        T data,
        String message,
        String timestamp
) {
    public static <T> ApiResponse<T> ok(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .timestamp(Instant.now().toString())
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .message(message)
                .timestamp(Instant.now().toString())
                .build();
    }
}
