package com.sharebridge.dao;

import java.util.List;

import com.sharebridge.dto.CategoryDto;
import com.sharebridge.dto.ProductDto;

public interface productDao {
	List<CategoryDto> allCategory();
	
	int insertProduct(ProductDto dto);
}
