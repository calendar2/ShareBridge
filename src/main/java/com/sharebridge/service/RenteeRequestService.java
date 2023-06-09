package com.sharebridge.service;

import java.util.List;

import com.sharebridge.dto.RequestDto;
import com.sharebridge.param.MypageParam;

public interface RenteeRequestService {
	List<RequestDto> getRequestListByMemberId(MypageParam mp);
	String cancelRequest(int member_id, int request_id);
}
