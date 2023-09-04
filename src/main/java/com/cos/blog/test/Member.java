package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter //게터 자동생성
//@Setter //세터 자동생성
@Data // 게터 & 세터 자동생성
@NoArgsConstructor // 빈 생성자
//@AllArgsConstructor
//@RequiredArgsConstructor // final 자동생성
public class Member {
	private  int id; // 데이터베이스에서 들고온 값을 변경하지 않아도 됨(불변성)
	private  String username;
	private  String password;
	private  String email;
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	
}
