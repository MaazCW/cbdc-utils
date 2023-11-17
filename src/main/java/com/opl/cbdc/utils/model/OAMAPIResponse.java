package com.opl.cbdc.utils.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class OAMAPIResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String referenceId;
	private Integer status;
	private String errorMessage;

	public OAMAPIResponse(Integer status, String errorMessage) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
	}
	
	public OAMAPIResponse(String referenceId, Integer status, String errorMessage) {
		super();
		this.referenceId = referenceId;
		this.status = status;
		this.errorMessage = errorMessage;
	}

}
