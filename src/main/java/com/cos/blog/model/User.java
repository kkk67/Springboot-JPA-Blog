package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM -> JAVA(다른언어) object -> 테이블로 매핑해주는 기술
 // user클래스가 자동으로 mysql에 테이블이 생성이 된다.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
// @DynamicInsert   insert시에 null인 필드를 빼줌
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스,auto_increment
	
	@Column(nullable = false,length = 20,unique = true)
	private String username; // 아이디
	
	@Column(nullable = false,length = 100) // 123456 => 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable = false,length = 50)
	private String email;
	
	//@ColumnDefault("'user'")
	//DB는 RoleType이 없음
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. admin,user,manager String이 아니라 셋 중 하나만 넣을 수 있음 (오류방지)
	
	@CreationTimestamp // 시간이 자동 입력
	private Timestamp createDate;
}
