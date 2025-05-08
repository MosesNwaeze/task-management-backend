package com.softnet.task_management.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperty(
  String secret,
  Long expiration
) {
}
