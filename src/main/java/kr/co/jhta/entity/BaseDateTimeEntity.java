package kr.co.jhta.entity;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

/*
 * @MappedSuperclass
 * 		JPA Entity 클래스의 부모 클래스 역할을 수행하게 한다.
 * 		JPA Entity 클래스들이 이 클래스를 상속받으면 createDate, updateDate를 컬럼으로 인식하게 된다.
 * 
 * @EntityListeners(AuditingEntityListener.class)
 * 		해당 클래스에 JPA Auditing 기능을 포함시킨다.
 * 		* JPA Audit 기능
 * 			Spring Data JPA에서 날짜와 시간에 대해서 자동으로 값을 넣어주도록 지원하는 기능이다.
 * @CreateDate
 * 		Entity가 생성되어 저장될 때 날짜/시간 정보가 자동 저장된다.
 * @LastModifiedDate
 * 		조회한 Entity의 값을 변경할 때 날짜/시간 정보가 자동 변경되어 저장된다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDateTimeEntity {
	//모든 클래스에 상속 받게 하기 위해 클래스를 별도로 만듦
	
	@CreatedDate
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
}
