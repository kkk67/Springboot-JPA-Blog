package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	private PrincipalDetail principal;
	
	@GetMapping({"/",""})
	public String index(@AuthenticationPrincipal PrincipalDetail principal) {
		// /WEB-INF/views/index.jsp
		//System.out.println("로그인 사용자 아이디 : " + principal.getUsername());
		return "index";
	}
}
