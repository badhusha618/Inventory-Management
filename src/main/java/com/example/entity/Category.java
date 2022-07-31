package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * Created by Bad_sha 24/07/22
 */

/**
 * The persistent class for the category database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name = "Category")
public class Category implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;

	@Column(name = "CategoryName")
	private String categoryName;

	@Column(name = "CreatedDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDateTime;

	@Column(name = "CreatedUser")
	private String createdUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastModifiedDateTime")
	private Date lastModifiedDateTime;

	@Column(name = "LastModifiedUser")
	private String lastModifiedUser;
}