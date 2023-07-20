package kr.co.jhta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.jhta.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
	
	
	/*
	 * JPA에 정의된 쿼리문이 없다면 직접 정의한다.
	 * select *
	 * from members
	 * where email = ?
	 */
	Optional<Member> findByEmail(String email); 
	
}
