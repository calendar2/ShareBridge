package com.sharebridge.service;

import java.util.List;

import com.sharebridge.dto.CategoryDto;
import com.sharebridge.dto.ProductDto;
import com.sharebridge.dto.QuestionDto;
import com.sharebridge.dto.ReviewDto;
import com.sharebridge.param.BaseLayoutParam;

public interface ProductService {
	List<CategoryDto> getAllCategory();
	
	boolean insertProduct(ProductDto dto);
	
	int getProductCountByMemberId(int memberId);
	
	List<ProductDto> getAllProducts(BaseLayoutParam bp);
	
	boolean updateProduct(ProductDto dto);
	
	ProductDto getProduct(int product_id);
	
	CategoryDto getCate(int category_id);
	
	boolean delProduct(int product_id);
	
	List<ReviewDto> getReviewList(int renter_id);
	
	List<QuestionDto> getQuestionList();
	
	List<ProductDto> getProductListForRenter(int member_id);
	
	String getProductImg(int product_id);
}
