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


package com.firefly.core.lending.origination.core.services.collateral.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.collateral.v1.ApplicationCollateralDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationCollateralService {

    /**
     * Retrieves a paginated list of application collateral items associated with a specific application ID.
     *
     * @param applicationId the unique identifier of the application whose collateral items are to be retrieved
     * @param paginationRequest the request object specifying pagination details such as page number and size
     * @return a reactive Mono containing a paginated response with a list of ApplicationCollateralDTO objects
     *         representing the collateral items associated with the provided application ID
     */
    Mono<PaginationResponse<ApplicationCollateralDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest);

    /**
     * Creates a new collateral entry associated with a specific loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the collateral is to be added
     * @param dto the data transfer object containing details of the collateral to be created
     * @return a reactive Mono containing the created ApplicationCollateralDTO representing the new collateral entry
     */
    Mono<ApplicationCollateralDTO> createCollateral(UUID applicationId, ApplicationCollateralDTO dto);

    /**
     * Retrieves the details of a specific collateral associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the collateral is linked
     * @param collateralId the unique identifier of the collateral to be retrieved
     * @return a reactive Mono containing the ApplicationCollateralDTO object representing the details of the specified collateral
     */
    Mono<ApplicationCollateralDTO> getCollateral(UUID applicationId, UUID collateralId);

    /**
     * Updates an existing collateral associated with a specific application and collateral ID.
     *
     * @param applicationId the unique identifier of the loan application to which the collateral belongs
     * @param collateralId the unique identifier of the collateral to be updated
     * @param dto the data transfer object containing updated details for the application collateral
     * @return a Mono emitting the updated ApplicationCollateralDTO upon a successful update operation
     */
    Mono<ApplicationCollateralDTO> updateCollateral(UUID applicationId, UUID collateralId, ApplicationCollateralDTO dto);

    /**
     * Deletes a specific collateral associated with a loan application.
     *
     * @param applicationId the unique identifier of the loan application to which the collateral belongs
     * @param collateralId the unique identifier of the collateral to be deleted
     * @return a Mono signaling when the deletion operation has been completed
     */
    Mono<Void> deleteCollateral(UUID applicationId, UUID collateralId);
}
