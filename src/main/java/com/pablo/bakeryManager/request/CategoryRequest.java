package com.pablo.bakeryManager.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.pablo.bakeryManager.filter.Filter;
import com.pablo.bakeryManager.filter.FilterSpecification;
import com.pablo.bakeryManager.interf.GetRequest;
import com.pablo.bakeryManager.model.Category;
import com.pablo.bakeryManager.validator.Validator;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequest implements GetRequest<Category> {

	private Validator validator = new Validator();
	private PageAndSortRequest pageAndSortRequest = new PageAndSortRequest();

	private String name;
	private Boolean visible;
	private String sort;
	private String direction;
	private int page;
	private int size;
	
	public CategoryRequest() {
		this.name = "";
		this.visible = null;
		this.sort = "";
		this.direction = "";
		this.page = 0;
		this.size = 0;
	}
	
	public Boolean parameterNameHasText() {
		return validator.hasText(this.name);
	}
	
	public Boolean parameterVisibleNotNull() {
		return validator.notNull(this.visible);
	}
	
	public Boolean parameterSortHasText() {
		return validator.hasText(this.sort);
	}
	
	public Boolean parameterDirectionHasText() {
		return validator.hasText(this.direction);
	}
	
	public Boolean parameterSizeIsPositive() {
		return validator.positiveNumber(this.size);
	}

	@Override
	public Boolean hasFilterParameters() {
		return this.parameterNameHasText() || this.parameterVisibleNotNull();
	}

	@Override
	public Boolean hasSortParameters() {
		return this.parameterSortHasText() & this.parameterDirectionHasText();
	}

	@Override
	public Boolean hasPagingParameters() {
		return this.parameterSizeIsPositive();
	}

	@Override
	public Boolean hasFilterAndSortParameters() {
		return this.hasFilterParameters() && this.hasSortParameters();
	}

	@Override
	public Boolean hasFilterAndPagingParameters() {
		return this.hasFilterParameters() && this.hasPagingParameters();
	}

	@Override
	public Boolean hasPagingAndSortParameters() {
		return this.hasPagingParameters() && this.hasSortParameters();
	}

	@Override
	public Boolean hasFilterAndPagingAndSortParameters() {
		return this.hasFilterParameters() && this.hasPagingParameters() && this.hasSortParameters();
	}

	@Override
	public FilterSpecification<Category> getFilterSpecification() {
		
		FilterSpecification<Category> filterSpecification = new FilterSpecification<Category>();
		List<Filter> filters = new ArrayList<Filter>();
		
		if (this.parameterNameHasText()) filters.add(new Filter(Category.NAME, this.name));
		if (this.parameterVisibleNotNull()) filters.add(new Filter(Category.VISIBLE, this.visible));		
		
		filterSpecification.addFilters(filters);
		return filterSpecification;
	}

	@Override
	public PageRequest getPaging() {
		return pageAndSortRequest.getPaging(this.page, this.size);
	}

	@Override
	public Sort getSort() {
		return pageAndSortRequest.getSort(this.sort, this.direction);
	}

	@Override
	public PageRequest getPagingAndSort() {
		return pageAndSortRequest.getPagingAndSort(this.page, this.size, this.sort, this.direction);
	}
}
