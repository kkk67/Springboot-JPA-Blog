package com.cos.blog.test;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		
		try {
		userRepository.deleteById(id);
	}catch (EmptyResultDataAccessException e) {
		return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
	}
		return "삭제되었습니다." + id;
	}
	
	// email,password
	@Transactional // save를 하지 않아도 업데이트가 됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) { // json 데이터를 요청 => Java object(MessageConverter의 Jacson 라이브러리가 변환해서 받아줌)
		System.out.println("id: " + id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());

		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//userRepository.save(user); 
		// save 함수는 id를 전달하지 않으면 insert를 해주고
		// id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
		// id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해줌
		
		//더티 체킹
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한 페이지당 2건에 데이터를 리턴 받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> list(@PageableDefault(size=1,sort="id",direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	// http://localhost:8000/blog/dummy/join
	// http의 body에 username,password,email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) { // key = value
		System.out.println("id: " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("createDate: " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	// {id} 주소로 파라미터를 전달받을 수 있음
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//1번 방법 : 없는 값을 리턴 받아서 찾으면 널값이 리턴되기 때문에 optional로 감싸서 가져와서 null인지 판단해서 return
		//2번 방법 : .orElseget()을 사용해 없는 id번호면 빈 User 객체를 만들어 오류는 발생시키지 않는 방법
		//3번 방법: .orElseThrow()를 사용해 없는 id번호면 오류메세지를 출력시키는 방법
		
		//람다식 new Supplier 없이 ()-> 으로 생략 가능함
//		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				// TODO Auto-generated method stub
//				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//			}
//		});
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 사용자는 없습니다.");
		});
		// 요청: 웹브라우저
		// user 객체 = 자바 오브젝트
		//변환 (웹 브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;
	}
}
