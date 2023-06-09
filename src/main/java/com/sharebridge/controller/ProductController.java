package com.sharebridge.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sharebridge.dto.CategoryDto;
import com.sharebridge.dto.MemberDto;
import com.sharebridge.dto.ProductDto;
import com.sharebridge.dto.ReviewDto;
import com.sharebridge.dto.WishDto;
import com.sharebridge.service.MemberService;
import com.sharebridge.service.ProductService;
import com.sharebridge.service.QuestionService;
import com.sharebridge.service.ReviewService;
import com.sharebridge.service.WishService;
import com.sharebridge.util.FileUtil;

@Controller
public class ProductController {
	@Autowired
	ProductService service;
	@Autowired
	MemberService memberService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	QuestionService questionService;
	@Autowired
	WishService wishService;
	
	// 상품등록
	@GetMapping(value = "productRegi.do")
	public String productRegi(Model model, HttpSession session) {
		if(session.getAttribute("login") == null) {
			session.setAttribute("required", true);
			return "redirect:/login.do";
		}
		
		return "productRegi";
	}
	
	@PostMapping(value = "productRegiAf.do")
	public String productRegiAf(ProductDto dto,
								@RequestParam(value="fileload", required=false)
								MultipartFile fileload,
								HttpServletRequest req,
								@RequestParam(value="start", required=false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
								LocalDateTime sdate,
								@RequestParam(value="end", required=false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
								LocalDateTime edate,
								Model model) {
		dto.setSdate(Timestamp.valueOf(sdate));
		dto.setEdate(Timestamp.valueOf(edate));
		
		// filename 취득(원본)
		String filename = fileload.getOriginalFilename();
		
		// upload 경로
		// folder -> 일단 로컬에 저장, 나중에 서버에 저장으로 변경할 것
//		String fupload = "C:\\upload";
		
		// server
		String fupload = req.getServletContext().getRealPath("/upload/product");
		
		// 파일명을 충돌되지 않는 명칭으로 변경
		String newfilename = FileUtil.getNewFileName(filename);
		
		dto.setPhoto(newfilename);	// DB에 파일 경로 저장 
		
		// 파일 생성
		File file = new File(fupload + "/" + newfilename);
		System.out.println("file path: " + file);

		try {			
			// DB에 저장
			boolean isS = service.insertProduct(dto);
			String msg = "PRODUCT_INSERT_OK";
			if(isS) {
				// 파일 업로드
				FileUtils.writeByteArrayToFile(file, fileload.getBytes());
			} else {
				msg = "PRODUCT_INSERT_NO";
			}
			model.addAttribute("insertProduct", msg);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "detailsMsg";
	}
	
	// 수정페이지로 이동
	@GetMapping("goUpdate.do")
	public String goUpdate(Model model, int product_id, int category_id) {
		List<CategoryDto> allCategory = service.getAllCategory();
		ProductDto detail = service.getProduct(product_id);
		
		model.addAttribute("allCategory", allCategory);
		model.addAttribute("detail", detail);
		model.addAttribute("cid", category_id);
		
		return "productUpdate";
	}
	
	// 상품 수정
	@PostMapping("/updateProduct.do")
	public String updateProduct(ProductDto dto,
								@RequestParam(value="fileload", required=false)
								MultipartFile fileload,
								HttpServletRequest req,
								@RequestParam(value="start", required=false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
								LocalDateTime sdate,
								@RequestParam(value="end", required=false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
								LocalDateTime edate,
								Model model) {
		
		dto.setSdate(Timestamp.valueOf(sdate));
		dto.setEdate(Timestamp.valueOf(edate));
		
		// 사진 수정
		if(fileload.isEmpty()) {	// 기존의 사진
			System.out.println("fileload is empty");
			String originalFile = service.getProductImg(dto.getProduct_id());
			dto.setPhoto(originalFile);
			
		} else {	// 수정된 사진 저장
			System.out.println("fileload is not empty");
			// filename 취득(원본)
			String filename = fileload.getOriginalFilename();
			
			// upload 경로
			// folder -> 일단 로컬에 저장, 나중에 서버에 저장으로 변경할 것
			//String fupload = "C:\\upload";
			
			// server
			String fupload = req.getServletContext().getRealPath("/upload/product");
			
			// 파일명을 충돌되지 않는 명칭으로 변경
			String newfilename = FileUtil.getNewFileName(filename);
			
			dto.setPhoto(newfilename);	// DB에 파일 경로 저장 
			
			// 파일 생성
			File file = new File(fupload + "/" + newfilename);
			
			try {			
				// 파일 업로드
				FileUtils.writeByteArrayToFile(file, fileload.getBytes());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// DB에 저장
		boolean isS = service.updateProduct(dto);
		String msg = "PRODUCT_UPDATE_OK";
		if(!isS) {					
			msg = "PRODUCT_UPDATE_NO";
		}
		
		model.addAttribute("updateProduct", msg);
		model.addAttribute("pid", dto.getProduct_id());
		model.addAttribute("cid", dto.getCategory_id());
		
		return "detailsMsg";	
	}
		
	// 상품 상세 보기
	@GetMapping("/productDetail.do")
	public String productDetail(int product_id, int category_id, Model model, HttpSession session) {
		if(session.getAttribute("login") == null) {
			session.setAttribute("required", true);
			return "redirect:/login.do";
		}
		
		MemberDto login = (MemberDto) session.getAttribute("login");
		
		// 상품 정보
		ProductDto detail = service.getProduct(product_id);
		CategoryDto getCate = service.getCate(category_id);
		
		// 위시 정보
		WishDto wishDto = new WishDto(product_id, login.getMember_id(), null);
		// 로그인한 사용자가 이제 보게 될 상품을 위시리스트에 등록했다면 WishDto가 반환됨
		// 로그인한 사용자가 이제 보게 될 상품을 위시리스트에 등록하지 않았다면 null이 반환됨
		wishDto = wishService.selectOneWish(wishDto);
		
		// 렌터 정보
		MemberDto renter = memberService.selectOneByMemberId(detail.getMember_id());
		List<ReviewDto> reviewList = service.getReviewList(renter.getMember_id());
		
		// 리뷰를 작성한 렌티 닉네임 취득
		List<String> r_renteeNickList = new ArrayList<>();
		if(reviewList.size() != 0) {
			int size = 4;
			if(reviewList.size() < 4) {
				size = reviewList.size();
			}
			
			for(int i=0; i<size; i++) {
				ReviewDto r = reviewList.get(i);
				String r_nick = reviewService.reviewListThree(r.getRentee_id());
				r_renteeNickList.add(r_nick);
			}
		}
		
		int questionCount = questionService.getQuestionCount(product_id);
		
		model.addAttribute("detail", detail);
		model.addAttribute("getCate", getCate);
		model.addAttribute("renter", renter);
		model.addAttribute("review", reviewList);
		model.addAttribute("r_renteeNick", r_renteeNickList);
		model.addAttribute("questionCount", questionCount);
		model.addAttribute("isWish", wishDto != null);
		
		return "productDetail";
	}
	
	// 상품 삭제 : 데이터는 그대로 보존
	@GetMapping("/delProduct.do")
	public String delProduct(int product_id, Model model) {
		boolean isS = service.delProduct(product_id);
		String msg = "PRODUCT_DELETE_OK";
		if(!isS) {			
			msg = "PRODUCT_DELETE_NO";
		}
		
		model.addAttribute("delProduct", msg);
		
		return "detailsMsg";
	}
	
	// 상품 대여 폼으로 이동
	@GetMapping("/goRequestFrm.do")
	public String goRequestFrm(int product_id, Model model) {
		ProductDto detail = service.getProduct(product_id);
		model.addAttribute("detail", detail);
		
		return "requestFrm";
	}
	
	// 상품 관리 페이지
	@RequestMapping(value="productList.do", method=RequestMethod.GET)
	public String productManage(int member_id, Model model) {
		List<ProductDto> productList = service.getProductListForRenter(member_id);
		model.addAttribute("productList", productList);
		
		return "productManage";
	}
}
