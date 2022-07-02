package org.melsif.secretkeeper.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "readiness")
public class ReadinessEndpoint {

    private String readiness = "NOT READY";

    @ReadOperation
    public String getReadiness() {
        return readiness;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setReady() {
        readiness = "READY";
    }
}
