// flatpickr
let sdate = document.getElementById("start").value;
let edate = document.getElementById("end").value;

$(document).ready(function() {
	$("#start").flatpickr({
		dateFormat: "Y-m-d",
		minDate : new Date().fp_incr(1) // 날짜는 다음날부터 선택
	});
	
	$("#end").flatpickr({
		dateFormat: "Y-m-d",
		minDate : new Date().fp_incr(2)
	});
	
	$("#start").change(function(e) {
		sdate = e.target.value;	
		console.log(sdate);
	});
	
	$("#end").change(function(e) {
		edate = e.target.value
		console.log(edate);
	});
	
	$("#test").click(function() {
		console.log("click");
		console.log($("#sdate").val());    	
	});
	    
	// 빈칸검사
	$("#regiBtn").click(function(e) {
		e.preventDefault();
		if($("#choice").val().trim() == "") {
			alert("카테고리를 선택해주세요");
			return;
		} else if($("#title").val().trim() == "") {
			e.preventDefault;
			alert("제목(상품명)을 입력해주세요");
			return;
		} else if(sdate == null || sdate == "") {
			e.preventDefault;
			alert("시작날짜를 선택해주세요");
			return;
		} else if(edate == null || edate == "") {
			e.preventDefault;
			alert("마지막날짜를 선택해주세요")
		} else if($("#price").val().trim() == "") {
			e.preventDefault;
			alert("가격을 입력해주세요");
		} else if($("#content").val().trim() == "") {
			e.preventDefault;
			alert("상품에 대한 내용을 입력해주세요");
		} else {
			$("#frm").submit();
		}
	});
}); 

// 사진 미리보기
const fileInput = document.getElementById("file");
const preview = document.getElementById("preview");
var prevURL, currentURL;

function changeImg() {
  URL.revokeObjectURL(prevURL);
  const selectedFile = fileInput.files[0];
  if (selectedFile) {
    currentURL = URL.createObjectURL(selectedFile);
    preview.src = currentURL;
    prevURL = currentURL;
  }
};

function deleteImg() {
	const selectedFile = fileInput.files[0];
    if (selectedFile) {
    	currentURL = URL.createObjectURL(selectedFile);
    	URL.revokeObjectURL(currentURL);
		
    	preview.src="https://i0.wp.com/adventure.co.kr/wp-content/uploads/2020/09/no-image.jpg";
    }  
}