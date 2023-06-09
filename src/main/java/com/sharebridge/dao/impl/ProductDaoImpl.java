package com.sharebridge.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sharebridge.dao.ProductDao;
import com.sharebridge.dao.ReviewDao;
import com.sharebridge.dto.CategoryDto;
import com.sharebridge.dto.ProductDto;
import com.sharebridge.dto.QuestionDto;
import com.sharebridge.dto.ReviewDto;
import com.sharebridge.param.BaseLayoutParam;

@Repository
public class ProductDaoImpl implements ProductDao {
	@Autowired
	SqlSession session;
	
	String c_ns = "Category.";
	String r_ns = "Review.";
	String q_ns = "Question.";
	String ns = "Product.";	
	
	@Override
	public List<CategoryDto> getAllCategory() {
		return session.selectList(c_ns + "getAllCategory");
	}

	@Override
	public int insertProduct(ProductDto dto) {
		return session.insert(ns + "insertProduct", dto);
	}
	
	@Override
	public int getProductCountByMemberId(int memberId) {
		return session.selectOne(ns + "getProductCountByMemberId", memberId);
	}

	@Override
	public List<ProductDto> getAllProducts() {
		return session.selectList(ns + "getAllProducts");
	}
	
	@Override
	public List<ProductDto> getCategoryProducts(int category_id) {
		return session.selectList(ns + "getCategoryProducts", category_id);
	}
	
	@Override
	public List<ProductDto> getTermProducts(String term) {
		return session.selectList(ns + "getTermProducts", term);
	}

	@Override
	public List<ProductDto> getCategoryTermProducts(BaseLayoutParam bp) {
		return session.selectList(ns + "getCategoryTermProducts", bp);
	}

	@Override
	public int updateProduct(ProductDto dto) {
		return session.update(ns + "updateProduct", dto);
	}

	@Override
	public ProductDto getProduct(int product_id) {
		return session.selectOne(ns + "getProduct", product_id);
	}
	
	@Override
	public ProductDto getProductByProduct_id(int product_id) {
		return session.selectOne(ns + "getProduct", product_id);
	}

	@Override
	public CategoryDto getCate(int category_id) {
		return session.selectOne(c_ns + "getCate", category_id);
	}

	@Override
	public int delProduct(int product_id) {
		return session.update(ns + "delProduct", product_id);
	}
	
	@Override
	public void updateStateTo1(int product_id) {
		session.update(ns + "updateStateTo1", product_id);
	}

	@Override
	public void updateStateTo0(int product_id) {
		session.update(ns + "updateStateTo0", product_id);
	}

	@Override
	public List<ReviewDto> getReviewList(int renter_id) {
		return session.selectList(r_ns + "getReviewList", renter_id);
	}

	@Override
	public List<QuestionDto> getQuestionList() {
		return session.selectList(q_ns + "getQuestionList");
	}

	@Override
	public List<ProductDto> getProductListForRenter(int member_id) {
		return session.selectList(ns + "getProductListForRenter", member_id);
	}

	@Override
	public String getProductImg(int product_id) {
		return session.selectOne(ns + "getProductImg", product_id);
	}
}
