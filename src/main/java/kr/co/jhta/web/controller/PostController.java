package kr.co.jhta.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.jhta.entity.Member;
import kr.co.jhta.entity.Post;
import kr.co.jhta.service.PostService;
import kr.co.jhta.web.form.AddPostForm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

	private final PostService postService;
	
	@GetMapping("/list")
	public String list(Model model) {
		
		List<Post> posts = postService.getPosts();
		model.addAttribute("posts", posts);
		
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
