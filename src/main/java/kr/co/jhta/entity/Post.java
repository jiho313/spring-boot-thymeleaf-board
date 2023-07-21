package kr.co.jhta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sample_board_posts") 
@DynamicInsert
@SequenceGenerator(
		name = "sample_posts_seq.generator",
		sequenceName = "sample_posts_seq",
		initialValue = 1,
		allocationSize = 1
		)
public class Post extends BaseDateTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "sample_posts_seq.generator")
	@Column(name = "post_no")
	private Integer no;
	
	@Column(name = "post_title")
	private String title;
	
	@Column(name = "post_content")
	private String content;
	
	@Column(name = "post_read_count")
	private int readCount;
	
	@Column(name = "post_comment_count")
	private int commentCount;
	
	@Column(name = "post_deleted")
	private String deleted;
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

}
