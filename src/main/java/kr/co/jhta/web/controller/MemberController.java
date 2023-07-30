package kr.co.jhta.web.controller;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
/*
 * @SessionAttribures
 * 		- 이 어노테이션은 Model에 저장한 객체를 HttpSession객체로 옮긴다.
 * 		- names 속성에서 지정한 속성명을 참고해서 해당 속성명으로 Model객체에 저장되는 객체를
 * 		  HttpSession객체에 동일한 속성명으로 저장시킨다.
 * 		- Model에 저장된 객체는 최종적으로 HttpServletRequest객체의 속성으로 저장되는데,
 * 		  요청객체에 속성으로 저장된 것은 응답이 완료되면 전부 사라진다.
 * 		  Model에 저장된 객체를 세션객체의 속성으로 저장하면, 응답이 완료된 후에도 유지된다.
 * 
 * @ModelAttribute
 * 		- 이 어노테이션은 지정된 속성명으로 저장된 객체를 찾아서 반환한다.
 * 		- 이 어노테이션에서 지정한 속성명으로 저장된 객체를 발견하지 못하면 새로 만들어 반환한다.
 */
@SessionAttributes(names = {"registerMemberForm"})
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/login")
	public String loginform() {
		return "member/loginform";
	}
	
	/*
	 * 요청방식 : GET
	 * 요청URL 	: http://localhost/member/register-next
	 * 처리내용	: 회원가입폼의 입력정보를 저장하는 RegisterMemberForm객체를 생성하고,
	 * 			  Model객체에 저장한다.
	 */
	@GetMapping("/register-first")
	public String form1(Model model) {
		RegisterMemberForm form = new RegisterMemberForm();
		model.addAttribute("registerMemberForm", form);
		log.info("첫번째 단계 폼 객체의 해시코드 -> {}",form.hashCode());
		log.info("첫번째 단계 폼 객체 -> {}",form);
		
		return "member/form1";
	}
	
	/*
	 * 요청방식	: POST
	 * 요청URL	: http://localhost/member/register-next
	 * 처리내용 : RegisterMemberForm 객체를 요청 핸들러 메소드의 매개변수에 선언하고,
	 * 			  폼 입력값(아이디)을 전달받는다.
	 * 			  다음 입력화면으로 이동한다.
	 */
	@RequestMapping("/register-next")
	public String form2(RegisterMemberForm registerMemberForm,
			BindingResult errors) {
		
		// 폼 입력값 유효성 체크하기
		if (!StringUtils.hasText(registerMemberForm.getId())) {
			errors.rejectValue("id", null, "아이디는 필수 입력값입니다.");
			return "member/form1";
		}
		
		if (registerMemberForm.getId().length() < 3 || registerMemberForm.getId().length() > 20) {
			errors.rejectValue("id", null, "아이디는 3글자 이상 20글자 이하로 입력하세요.");
			return "member/form1";
		}

		log.info("두번째 단계 폼 객체의 해시코드 -> {}",registerMemberForm.hashCode());
		log.info("두번째 단계 - 폼 객체 -> {}", registerMemberForm);
		
		return "member/form2";
	}
	
	@PostMapping("/register")
	public String register(RegisterMemberForm registerMemberForm,
			BindingResult errors,
			SessionStatus sessionStatus,
			RedirectAttributes redirectAttributes) {
		
		log.info("세번째 단계 폼 객체의 해시코드 -> {}",registerMemberForm.hashCode());
		log.info("세번째 단계 - 폼 객체 -> {}", registerMemberForm);
		
		if(!StringUtils.hasText(registerMemberForm.getPassword())) {
			errors.rejectValue("password", null, "비밀번호는 필수 입력값입니다.");
		}
		if (registerMemberForm.getPassword().length() < 2 || registerMemberForm.getPassword().length() > 4) {
			errors.rejectValue("password", null, "비밀번호는 2글자 이상 4글자 이하로 입력하세요.");
		}
		if(!StringUtils.hasText(registerMemberForm.getName())) {
			errors.rejectValue("name", null, "이름은 필수 입력값입니다.");
		}
		if(!StringUtils.hasText(registerMemberForm.getEmail())) {
			errors.rejectValue("email", null, "이메일은 필수 입력값입니다.");
		}
		if (errors.hasErrors()) {
			return "member/form2";
		}
		
		try {
			memberService.registerUser(registerMemberForm);
		} catch (DuplicatedMemberIdException ex) {
			errors.rejectValue("id", null, "사용할 수 없는 아이디입니다.");
			return "member/form1";
		} catch (DuplicatedEmailException ex) {
			errors.rejectValue("email", null, "사용할 수 없는 이메일입니다.");
			return "member/form2";
		}
		
		UserDetails userDetails = memberService.loadUserByUsername(registerMemberForm.getId());
		
		// addFlashAttribute 리다이렉트 한 바로 다음 페이지까지 사용하고 사라지는 객체
		redirectAttributes.addFlashAttribute("user", userDetails);
		
		// 사용한 세션객체 파괴
		sessionStatus.setComplete();
		
		return "redirect:/member/registered";
	}
	
	@GetMapping("/registered")
	public String registered() {
		
		return "member/registered";
	}
	
}

