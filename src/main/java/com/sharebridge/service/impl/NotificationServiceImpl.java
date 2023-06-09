package com.sharebridge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharebridge.dao.NotificationDao;
import com.sharebridge.dto.NotificationDto;
import com.sharebridge.param.NotificationListParam;
import com.sharebridge.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationDao dao;

	@Override
	public int getNotificationCountByMemberId(int memberId) {
		return dao.getNotificationCountByMemberId(memberId);
	}

	@Override
	public List<NotificationDto> getNotificationList(NotificationListParam nlp) {
		return dao.getNotificationList(nlp);
	}

	@Override
	public void readNotification(int notification_id) {
		dao.readNotification(notification_id);
	}

	@Override
	public void deleteNotificationByNotification_id(int notification_id) {
		dao.deleteNotificationByNotification_id(notification_id);
	}

	@Override
	public boolean requestNoti(int request_id) {
		int n = dao.requestNoti(request_id);
		return n>0?true:false;
	}
}
