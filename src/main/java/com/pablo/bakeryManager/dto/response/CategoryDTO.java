package com.pablo.bakeryManager.dto.response;

import com.pablo.bakeryManager.interf.ResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CategoryDTO implements ResponseDTO {

	private Long id;
	private String name;
	private Boolean visible;
}