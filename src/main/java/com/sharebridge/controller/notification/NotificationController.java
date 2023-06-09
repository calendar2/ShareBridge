package com.sharebridge.controller.notification;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sharebridge.dto.MemberDto;
import com.sharebridge.dto.NotificationDto;
import com.sharebridge.param.NotificationListParam;
import com.sharebridge.service.NotificationService;

@Controller
public class NotificationController {
	@Autowired
	NotificationService notificationService;
	
	@GetMapping("/notification/notification_list.do")
	public String notificationListView(HttpSession session, Model model) {
		if(session.getAttribute("login") == null) {
			session.setAttribute("required", true);
			return "redirect:/login.do";
		}
		
		MemberDto memberInfo = (MemberDto) session.getAttribute("login");
		int member_id = memberInfo.getMember_id();
		
		NotificationListParam nlp = new NotificationListParam(member_id, 0, 0);
		List<NotificationDto> notificationList = notificationService.getNotificationList(nlp);
		
		System.out.println("notificationList => " + notificationList);
		
		model.addAttribute("notificationList", notificationList);
		
		return "notification_list";
	}
	
	@PostMapping("/notification/read_notification.do")
	public ResponseEntity<Void> readNotification(HttpSession session, int notification_id) {
		if(session.getAttribute("login") == null) {
			session.setAttribute("required", true);
			return ResponseEntity.status(300).header("Location", "/sharebridge/login.do").build();
		}
		
		notificationService.readNotification(notification_id);
		return ResponseEntity.status(200).build();
	}
	
	@PostMapping("/notification/delete_notification.do")
	public ResponseEntity<Void> deleteNotification(HttpSession session, int notification_id) {
		if(session.getAttribute("login") == null) {
			session.setAttribute("required", true);
			return ResponseEntity.status(300).header("Location", "/sharebridge/login.do").build();
		}
		
		notificationService.deleteNotificationByNotification_id(notification_id);
		
		return ResponseEntity.status(200).build();
	}
}
