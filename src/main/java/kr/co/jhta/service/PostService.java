package kr.co.jhta.service;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.jhta.dto.PostDto;
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
	
	public Page<Post> getPosts(Pageable pageable) {
		return postRepository.findAll(pageable);
	}

	public void increaseReadCount(int no) {
		Optional<Post> optional = postRepository.findById(no);
		Post post = optional.orElseThrow(() -> new RuntimeException("게시글 없음"));
		post.setReadCount(post.getReadCount() + 1);
		
		postRepository.save(post);
	}
	
	public PostDto getPost(int no) {
		Optional<Post> optional = postRepository.findById(no);
		Post post = optional.orElseThrow(() -> new RuntimeException("게시글 없음"));
		
		// PostDto객체를 생성해서 Post객체의 값을 저장한다.
		// 		- 영속성 객체가 계속 메모리에 남아있는 것을 방지하기 위해 영속성 객체를 빠르게 비워줘야하기 때문이다.
		PostDto dto = new PostDto();
		BeanUtils.copyProperties(post, dto);
		dto.setMemberId(post.getMember().getId());
		dto.setMemberName(post.getMember().getName());
		
		return dto;
	}
	
}
