package com.eatco.authservice.model;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_permissions")
@Data
public class UserPermissions extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    private Long userId;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean isEmailEnabled;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean isSmsEnabled;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean isBankingEnabled;

    private boolean isPushEnabled;

    private boolean isMerchantUpdatesEnabled;

    private boolean isBillingEnabled;


}


