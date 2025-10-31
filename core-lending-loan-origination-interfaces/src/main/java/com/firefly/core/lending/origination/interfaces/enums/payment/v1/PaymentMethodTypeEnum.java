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


package com.firefly.core.lending.origination.interfaces.enums.payment.v1;

/**
 * Enum representing the type of payment method for loan disbursement or repayment.
 * 
 * <ul>
 *   <li><b>INTERNAL_ACCOUNT</b>: Payment through an account within the Firefly core banking system.
 *       The account is identified by an internal account ID.</li>
 *   <li><b>EXTERNAL_ACCOUNT</b>: Payment through an external bank account (domiciliación/direct debit).
 *       The account details are stored in the ApplicationExternalBankAccount entity.</li>
 * </ul>
 */
public enum PaymentMethodTypeEnum {
    /**
     * Payment through an internal account in the Firefly core banking system.
     */
    INTERNAL_ACCOUNT,
    
    /**
     * Payment through an external bank account (domiciliación/direct debit).
     */
    EXTERNAL_ACCOUNT
}

