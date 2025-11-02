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


package com.firefly.core.lending.origination.core.services.task.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.ApplicationTaskMapper;
import com.firefly.core.lending.origination.interfaces.dtos.task.v1.ApplicationTaskDTO;
import com.firefly.core.lending.origination.models.entities.task.v1.ApplicationTask;
import com.firefly.core.lending.origination.models.repositories.task.v1.ApplicationTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationTaskServiceImpl implements ApplicationTaskService {

    @Autowired
    private ApplicationTaskRepository repository;

    @Autowired
    private ApplicationTaskMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationTaskDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationTaskDTO> createTask(UUID applicationId, ApplicationTaskDTO dto) {
        ApplicationTask entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationTaskDTO> getTask(UUID applicationId, UUID taskId) {
        return repository.findById(taskId)
                .filter(task -> task.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationTaskDTO> updateTask(UUID applicationId, UUID taskId, ApplicationTaskDTO dto) {
        return repository.findById(taskId)
                .filter(task -> task.getLoanApplicationId().equals(applicationId))
                .flatMap(existingTask -> {
                    ApplicationTask updated = mapper.toEntity(dto);
                    updated.setTaskId(taskId);
                    updated.setLoanApplicationId(applicationId);
                    updated.setCreatedAt(existingTask.getCreatedAt());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteTask(UUID applicationId, UUID taskId) {
        return repository.findById(taskId)
                .filter(task -> task.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}

