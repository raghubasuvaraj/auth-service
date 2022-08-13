package com.eatco.authservice.dto;

import lombok.Data;

@Data
public class UserPermissionsDto {

    private Long userId;
    private Boolean isEmailEnabled = true;
    private Boolean isPushEnabled = false;
    private Boolean isSmsEnabled  = true;
    private Boolean isBankingEnabled = true;
    private Boolean isMerchantUpdatesEnabled = true;
    private Boolean isBillingEnabled = true;

}
