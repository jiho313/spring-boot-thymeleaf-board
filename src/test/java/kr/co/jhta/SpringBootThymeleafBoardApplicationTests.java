package kr.co.jhta;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.jhta.entity.Member;
import kr.co.jhta.repository.MemberRepository;

@SpringBootTest
class SpringBootThymeleafBoardApplicationTests {

	@Autowired
	MemberRepository memberRepository;

	/*
	 * @BeforEach
	 * 		- 테스트에 대한 사전작업이 정의된 메소드에 지정한다.
	 * 		- 이 어노테이션이 지정된 메소드에 구현된 내용은
	 * 		  테스트 메소드가 실행될 때 마다 매번 실행된다.
	 */
	@BeforeEach
	public void setup() {
		memberRepository.deleteAll();
	}
	
	
	/*
	 * @DisplayName
	 * 		- 테스트 결과를 표시하는 창에 출력될 테스트 이름을 지정한다.
	 * @Test
	 * 		- 테스트 메소드를 지정한다.
	 * @Disabled
	 * 		- 테스트 메소드의 실행을 취소한다.
	 */
	@DisplayName("saveMember: 멤버추가에 성공한다.")
	@Test
//	@Disabled
	public void testSaveMember() {
		
		// 준비하기
		Member member = new Member();
		member.setId("hong");
		member.setPassword("zxcv1234");
		member.setName("홍길동");
		member.setEmail("hong@gmail.com");
		member.setTel("010-1234-5678");
		
		// 실행하기
		memberRepository.save(member);
		
		// 검증하기
		Member savedMember = memberRepository.findById("hong")
											 .orElseThrow(() -> new RuntimeException("hong"));
		
		assertThat(savedMember.getId()).isNotNull();
		assertThat(savedMember.getId()).isEqualTo("hong");
		assertThat(savedMember.getName()).isEqualTo("홍길동");
		
		
	}
}
