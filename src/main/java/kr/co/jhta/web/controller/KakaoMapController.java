package kr.co.jhta.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map")
public class KakaoMapController {
	
	@GetMapping("/search")
	public String search() {
		
		return "map/search";
	}

	@GetMapping("/image")
	public String image() {
		
		return "map/image";
	}
	
	@GetMapping("/test")
	public String test() {
		return "map/placeId";
	}
}
