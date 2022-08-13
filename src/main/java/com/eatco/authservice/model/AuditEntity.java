package com.eatco.authservice.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(value=AuditingEntityListener.class)
@Data
public abstract class AuditEntity {

	@Column(name = "created_at", updatable = false)
	@CreatedDate
	@Temporal(TIMESTAMP)
	protected Date createdAt;

	@Column(name = "updated_at")
	@LastModifiedDate
	@Temporal(TIMESTAMP)
	protected Date updatedAt;

	@Column(name = "created_by", length = 150)
	@CreatedBy
	protected String createdBy;

	@Column(name = "updated_by",length = 150)
	@LastModifiedBy
	protected String updatedBy;



}
