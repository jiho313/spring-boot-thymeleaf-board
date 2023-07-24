package kr.co.jhta.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.jhta.dto.Pagination;
import kr.co.jhta.entity.Member;
import kr.co.jhta.entity.Post;
import kr.co.jhta.service.PostService;
import kr.co.jhta.web.form.AddPostForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
@Slf4j
public class PostController {

	private final PostService postService;
	
	@GetMapping("/list")
	public String list(@PageableDefault(page = 0,
										size = 10,
										sort = "no", direction = Direction.DESC) Pageable pageable,
			Model model) {
		
		log.info("페이징 -> {}", pageable);
		
		Page<Post> page = postService.getPosts(pageable);
		
		List<Post> posts = page.getContent();
		model.addAttribute("posts", posts);
		
		Pagination pagination = new Pagination(page.getNumber(), page.getSize(), page.getTotalPages());
		model.addAttribute("pagination", pagination);
		
		return "post/list";
	}
		
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/add")
	public String form(Model model) {
		model.addAttribute("addPostForm", new AddPostForm());
		
		return "post/form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add")
	public String add(@AuthenticationPrincipal Member member, @Valid AddPostForm addPostForm, BindingResult errors) {
		if(errors.hasErrors()) {
			return "post/form";
		}
		postService.addPost(addPostForm, member);
		
		return "redirect:list";
	}
}
