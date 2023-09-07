package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/test/user")
	public Member get_user(@RequestBody Member member) {
		
		System.out.println("유저번호: "+member.getId());
		System.out.println("id: "+member.getUsername());
		System.out.println("pw: "+member.getPassword());
		System.out.println("email "+member.getEmail());
		
		return member;
		
	}
}
