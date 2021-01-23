package com.pablo.bakeryManager.dto.category;

import java.util.HashMap;
import java.util.Map;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pablo.bakeryManager.annotation.TrueOrFalse;
import com.pablo.bakeryManager.annotation.UniqueToUpdate;
import com.pablo.bakeryManager.error.CategoryErrorMessage;
import com.pablo.bakeryManager.interf.FourthValidation;
import com.pablo.bakeryManager.interf.RequestDTO;
import com.pablo.bakeryManager.interf.SecondValidation;
import com.pablo.bakeryManager.interf.ThirdValidation;
import com.pablo.bakeryManager.model.Category;
import com.pablo.bakeryManager.validator.ValidatorUniqueCategory;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@GroupSequence({
	RequestUpdateCategoryDTO.class,
	SecondValidation.class,
	ThirdValidation.class,
	FourthValidation.class
})
public class RequestUpdateCategoryDTO implements RequestDTO {

	@NotNull
	@Positive(groups = SecondValidation.class)
	private Integer idCategory;
	
	@NotNull
	@NotEmpty(groups = SecondValidation.class)
	@Size(min = 3, max = 100, message = CategoryErrorMessage.SIZE_NAME, groups = ThirdValidation.class)
	private String name;
	
	@UniqueToUpdate(property = Category.NAME, uniqueDataValidator = ValidatorUniqueCategory.class, 
		message = CategoryErrorMessage.UNIQUE_NAME, groups = FourthValidation.class)
	private Map<String, Object> getName_() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.idCategory);
		map.put("value", this.name);
		
		return map;
	}
	
	@NotNull
	@TrueOrFalse(groups = SecondValidation.class)
	private Boolean visible;
	
	@SuppressWarnings("unchecked")
	@JsonProperty("category")
	@JsonIgnoreProperties(ignoreUnknown = true)
	private void getJsonProperties(Map<String, Object> category) {
	
		Map<String, Object> data = (Map<String, Object>) category.get("data");
		
		idCategory = (Integer) data.get("idCategory");
		name       = (String)  data.get("name");
		visible    = (Boolean) data.get("visible");
	}
	
	public Long getIdCategory() {
		return Long.valueOf(this.idCategory);
	}
}
