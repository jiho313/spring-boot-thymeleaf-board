package kr.co.jhta.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sample_board_members")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
public class Member extends BaseDateTimeEntity implements UserDetails {

	private static final long serialVersionUID = 2599332695123557866L;

	@Id
	@Column(name = "member_id", updatable = false)
	private String id;
	
	@Column(name = "member_password", nullable = false)
	private String password;
	
	@Column(name = "member_name", nullable = false)
	private String name;
	
	@Column(name = "member_email", unique = true)
	private String email;
	
	@Column(name = "member_tel")
	private String tel;
	
	@Column(name = "member_deleted")
	private String deleted;
	
//	@Temporal(TemporalType.DATE)
//	@Column(name = "member_updated_date")
//	private Date updateDate;
//	
//	@Temporal(TemporalType.DATE)
//	@Column(name = "member_created_date")
//	private Date createDate;
	
	@Override
	public String getUsername() {
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
