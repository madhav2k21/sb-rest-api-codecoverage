package com.techleads.app.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String location;
	
	@ValidateEmptyStatusCode()
	@Transient
	private String loadEmptyStatusCode;
	@Transient
	@ValidateListEmptyStatusCodes()
	private List<String> loadEmptyStatusCodes;
	@Transient
	@ValidateListRouteSequenceCodes()
	private List<Route> routes;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", timezone = "Asia/Kolkata" )
	private Timestamp createdDateTime;
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss",timezone = "Asia/Kolkata")
	private Timestamp updatedDateTime;
	public Users(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}
	
	

}
