/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.lending.origination.core.services.impl;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.ApplicationCommentMapper;
import com.firefly.core.lending.origination.core.services.ApplicationCommentService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationCommentDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationComment;
import com.firefly.core.lending.origination.models.repositories.ApplicationCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationCommentServiceImpl implements ApplicationCommentService {

    @Autowired
    private ApplicationCommentRepository repository;

    @Autowired
    private ApplicationCommentMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationCommentDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationCommentDTO> createComment(UUID applicationId, ApplicationCommentDTO dto) {
        ApplicationComment entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationCommentDTO> getComment(UUID applicationId, UUID commentId) {
        return repository.findById(commentId)
                .filter(comment -> comment.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationCommentDTO> updateComment(UUID applicationId, UUID commentId, ApplicationCommentDTO dto) {
        return repository.findById(commentId)
                .filter(comment -> comment.getLoanApplicationId().equals(applicationId))
                .flatMap(existingComment -> {
                    ApplicationComment updated = mapper.toEntity(dto);
                    updated.setCommentId(commentId);
                    updated.setLoanApplicationId(applicationId);
                    updated.setCreatedAt(existingComment.getCreatedAt());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteComment(UUID applicationId, UUID commentId) {
        return repository.findById(commentId)
                .filter(comment -> comment.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}

