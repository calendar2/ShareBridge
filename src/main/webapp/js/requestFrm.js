
let sdate = document.getElementById("start").placeholder;
let edate = document.getElementById("end").placeholder;

range(sdate, edate);
			
// flatpickr
function range(sdate, edate) {
	const sfp = flatpickr("#start", {
		dateFormat: "Y-m-d",
		enable : [{
			from: sdate,
			to: edate
		}],
	});
	const efp = flatpickr("#end", {
		dateFormat: "Y-m-d",
		enable : [{
			from: sdate,
			to: edate
		}]
	});	
}

function diffDate(sdate, edate) {
	const d1 = new Date(sdate);
  	const d2 = new Date(edate);
  
	const diff = d1.getTime() - d2.getTime();
  	
  	return Math.abs(diff / (1000 * 60 * 60 * 24));	
}

$(document).ready(function() {
	$(".selector").on('focus', function(currentTarget) {$(currentTarget).blur()});
	$(".selector").prop('readonly', false);	
	
	// 총 결제금액
	$(".selector").change(function() {
		let s = $("#start").val();	// 시작일
		let e = $("#end").val();	// 마지막일
		
		let total = $("#price").val() * diffDate(s,e);
		$("#total").val(total);
	});
	
	// 대여기간 설정
	// 작성,수정 시 날짜에 변동이 있으면 변동된 입력값을 넣어준다
	$("#start").change(function() {
		let convert_s = new Date($("#start").val()).toISOString().slice(0,19);
		$("#startDate").val(convert_s);
	});
	
	$("#end").change(function() {
		let convert_e = new Date($("#end").val()).toISOString().slice(0,19);
		$("#endDate").val(convert_e);	
	});
	
	// 수령자 정보
	$("#same_chk").click(function() {
		if($("#same_chk").is(":checked")) {
			let name = $("#name").val();
			let phone = $("#phone_number").val();
			
			$("#receiver").val(name);
			$("#receiver_phone").val(phone);			
		} else {
			$("#receiver").val("");
			$("#receiver_phone").val("");
		}
	});
	
	// 입력된 주소 저장
	if($(".addAddress") != undefined) {	// 입력값이 있을경우
		setAddress();
	}
	
	$(".addAddress").change(function() { // 입력값이 변할경우
		setAddress();
	});
	
	// 빈칸검사
	$.fn.blankChk = function(path) {
		if($("#name").val() == undefined || $("#name").val().trim() == "") {
			alert("대여자명을 입력해주세요");
			return;
		} else if($("#email").val() == undefined || $("#email").val().trim() == "") {
			alert("이메일을 입력해주세요");
			return;
		} else if($("#phone_number").val() == undefined || $("#phone_number").val().trim() == "") {
			alert("휴대전화 번호를 입력해주세요");
			return;
		} else if($("#start").val() == undefined || $("#end").val().trim() == "") {
			alert("시작일을 선택해주세요");
			return;
		} else if($("#end").val() == undefined || $("#end").val().trim() == "") {
			alert("마지막일을 선택해주세요");
			return;
		} else if($("#receiver").val() == undefined || $("#receiver").val().trim() == "") {
			alert("수령자명을 입력해주세요");
			return;
		} else if($("#receiver_phone").val() == undefined || $("#receiver").val().trim() == "") {
			alert("수령자 휴대전화 번호를 입력해주세요");
			return;
		} else if($("#postcode").val() == undefined || $("#postcode").val().trim() == "") {
			alert("우편번호를 조회해주세요");
			return;
		} else if($("#detailAddress").val() == undefined || $("#detailAddress").val().trim() == "") {
			alert("상세주소를 입력해주세요");
			return;
		} else {
			let a = $("#frm").serializeArray();
			console.log(a);
			$("#frm").attr("action",path).submit();
		}
	}
	
	$("#regiBtn").click(function(e) {
		e.preventDefault();
		
		let path = "requestFrmAf.do?choice='req'";
		$.fn.blankChk(path);
	});
	
	$("#shopping_cart").click(function(e) {
		e.preventDefault();
		
		let path = "requestFrmAf.do?choice='cart";
		$.fn.blankChk(path);
	});
});

// 주소찾기
function findPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
            
            } 

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
            
        }
    }).open();
    
}

function setAddress() {
	let postcode = $('#postcode').val();
	let address = $("#address").val();
	let detail = $("#detailAddress").val();
	
	$("#saveAddress").val(postcode+"/"+address+"/"+detail);
}

