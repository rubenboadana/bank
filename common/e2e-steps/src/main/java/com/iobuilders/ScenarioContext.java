package com.iobuilders;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@ScenarioScope
@Component
@Data
public class ScenarioContext {
    private ResponseEntity<String> response;
}
