package com.example.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by Bad_sha 24/07/22
 */

/**
 * The persistent class for the category_log database table.
 * 
 */
@Entity
@Table(name="category_log")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "created_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDateTime;

	@Column(name = "created_user")
	private String createdUser;

	@Column(name = "last_modified_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDateTime;

	@Column(name = "last_modified_user")
	private String lastModifiedUser;
}