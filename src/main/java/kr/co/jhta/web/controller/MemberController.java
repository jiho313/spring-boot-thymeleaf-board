package kr.co.jhta.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.jhta.exception.DuplicatedEmailException;
import kr.co.jhta.exception.DuplicatedMemberIdException;
import kr.co.jhta.service.MemberService;
import kr.co.jhta.web.form.RegisterMemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/register")
	public String form(Model model) {
		// 회원가입 폼 화면으로 이동하기 전에 RegisterMemberForm객체를 생성해서 Model객체에 저장한다.
		// 화면에서는 Model객체에 "registerMemberForm"이름으로 등록된 객체를 조회해서
		// 입력폼의 각 필드에 값을 출력한다. - 오류가 나서 다시 form으로 되돌아오는 경우를 고려해서
		model.addAttribute("registerMemberForm", new RegisterMemberForm());
		return "member/form";
	}
	
	@PostMapping("/register")
	/*
	 * @Valid
	 * 		- 유효성 검사할 객체 앞에 지정한다. 
	 * BindingResult
	 * 		- 검사한 객체 바로 뒤에 적는다.
	 * 		- 유효성 체크를 통과하지 못했을 경우 출력되는 에러가 들어있다.
	 */
	public String register(@Valid RegisterMemberForm registerMemberForm, BindingResult errors) {
		log.info("errors -> {}", errors);
		if(errors.hasErrors()) {
//			폼 안에 든 정보들이 그대로 다시 전달되어야 하기 때문에 리다이렉트가 아닌
//			내부 이동으로 처리한다.
			return "member/form";
		}
		try {
			memberService.registerUser(registerMemberForm);
		} catch (DuplicatedMemberIdException ex) {
			errors.rejectValue("id", null, "이미 사용중인 아이디입니다.");
			return "member/form";
		} catch (DuplicatedEmailException ex) {
			errors.rejectValue("email", null, "이미 사용중인 이메일입니다.");
			return "member/form";
		}
		
		return "redirect:registered";
	}
	
	@GetMapping("/registered")
	public String registered() {
		
		return "member/registered";
	}
	
}

