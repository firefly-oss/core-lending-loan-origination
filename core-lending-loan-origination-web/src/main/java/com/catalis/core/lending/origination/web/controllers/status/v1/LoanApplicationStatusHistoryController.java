package com.catalis.core.lending.origination.web.controllers.status.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.origination.core.services.status.v1.LoanApplicationStatusHistoryService;
import com.catalis.core.lending.origination.interfaces.dtos.status.v1.LoanApplicationStatusHistoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/loan-applications/{applicationId}/status-history")
@RequiredArgsConstructor
@Tag(name = "LoanApplicationStatusHistory", description = "Manage status history records for a loan application")
public class LoanApplicationStatusHistoryController {

    private final LoanApplicationStatusHistoryService service;

    @GetMapping
    @Operation(summary = "List status history", description = "Retrieves a paginated list of status history entries for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<LoanApplicationStatusHistoryDTO>>> findAllStatusHistory(
            @PathVariable Long applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a status record", description = "Creates a new status history record for a loan application.")
    public Mono<ResponseEntity<LoanApplicationStatusHistoryDTO>> createStatusHistory(
            @PathVariable Long applicationId,
            @RequestBody LoanApplicationStatusHistoryDTO dto) {
        return service.createStatusHistory(applicationId, dto)
                .map(createdDto -> ResponseEntity.status(201).body(createdDto))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{statusHistoryId}")
    @Operation(summary = "Get a status record", description = "Retrieves a specific status history record.")
    public Mono<ResponseEntity<LoanApplicationStatusHistoryDTO>> getStatusHistory(
            @PathVariable Long applicationId,
            @PathVariable Long statusHistoryId) {
        return service.getStatusHistory(applicationId, statusHistoryId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{statusHistoryId}")
    @Operation(summary = "Update a status record", description = "Updates an existing status history record.")
    public Mono<ResponseEntity<LoanApplicationStatusHistoryDTO>> updateStatusHistory(
            @PathVariable Long applicationId,
            @PathVariable Long statusHistoryId,
            @RequestBody LoanApplicationStatusHistoryDTO dto) {
        return service.updateStatusHistory(applicationId, statusHistoryId, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{statusHistoryId}")
    @Operation(summary = "Delete a status record", description = "Removes a status history entry.")
    public Mono<ResponseEntity<Void>> deleteStatusHistory(
            @PathVariable Long applicationId,
            @PathVariable Long statusHistoryId) {
        return service.deleteStatusHistory(applicationId, statusHistoryId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}