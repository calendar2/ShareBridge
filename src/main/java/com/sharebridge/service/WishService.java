package com.sharebridge.service;

import java.util.List;

import com.sharebridge.dto.ProductDto;
import com.sharebridge.dto.WishDto;

public interface WishService {
	List<ProductDto> getAllWishByMemberId(int memberId);
	WishDto selectOneWish(WishDto wishDto);
	int addWish(WishDto wishDto);
	int removeWish(WishDto wishDto);
}
