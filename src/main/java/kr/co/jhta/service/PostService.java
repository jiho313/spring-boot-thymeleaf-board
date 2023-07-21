package kr.co.jhta.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kr.co.jhta.entity.Member;
import kr.co.jhta.entity.Post;
import kr.co.jhta.repository.PostRepository;
import kr.co.jhta.web.form.AddPostForm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	
	public void addPost (@Valid AddPostForm addPostForm, Member member) {
		Post post = new Post();
		BeanUtils.copyProperties(addPostForm, post);
		
		post.setMember(member);
		
		postRepository.save(post);
	}
	
	public List<Post> getPosts() {
		return postRepository.findAll();
	}
	
}
