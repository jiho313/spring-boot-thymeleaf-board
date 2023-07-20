package kr.co.jhta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.jhta.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
	
	
}
