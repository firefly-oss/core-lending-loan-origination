package com.catalis.core.lending.origination.web.controllers.catalog.v1;

import com.catalis.core.lending.origination.core.services.catalog.v1.DecisionCodeService;
import com.catalis.core.lending.origination.models.entities.catalog.v1.DecisionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/decision-codes")
@RequiredArgsConstructor
public class DecisionCodeController {

    private final DecisionCodeService decisionCodeService;

    @GetMapping
    public Flux<DecisionCode> getAllDecisionCodes() {
        return decisionCodeService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<DecisionCode> getDecisionCodeById(@PathVariable Long id) {
        return decisionCodeService.findById(id);
    }

    @GetMapping("/code/{code}")
    public Mono<DecisionCode> getDecisionCodeByCode(@PathVariable String code) {
        return decisionCodeService.findByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DecisionCode> createDecisionCode(@RequestBody DecisionCode decisionCode) {
        return decisionCodeService.save(decisionCode);
    }

    @PutMapping("/{id}")
    public Mono<DecisionCode> updateDecisionCode(@PathVariable Long id, @RequestBody DecisionCode decisionCode) {
        decisionCode.setDecisionCodeId(id);
        return decisionCodeService.save(decisionCode);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteDecisionCode(@PathVariable Long id) {
        return decisionCodeService.deleteById(id);
    }
}