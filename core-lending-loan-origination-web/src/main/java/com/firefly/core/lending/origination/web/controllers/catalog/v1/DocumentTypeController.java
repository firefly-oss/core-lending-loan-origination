package com.firefly.core.lending.origination.web.controllers.catalog.v1;

import com.firefly.core.lending.origination.core.services.catalog.v1.DocumentTypeService;
import com.firefly.core.lending.origination.models.entities.catalog.v1.DocumentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/document-types")
@RequiredArgsConstructor
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @GetMapping
    public Flux<DocumentType> getAllDocumentTypes() {
        return documentTypeService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<DocumentType> getDocumentTypeById(@PathVariable Long id) {
        return documentTypeService.findById(id);
    }

    @GetMapping("/code/{code}")
    public Mono<DocumentType> getDocumentTypeByCode(@PathVariable String code) {
        return documentTypeService.findByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DocumentType> createDocumentType(@RequestBody DocumentType documentType) {
        return documentTypeService.save(documentType);
    }

    @PutMapping("/{id}")
    public Mono<DocumentType> updateDocumentType(@PathVariable Long id, @RequestBody DocumentType documentType) {
        documentType.setDocumentTypeId(id);
        return documentTypeService.save(documentType);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteDocumentType(@PathVariable Long id) {
        return documentTypeService.deleteById(id);
    }
}