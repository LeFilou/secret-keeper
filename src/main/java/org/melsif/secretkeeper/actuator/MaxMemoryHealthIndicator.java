package org.melsif.secretkeeper.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class MaxMemoryHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean insufficientMemory = Runtime.getRuntime().maxMemory() < (1024 * 1024 * 100);
        final Status status = insufficientMemory ? Status.DOWN : Status.UP;
        return Health.status(status).build();
    }
}
