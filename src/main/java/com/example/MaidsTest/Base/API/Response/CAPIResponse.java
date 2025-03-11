package com.example.MaidsTest.Base.API.Response;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
public class CAPIResponse 
{
	private int statusCode;
	
	private String statusDescription;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private String successMessage;
	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private String errors;

	
	public CAPIResponse setStatus(HttpStatus status)
	{
		this.statusCode = status.value();
		this.statusDescription = status.name();
		
		return this;
	}
}
