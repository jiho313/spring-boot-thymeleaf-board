package kr.co.jhta.dto;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
	
	private int no;
	private String title;
	private String content;
	private int readCount;
	private int commentCount;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private String memberId;
	private String memberName;
	
}
