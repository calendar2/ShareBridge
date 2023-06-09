<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/sharebridge/css/notification/notification_list.css">

<style>
	#button_wrap {
		z-index: 1;
	}
</style>

<main>
	<div id="title_wrap">
		<h1 id="page_title">알림</h1>
	</div>
	
	<div id="list_wrap">
		<c:if test="${empty notificationList }">
			<p id="no_notification">**내개 온 알림이 없습니다</p>
		</c:if>
		<c:if test="${not empty notificationList }">
			<c:forEach items="${notificationList }" var="notification">
			
				<c:set var="readText" value="읽음" />
				<c:if test="${notification.read eq 0 }">
					<c:set var="className" value="noread" />
					<c:set var="readText" value="안읽음" />
				</c:if>
			
			<div class="list ${className }" notification_id="${notification.notification_id }">
				<h3>대여신청</h3>
				<p>${notification.rdate }</p>
				<div id="text_button_wrap">
					<p>'${notification.renteeNickname }'님이 '${notification.productName }' 대여를 희망합니다</p>
					<div id="button_wrap">
						<span>${readText }</span>
						<span class="delete_btn">X</span>
					</div>
				</div>
			</div>
			</c:forEach>
		</c:if>
	</div>
</main>

<script src="/sharebridge/js/public/common.js"></script>
<script>
	$(".noread").on("click", function() {
		let notification_id = $(this).attr("notification_id");
		
		let $this = $(this);
		
		$.ajax({
			url: "/sharebridge/notification/read_notification.do",
			type: "POST",
			data: "notification_id="+notification_id,
			success: function() {
				$this.removeClass("noread");
				$this.off("click");
				
				let $readText = $this.children("#text_button_wrap").children("#button_wrap").children().eq(0);
				$readText.html("읽음");
			},
			error: function(xhr){
				if(xhr.status == 300) {
					goTo(xhr.responseHeader("Location"));
				}
			}
		});
	});
	
	$(".delete_btn").on("click", function(e) {
		let $list = $(this).parent().parent().parent();
		let notification_id = $list.attr("notification_id");
		
		$.ajax({
			url: "/sharebridge/notification/delete_notification.do",
			type: "POST",
			data: "notification_id="+notification_id,
			success: function() {
				$list.remove();
			},
			error: function(xhr) {
				if(xhr.status == 300) {
					goTo(xhr.responseHeader("Location"));
				}
			}
		});
		
		e.stopPropagation();
	});
</script>