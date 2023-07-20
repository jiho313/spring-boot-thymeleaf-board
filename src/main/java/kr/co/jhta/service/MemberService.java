package kr.co.jhta.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kr.co.jhta.entity.Member;
import kr.co.jhta.exception.DuplicatedEmailException;
import kr.co.jhta.exception.DuplicatedMemberIdException;
import kr.co.jhta.repository.MemberRepository;
import kr.co.jhta.web.form.RegisterMemberForm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	
	public void registerUser(RegisterMemberForm registerMemberForm) {
		Optional<Member>optionalMember  = memberRepository.findById(registerMemberForm.getId());
//		사용자 정보(아이디)가 이미 존재한다면 개발자가 직접 만든 예외를 던짐
//		아이디로 사용자 정보 조회하기
		if(optionalMember.isPresent()) {
			throw new DuplicatedMemberIdException(registerMemberForm.getId());
		}
		
//		이메일로 사용자 정보 조회하기
		optionalMember = memberRepository.findByEmail(registerMemberForm.getEmail());
		if(optionalMember.isPresent()) {
			throw new DuplicatedEmailException(registerMemberForm.getEmail());
		}
		
		Member member = new Member();
		BeanUtils.copyProperties(registerMemberForm, member);
		
		memberRepository.save(member);
	}
	
	
}
