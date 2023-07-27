package kr.co.jhta.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("msg", "타임리프로 제작한 홈화면입니다.");
		return "home";
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		model.addAttribute("msg", "타임리프로 제작한 홈화면입니다.");
		return "home2";
	}
	
	@GetMapping("/map")
	public String mapimage(Model model) {
		model.addAttribute("msg", "타임리프로 제작한 홈화면입니다.");
		return "map/image";
	}
}
